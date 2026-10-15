import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { ToastService } from '../../core/services/toast.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  private readonly auth = inject(AuthService);
  private readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);
  private readonly toast = inject(ToastService);

  email = '';
  password = '';
  remember = false;
  loading = signal(false);

  isForgotPassword = signal(false);
  newPassword = '';
  confirmNewPassword = '';

  readonly backgroundVideo = 'assets/videos/bugatti.mp4';

  async submit(): Promise<void> {
    if (this.loading()) return;
    this.loading.set(true);

    const ok = await this.auth.login(this.email, this.password, this.remember);
    this.loading.set(false);

    if (ok) {
      const returnUrl = this.route.snapshot.queryParams['returnUrl'] as string;
      void this.router.navigateByUrl(returnUrl || '/');
    }
  }

  forgotPassword(): void {
    this.isForgotPassword.set(true);
    this.newPassword = '';
    this.confirmNewPassword = '';
  }

  async submitResetPassword(): Promise<void> {
    if (this.loading()) return;

    if (this.newPassword !== this.confirmNewPassword) {
      this.toast.error('As senhas não coincidem');
      return;
    }

    this.loading.set(true);
    const ok = await this.auth.redefinirSenha(this.email, this.newPassword);
    this.loading.set(false);

    if (ok) {
      this.isForgotPassword.set(false);
      this.password = '';
      this.newPassword = '';
      this.confirmNewPassword = '';
    }
  }
}
