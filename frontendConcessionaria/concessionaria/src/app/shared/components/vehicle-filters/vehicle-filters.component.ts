import { Component, inject, output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { SortOption } from '../../../core/models/vehicle';
import { VehicleCatalogService } from '../../../core/services/vehicle-catalog.service';
import { ScrollRevealDirective } from '../../directives/scroll-reveal.directive';

@Component({
  selector: 'app-vehicle-filters',
  standalone: true,
  imports: [FormsModule, ScrollRevealDirective],
  templateUrl: './vehicle-filters.component.html',
  styleUrl: './vehicle-filters.component.css'
})
export class VehicleFiltersComponent {
  readonly filtersChanged = output<void>();
  protected readonly catalog = inject(VehicleCatalogService);
  protected readonly sortOptions: { value: SortOption; label: string }[] = [
    { value: 'price-desc', label: 'Preço: maior → menor' },
    { value: 'price-asc', label: 'Preço: menor → maior' },
    { value: 'power-desc', label: 'Potência: maior → menor' },
    { value: 'power-asc', label: 'Potência: menor → maior' }
  ];

  speedOptions = [
    { value: null, label: 'Qualquer velocidade' },
    { value: 250, label: '250+ km/h' },
    { value: 300, label: '300+ km/h' },
    { value: 350, label: '350+ km/h' }
  ];

  priceRanges = [
    { min: null, max: null, label: 'Qualquer preço' },
    { min: null, max: 2000000, label: 'Até R$ 2M' },
    { min: 2000000, max: 5000000, label: 'R$ 2M – R$ 5M' },
    { min: 5000000, max: 10000000, label: 'R$ 5M – R$ 10M' },
    { min: 10000000, max: null, label: 'Acima de R$ 10M' }
  ];

  onSortChange(value: SortOption): void {
    this.catalog.updateFilters({ sort: value });
    this.filtersChanged.emit();
  }

  onSpeedChange(value: number | null): void {
    this.catalog.updateFilters({ minSpeed: value });
    this.filtersChanged.emit();
  }

  onBrandFilterChange(value: string): void {
    const brand = value || null;
    this.catalog.setBrand(brand);
    this.filtersChanged.emit();
  }

  onPriceRangeChange(index: number): void {
    const range = this.priceRanges[index];
    this.catalog.updateFilters({ minPrice: range.min, maxPrice: range.max });
    this.filtersChanged.emit();
  }

  get selectedPriceIndex(): number {
    const { minPrice, maxPrice } = this.catalog.filters();
    return this.priceRanges.findIndex((r) => r.min === minPrice && r.max === maxPrice);
  }
}
