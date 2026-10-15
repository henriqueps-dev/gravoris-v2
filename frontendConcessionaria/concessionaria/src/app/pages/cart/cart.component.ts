import { Component, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../core/services/auth.service';
import { CartService } from '../../core/services/cart.service';
import { OrderService } from '../../core/services/order.service';
import { ToastService } from '../../core/services/toast.service';
import { formatPrice } from '../../core/utils/format';
import { SiteHeaderComponent } from '../../shared/components/site-header/site-header.component';
import { ScrollRevealDirective } from '../../shared/directives/scroll-reveal.directive';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [RouterLink, FormsModule, SiteHeaderComponent, ScrollRevealDirective],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent {
  protected readonly cart = inject(CartService);
  private readonly auth = inject(AuthService);
  private readonly orders = inject(OrderService);
  private readonly toast = inject(ToastService);
  protected readonly formatPrice = formatPrice;
  protected readonly checkingOut = signal(false);

  updateQty(id: string, qty: number): void {
    this.cart.updateQuantity(id, qty);
  }

  remove(id: string): void {
    this.cart.remove(id);
  }

  clear(): void {
    this.cart.clear();
  }

  async checkout(): Promise<void> {
    if (this.checkingOut() || this.cart.cartItems().length === 0) return;

    const clienteId = this.auth.getClienteId();
    if (!clienteId) {
      this.toast.error('Faça login para finalizar a compra');
      return;
    }

    this.checkingOut.set(true);

    try {
      await this.orders.createOrder(clienteId, this.cart.cartItems());
      this.cart.clear(false);
      this.toast.success('Compra realizada com sucesso');
    } catch {
      this.toast.error('Não foi possível finalizar o pedido');
    } finally {
      this.checkingOut.set(false);
    }
  }
}
