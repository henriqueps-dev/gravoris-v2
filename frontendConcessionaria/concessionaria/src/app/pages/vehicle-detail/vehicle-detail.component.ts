import { Component, computed, inject, input, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Vehicle } from '../../core/models/vehicle';
import { VehicleCatalogService } from '../../core/services/vehicle-catalog.service';
import { CartService } from '../../core/services/cart.service';
import { AuthService } from '../../core/services/auth.service';
import { AuthModalService } from '../../core/services/auth-modal.service';
import { FavoritesService } from '../../core/services/favorites.service';
import { formatPrice } from '../../core/utils/format';
import { SiteHeaderComponent } from '../../shared/components/site-header/site-header.component';
import { ScrollRevealDirective } from '../../shared/directives/scroll-reveal.directive';

@Component({
  selector: 'app-vehicle-detail',
  standalone: true,
  imports: [RouterLink, SiteHeaderComponent, ScrollRevealDirective],
  templateUrl: './vehicle-detail.component.html',
  styleUrl: './vehicle-detail.component.css'
})
export class VehicleDetailComponent implements OnInit {
  readonly id = input.required<string>();
  private readonly catalog = inject(VehicleCatalogService);
  protected readonly cart = inject(CartService);
  protected readonly auth = inject(AuthService);
  protected readonly favorites = inject(FavoritesService);
  private readonly authModal = inject(AuthModalService);
  protected readonly formatPrice = formatPrice;

  private readonly loadedVehicle = signal<Vehicle | undefined>(undefined);
  readonly loading = signal(true);

  protected readonly vehicle = computed(
    () => this.loadedVehicle() ?? this.catalog.getById(this.id())
  );

  selectedImageIndex = 0;

  ngOnInit(): void {
    void this.loadVehicle();
  }

  private async loadVehicle(): Promise<void> {
    this.loading.set(true);
    try {
      const vehicle = await this.catalog.fetchById(this.id());
      this.loadedVehicle.set(vehicle);
    } finally {
      this.loading.set(false);
    }
  }

  addToCart(): void {
    if (!this.auth.isLoggedIn()) {
      this.authModal.open();
      return;
    }
    const v = this.vehicle();
    if (v) this.cart.add(v);
  }

  toggleFavorite(): void {
    const v = this.vehicle();
    if (v) this.favorites.toggle(v.id);
  }

  isFavorite(): boolean {
    const v = this.vehicle();
    return v ? this.favorites.isFavorite(v.id) : false;
  }

  selectImage(index: number): void {
    this.selectedImageIndex = index;
  }
}
