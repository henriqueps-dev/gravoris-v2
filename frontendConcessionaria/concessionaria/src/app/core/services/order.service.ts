import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { API_BASE_URL } from '../config/api.config';
import { PedidoRequest, PedidoResponse } from '../models/api';
import { CartItem } from './cart.service';

@Injectable({ providedIn: 'root' })
export class OrderService {
  private readonly http = inject(HttpClient);

  async createOrder(clienteId: number, items: CartItem[]): Promise<PedidoResponse> {
    const request: PedidoRequest = {
      clienteId,
      items: items.map((item) => ({
        productId: Number(item.vehicle.id),
        quantity: item.quantity,
        price: item.vehicle.price
      }))
    };

    return firstValueFrom(this.http.post<PedidoResponse>(`${API_BASE_URL}/pedidos`, request));
  }

  async getOrdersByCliente(clienteId: number): Promise<PedidoResponse[]> {
    return firstValueFrom(this.http.get<PedidoResponse[]>(`${API_BASE_URL}/pedidos/cliente/${clienteId}`));
  }
}
