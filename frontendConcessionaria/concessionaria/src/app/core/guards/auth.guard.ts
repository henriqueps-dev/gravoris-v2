import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { AuthModalService } from '../services/auth-modal.service';

export const authGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  if (auth.isLoggedIn()) return true;

  inject(AuthModalService).open();
  return inject(Router).createUrlTree(['/']);
};
