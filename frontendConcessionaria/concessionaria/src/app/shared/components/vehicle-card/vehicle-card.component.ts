import { Component, inject, input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Vehicle } from '../../../core/models/vehicle';
import { formatPrice } from '../../../core/utils/format';
import { ScrollRevealDirective } from '../../directives/scroll-reveal.directive';
import { FavoritesService } from '../../../core/services/favorites.service';

@Component({
  selector: 'app-vehicle-card',
  standalone: true,
  imports: [RouterLink, ScrollRevealDirective],
  templateUrl: './vehicle-card.component.html',
  styleUrl: './vehicle-card.component.css'
})
export class VehicleCardComponent {
  readonly vehicle = input.required<Vehicle>();
  protected readonly formatPrice = formatPrice;
  protected readonly favorites = inject(FavoritesService);

  toggleFavorite(event: Event): void {
    event.preventDefault();
    event.stopPropagation();
    this.favorites.toggle(this.vehicle().id);
  }

  isFavorite(): boolean {
    return this.favorites.isFavorite(this.vehicle().id);
  }
}
