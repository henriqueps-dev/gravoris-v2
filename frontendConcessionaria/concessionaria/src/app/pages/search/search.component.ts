import { Component, computed, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { VehicleCatalogService } from '../../core/services/vehicle-catalog.service';
import { SiteHeaderComponent } from '../../shared/components/site-header/site-header.component';
import { VehicleCardComponent } from '../../shared/components/vehicle-card/vehicle-card.component';
import { BrandBarComponent } from '../../shared/components/brand-bar/brand-bar.component';
import { VehicleFiltersComponent } from '../../shared/components/vehicle-filters/vehicle-filters.component';

@Component({
  selector: 'app-search',
  standalone: true,
  imports: [
    RouterLink,
    SiteHeaderComponent,
    VehicleCardComponent,
    BrandBarComponent,
    VehicleFiltersComponent
  ],
  templateUrl: './search.component.html',
  styleUrl: './search.component.css'
})
export class SearchComponent implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  protected readonly catalog = inject(VehicleCatalogService);

  protected readonly query = computed(() => this.catalog.searchQuery());

  protected readonly vehicles = computed(() => {
    this.catalog.searchQuery();
    this.catalog.filters();
    return this.catalog.getFilteredVehicles();
  });

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      const q = (params['q'] as string) ?? '';
      void this.catalog.searchFromApi(q);
    });
  }

  onHeaderSearch(term: string): void {
    void this.router.navigate(['/busca'], { queryParams: { q: term } });
  }

  refresh(): void {
    this.catalog.filters.update((f) => ({ ...f }));
  }
}
