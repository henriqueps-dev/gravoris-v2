import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { VehicleDetailComponent } from './pages/vehicle-detail/vehicle-detail.component';
import { SearchComponent } from './pages/search/search.component';
import { CartComponent } from './pages/cart/cart.component';
import { AccountComponent } from './pages/account/account.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { authGuard } from './core/guards/auth.guard';
import { guestGuard } from './core/guards/guest.guard';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'veiculo/:id', component: VehicleDetailComponent },
  { path: 'busca', component: SearchComponent },
  { path: 'login', component: LoginComponent, canActivate: [guestGuard] },
  { path: 'cadastro', component: RegisterComponent, canActivate: [guestGuard] },
  { path: 'carrinho', component: CartComponent, canActivate: [authGuard] },
  { path: 'conta', component: AccountComponent, canActivate: [authGuard] },
  { path: '**', redirectTo: '' }
];
