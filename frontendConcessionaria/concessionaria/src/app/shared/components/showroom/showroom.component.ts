import { Component, computed, inject } from '@angular/core';
import { BrandBarComponent } from '../brand-bar/brand-bar.component';
import { VehicleCardComponent } from '../vehicle-card/vehicle-card.component';
import { VehicleFiltersComponent } from '../vehicle-filters/vehicle-filters.component';
import { VehicleCatalogService } from '../../../core/services/vehicle-catalog.service';

@Component({
  selector: 'app-showroom',
  standalone: true,
  imports: [BrandBarComponent, VehicleCardComponent, VehicleFiltersComponent],
  templateUrl: './showroom.component.html',
  styleUrl: './showroom.component.css'
})
export class ShowroomComponent {
  protected readonly catalog = inject(VehicleCatalogService);

  protected readonly vehicles = computed(() => {
    this.catalog.filters();
    return this.catalog.getFilteredVehicles();
  });

  refresh(): void {
    this.catalog.filters.update((f) => ({ ...f }));
  }
}
