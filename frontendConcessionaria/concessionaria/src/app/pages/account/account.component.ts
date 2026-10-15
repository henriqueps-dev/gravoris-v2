import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../core/services/auth.service';
import { ClienteService } from '../../core/services/cliente.service';
import { FavoritesService } from '../../core/services/favorites.service';
import { VehicleCatalogService } from '../../core/services/vehicle-catalog.service';
import { OrderService } from '../../core/services/order.service';
import { ToastService } from '../../core/services/toast.service';
import { ClienteResponse, PedidoResponse } from '../../core/models/api';
import { formatPrice } from '../../core/utils/format';
import { SiteHeaderComponent } from '../../shared/components/site-header/site-header.component';
import { VehicleCardComponent } from '../../shared/components/vehicle-card/vehicle-card.component';

@Component({
  selector: 'app-account',
  standalone: true,
  imports: [RouterLink, FormsModule, SiteHeaderComponent, VehicleCardComponent],
  templateUrl: './account.component.html',
  styleUrl: './account.component.css'
})
export class AccountComponent implements OnInit {
  protected readonly auth = inject(AuthService);
  protected readonly favorites = inject(FavoritesService);
  protected readonly catalog = inject(VehicleCatalogService);
  private readonly orderService = inject(OrderService);
  private readonly clienteService = inject(ClienteService);
  private readonly toast = inject(ToastService);

  activeSection = 'perfil';
  orders = signal<PedidoResponse[]>([]);
  loadingOrders = signal(false);

  cliente = signal<ClienteResponse | null>(null);
  loadingProfile = signal(false);
  editingProfile = signal(false);
  savingProfile = signal(false);

  editName = '';
  editEmail = '';
  editPhone = '';
  editCpf = '';

  protected readonly formatPrice = formatPrice;

  protected readonly favoriteVehicles = computed(() => {
    const ids = this.favorites.favoriteIds();
    return this.catalog.getFilteredVehicles().filter((v) => ids.includes(v.id));
  });

  async ngOnInit(): Promise<void> {
    if (typeof window !== 'undefined') {
      const hash = window.location.hash.replace('#', '');
      if (['perfil', 'pedidos', 'favoritos', 'configuracoes'].includes(hash)) {
        this.activeSection = hash;
      }
    }
    await Promise.all([
      this.loadProfile(),
      this.loadOrders()
    ]);
  }

  async setSection(section: string): Promise<void> {
    this.activeSection = section;
    if (typeof window !== 'undefined') {
      window.location.hash = section;
    }
    if (section === 'pedidos') {
      await this.loadOrders();
    } else if (section === 'perfil') {
      await this.loadProfile();
    }
  }

  async loadProfile(): Promise<void> {
    const clienteId = this.auth.getClienteId();
    if (!clienteId) return;

    this.loadingProfile.set(true);
    try {
      const data = await this.clienteService.buscarPorId(clienteId);
      this.cliente.set(data);
      this.initEditForm(data);
    } catch (error) {
      console.error('Erro ao buscar perfil do cliente:', error);
    } finally {
      this.loadingProfile.set(false);
    }
  }

  initEditForm(data: ClienteResponse): void {
    this.editName = data.name || '';
    this.editEmail = data.email || '';
    this.editPhone = data.phone || '';
    this.editCpf = data.cpf || '';
  }

  startEditing(): void {
    const c = this.cliente();
    if (c) {
      this.initEditForm(c);
    }
    this.editingProfile.set(true);
  }

  cancelEditing(): void {
    const c = this.cliente();
    if (c) {
      this.initEditForm(c);
    }
    this.editingProfile.set(false);
  }

  async saveProfile(): Promise<void> {
    if (this.savingProfile()) return;
    this.savingProfile.set(true);

    const updated = await this.auth.atualizarPerfil({
      name: this.editName,
      email: this.editEmail,
      phone: this.editPhone,
      cpf: this.editCpf
    });

    this.savingProfile.set(false);

    if (updated) {
      this.cliente.set(updated);
      this.editingProfile.set(false);
    }
  }

  formatCpf(cpf: string): string {
    if (!cpf) return 'Não informado';
    const digits = cpf.replace(/\D/g, '');
    if (digits.length === 11) {
      return `${digits.slice(0, 3)}.${digits.slice(3, 6)}.${digits.slice(6, 9)}-${digits.slice(9)}`;
    }
    return cpf;
  }

  formatPhone(phone: string): string {
    if (!phone) return 'Não informado';
    const digits = phone.replace(/\D/g, '');
    if (digits.length === 11) {
      return `(${digits.slice(0, 2)}) ${digits.slice(2, 7)}-${digits.slice(7)}`;
    }
    if (digits.length === 10) {
      return `(${digits.slice(0, 2)}) ${digits.slice(2, 6)}-${digits.slice(6)}`;
    }
    return phone;
  }

  async loadOrders(): Promise<void> {
    const clienteId = this.auth.getClienteId();
    if (!clienteId) return;

    this.loadingOrders.set(true);
    try {
      const result = await this.orderService.getOrdersByCliente(clienteId);
      // Ordenar por ID decrescente (mais recentes primeiro)
      result.sort((a, b) => b.id - a.id);
      this.orders.set(result);
    } catch (error) {
      console.error('Erro ao carregar pedidos:', error);
    } finally {
      this.loadingOrders.set(false);
    }
  }

  formatDate(dateStr: string): string {
    try {
      const date = new Date(dateStr);
      return date.toLocaleDateString('pt-BR', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
      });
    } catch {
      return dateStr;
    }
  }

  getStatusClass(status: string): string {
    const s = status.toLowerCase();
    if (s.includes('pendente')) return 'status-pending';
    if (s.includes('pago') || s.includes('finalizado') || s.includes('entregue')) return 'status-success';
    if (s.includes('cancelado')) return 'status-canceled';
    return '';
  }
}
