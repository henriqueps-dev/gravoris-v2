import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  private readonly auth = inject(AuthService);
  private readonly router = inject(Router);

  fullName = '';
  email = '';
  phone = '';
  cpf = '';
  password = '';
  confirmPassword = '';
  loading = signal(false);

  readonly backgroundVideo = 'assets/videos/mclaren.mp4';

  async submit(): Promise<void> {
    if (this.loading()) return;
    this.loading.set(true);

    const ok = await this.auth.register({
      fullName: this.fullName,
      email: this.email,
      phone: this.phone,
      cpf: this.cpf,
      password: this.password,
      confirmPassword: this.confirmPassword
    });
    this.loading.set(false);

    if (ok) {
      void this.router.navigate(['/conta']);
    }
  }
}
