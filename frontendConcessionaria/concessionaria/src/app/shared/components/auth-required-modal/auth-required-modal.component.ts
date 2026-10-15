import { Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthModalService } from '../../../core/services/auth-modal.service';

@Component({
  selector: 'app-auth-required-modal',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './auth-required-modal.component.html',
  styleUrl: './auth-required-modal.component.css'
})
export class AuthRequiredModalComponent {
  protected readonly authModal = inject(AuthModalService);

  close(): void {
    this.authModal.close();
  }
}
