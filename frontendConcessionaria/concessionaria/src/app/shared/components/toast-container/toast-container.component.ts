import { Component, inject } from '@angular/core';
import { ToastService } from '../../../core/services/toast.service';

@Component({
  selector: 'app-toast-container',
  standalone: true,
  templateUrl: './toast-container.component.html',
  styleUrl: './toast-container.component.css'
})
export class ToastContainerComponent {
  protected readonly toastService = inject(ToastService);

  dismiss(id: number): void {
    this.toastService.dismiss(id);
  }
}
