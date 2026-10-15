import { Injectable, computed, inject, signal } from '@angular/core';
import { Vehicle } from '../models/vehicle';
import { ToastService } from './toast.service';

export interface CartItem {
  vehicle: Vehicle;
  quantity: number;
}

const STORAGE_KEY = 'gravoris-cart';

@Injectable({ providedIn: 'root' })
export class CartService {
  private readonly toast = inject(ToastService);
  private readonly items = signal<CartItem[]>(this.loadFromStorage());

  readonly cartItems = this.items.asReadonly();

  readonly totalItems = computed(() =>
    this.items().reduce((sum, item) => sum + item.quantity, 0)
  );

  readonly totalPrice = computed(() =>
    this.items().reduce((sum, item) => sum + item.vehicle.price * item.quantity, 0)
  );

  add(vehicle: Vehicle): void {
    const current = this.items();
    const existing = current.find((i) => i.vehicle.id === vehicle.id);
    if (existing) {
      this.items.set(
        current.map((i) =>
          i.vehicle.id === vehicle.id ? { ...i, quantity: i.quantity + 1 } : i
        )
      );
    } else {
      this.items.set([...current, { vehicle, quantity: 1 }]);
    }
    this.persist();
    this.toast.success('Veículo adicionado ao carrinho');
  }

  remove(vehicleId: string): void {
    this.items.set(this.items().filter((i) => i.vehicle.id !== vehicleId));
    this.persist();
    this.toast.success('Veículo removido do carrinho');
  }

  updateQuantity(vehicleId: string, quantity: number): void {
    if (quantity < 1) {
      this.remove(vehicleId);
      return;
    }
    this.items.set(
      this.items().map((i) => (i.vehicle.id === vehicleId ? { ...i, quantity } : i))
    );
    this.persist();
  }

  clear(showToast = true): void {
    this.items.set([]);
    this.persist();
    if (showToast) {
      this.toast.success('Carrinho limpo com sucesso');
    }
  }

  private loadFromStorage(): CartItem[] {
    if (typeof localStorage === 'undefined') return [];
    try {
      const raw = localStorage.getItem(STORAGE_KEY);
      return raw ? (JSON.parse(raw) as CartItem[]) : [];
    } catch {
      return [];
    }
  }

  private persist(): void {
    if (typeof localStorage === 'undefined') return;
    localStorage.setItem(STORAGE_KEY, JSON.stringify(this.items()));
  }
}
