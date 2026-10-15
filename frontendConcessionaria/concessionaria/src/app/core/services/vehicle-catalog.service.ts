import { HttpClient } from '@angular/common/http';
import { computed, inject, Injectable, signal } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { API_BASE_URL } from '../config/api.config';
import { ProdutoResponse } from '../models/api';
import { Brand, SortOption, Vehicle, VehicleFilters } from '../models/vehicle';
import { mapProdutoToVehicle } from '../utils/product-mapper';

const DEFAULT_FILTERS: VehicleFilters = {
  brand: null,
  minSpeed: null,
  minPrice: null,
  maxPrice: null,
  sort: 'price-desc'
};

@Injectable({ providedIn: 'root' })
export class VehicleCatalogService {
  private readonly http = inject(HttpClient);
  private readonly vehicles = signal<Vehicle[]>([]);

  readonly filters = signal<VehicleFilters>({ ...DEFAULT_FILTERS });
  readonly searchQuery = signal('');
  readonly loading = signal(false);
  readonly loaded = signal(false);
  readonly error = signal<string | null>(null);

  readonly availableBrands = computed(() =>
    [...new Set(this.vehicles().map((v) => v.brand))].sort()
  );

  constructor() {
    void this.loadVitrine();
  }

  async loadVitrine(): Promise<void> {
    this.loading.set(true);
    this.error.set(null);

    try {
      const products = await firstValueFrom(
        this.http.get<ProdutoResponse[]>(`${API_BASE_URL}/produtos/vitrine`)
      );
      this.vehicles.set(products.map(mapProdutoToVehicle));
      this.loaded.set(true);
    } catch {
      this.error.set('Não foi possível carregar o catálogo');
    } finally {
      this.loading.set(false);
    }
  }

  async searchFromApi(term: string): Promise<void> {
    const trimmed = (term ?? '').trim();
    this.searchQuery.set(trimmed);
    this.loading.set(true);
    this.error.set(null);

    try {
      const products = await firstValueFrom(
        this.http.get<ProdutoResponse[]>(`${API_BASE_URL}/produtos/busca`, {
          params: trimmed ? { q: trimmed } : {}
        })
      );
      this.vehicles.set(products.map(mapProdutoToVehicle));
      this.loaded.set(true);
    } catch {
      this.error.set('Não foi possível buscar veículos');
    } finally {
      this.loading.set(false);
    }
  }

  getById(id: string): Vehicle | undefined {
    return this.vehicles().find((v) => v.id === id);
  }

  async fetchById(id: string): Promise<Vehicle | undefined> {
    this.loading.set(true);
    this.error.set(null);

    try {
      const product = await firstValueFrom(
        this.http.get<ProdutoResponse>(`${API_BASE_URL}/produtos/${id}`)
      );
      const vehicle = mapProdutoToVehicle(product);
      this.vehicles.update((list) => {
        const index = list.findIndex((v) => v.id === vehicle.id);
        if (index >= 0) {
          const updated = [...list];
          updated[index] = vehicle;
          return updated;
        }
        return [...list, vehicle];
      });
      return vehicle;
    } catch (err) {
      console.error(`Erro ao consultar produto ${id}:`, err);
      return this.getById(id);
    } finally {
      this.loading.set(false);
    }
  }

  setBrand(brand: Brand | null): void {
    this.filters.update((f) => ({ ...f, brand }));
  }

  updateFilters(partial: Partial<VehicleFilters>): void {
    this.filters.update((f) => ({ ...f, ...partial }));
  }

  resetFilters(): void {
    this.filters.set({ ...DEFAULT_FILTERS });
    this.searchQuery.set('');
  }

  getFilteredVehicles(): Vehicle[] {
    const { brand, minSpeed, minPrice, maxPrice, sort } = this.filters();
    let result = [...this.vehicles()];

    if (brand) {
      result = result.filter((v) => v.brand === brand);
    }

    if (minSpeed != null) {
      result = result.filter((v) => v.topSpeed >= minSpeed);
    }

    if (minPrice != null) {
      result = result.filter((v) => v.price >= minPrice);
    }

    if (maxPrice != null) {
      result = result.filter((v) => v.price <= maxPrice);
    }

    return this.sortVehicles(result, sort);
  }

  search(term: string): Vehicle[] {
    this.searchQuery.set(term);
    return this.getFilteredVehicles();
  }

  private sortVehicles(vehicles: Vehicle[], sort: SortOption): Vehicle[] {
    const sorted = [...vehicles];
    switch (sort) {
      case 'price-asc':
        return sorted.sort((a, b) => a.price - b.price);
      case 'price-desc':
        return sorted.sort((a, b) => b.price - a.price);
      case 'power-asc':
        return sorted.sort((a, b) => a.power - b.power);
      case 'power-desc':
        return sorted.sort((a, b) => b.power - a.power);
      default:
        return sorted;
    }
  }
}
