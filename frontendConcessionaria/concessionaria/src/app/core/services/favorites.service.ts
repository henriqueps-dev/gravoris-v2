import { Injectable, computed, signal } from '@angular/core';
import { ToastService } from './toast.service';
import { AuthService } from './auth.service';
import { AuthModalService } from './auth-modal.service';

const STORAGE_KEY = 'gravoris-favorites';

@Injectable({ providedIn: 'root' })
export class FavoritesService {
  private readonly ids = signal<string[]>(this.load());

  readonly favoriteIds = this.ids.asReadonly();
  readonly count = computed(() => this.ids().length);

  constructor(
    private readonly auth: AuthService,
    private readonly authModal: AuthModalService,
    private readonly toast: ToastService
  ) {}

  isFavorite(vehicleId: string): boolean {
    return this.ids().includes(vehicleId);
  }

  toggle(vehicleId: string): boolean {
    if (!this.auth.isLoggedIn()) {
      this.authModal.open();
      return false;
    }

    if (this.isFavorite(vehicleId)) {
      this.ids.update((list) => list.filter((id) => id !== vehicleId));
      this.toast.info('Veículo removido dos favoritos');
    } else {
      this.ids.update((list) => [...list, vehicleId]);
      this.toast.success('Veículo adicionado aos favoritos');
    }
    this.persist();
    return true;
  }

  private load(): string[] {
    if (typeof localStorage === 'undefined') return [];
    try {
      const raw = localStorage.getItem(STORAGE_KEY);
      return raw ? (JSON.parse(raw) as string[]) : [];
    } catch {
      return [];
    }
  }

  private persist(): void {
    if (typeof localStorage === 'undefined') return;
    localStorage.setItem(STORAGE_KEY, JSON.stringify(this.ids()));
  }
}
