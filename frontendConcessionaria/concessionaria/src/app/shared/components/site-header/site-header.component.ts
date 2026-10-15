import { Component, HostListener, inject, input, output, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CartService } from '../../../core/services/cart.service';
import { AuthService } from '../../../core/services/auth.service';
import { AuthModalService } from '../../../core/services/auth-modal.service';

@Component({
  selector: 'app-site-header',
  standalone: true,
  imports: [RouterLink, FormsModule],
  templateUrl: './site-header.component.html',
  styleUrl: './site-header.component.css'
})
export class SiteHeaderComponent {
  readonly transparent = input(false);
  readonly search = output<string>();

  protected readonly cart = inject(CartService);
  protected readonly auth = inject(AuthService);
  private readonly authModal = inject(AuthModalService);
  private readonly router = inject(Router);

  searchTerm = '';
  menuOpen = false;
  userMenuOpen = signal(false);

  onSearchSubmit(): void {
    const term = this.searchTerm.trim();
    if (!term) return;
    this.search.emit(term);
    void this.router.navigate(['/busca'], { queryParams: { q: term } });
  }

  toggleMenu(): void {
    this.menuOpen = !this.menuOpen;
  }

  toggleUserMenu(): void {
    if (!this.auth.isLoggedIn()) {
      void this.router.navigate(['/login']);
      return;
    }
    this.userMenuOpen.update((v) => !v);
  }

  onCartClick(event: Event): void {
    if (this.auth.isLoggedIn()) return;
    event.preventDefault();
    this.authModal.open();
  }

  logout(): void {
    this.userMenuOpen.set(false);
    this.auth.logout();
  }

  @HostListener('document:click', ['$event'])
  closeUserMenu(event: MouseEvent): void {
    const target = event.target as HTMLElement;
    if (!target.closest('.user-menu-wrap')) {
      this.userMenuOpen.set(false);
    }
  }
}
