import { Component, inject, output } from '@angular/core';
import { Brand } from '../../../core/models/vehicle';
import { VehicleCatalogService } from '../../../core/services/vehicle-catalog.service';

@Component({
  selector: 'app-brand-bar',
  standalone: true,
  templateUrl: './brand-bar.component.html',
  styleUrl: './brand-bar.component.css'
})
export class BrandBarComponent {
  readonly brandSelected = output<Brand | null>();
  protected readonly catalog = inject(VehicleCatalogService);

  selectBrand(brand: Brand): void {
    const current = this.catalog.filters().brand;
    const next = current === brand ? null : brand;
    this.catalog.setBrand(next);
    this.brandSelected.emit(next);
  }

  isActive(brand: Brand): boolean {
    return this.catalog.filters().brand === brand;
  }
}
