import { Injectable, signal } from '@angular/core';

export type ToastType = 'success' | 'error' | 'info';

export interface Toast {
  id: number;
  type: ToastType;
  message: string;
  icon: string;
}

@Injectable({ providedIn: 'root' })
export class ToastService {
  private nextId = 0;
  readonly toasts = signal<Toast[]>([]);

  success(message: string): void {
    this.show('success', message, '✓');
  }

  error(message: string): void {
    this.show('error', message, '✕');
  }

  info(message: string): void {
    this.show('info', message, 'ℹ');
  }

  private show(type: ToastType, message: string, icon: string): void {
    const id = ++this.nextId;
    const toast: Toast = { id, type, message, icon };
    this.toasts.update((list) => [...list, toast]);

    setTimeout(() => this.dismiss(id), 4200);
  }

  dismiss(id: number): void {
    this.toasts.update((list) => list.filter((t) => t.id !== id));
  }
}
