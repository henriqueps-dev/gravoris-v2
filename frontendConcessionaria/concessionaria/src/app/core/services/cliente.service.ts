import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { API_BASE_URL } from '../config/api.config';
import { ClienteRequest, ClienteResponse, LoginRequest, LoginResponse, RedefinirSenhaRequest } from '../models/api';

/**
 * Equivalente Angular ao clienteService.js — comunicação HTTP com /api/clientes.
 */
@Injectable({ providedIn: 'root' })
export class ClienteService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${API_BASE_URL}/clientes`;

  cadastrar(payload: ClienteRequest): Promise<ClienteResponse> {
    return firstValueFrom(this.http.post<ClienteResponse>(this.baseUrl, payload));
  }

  atualizar(id: number, payload: ClienteRequest): Promise<ClienteResponse> {
    return firstValueFrom(this.http.put<ClienteResponse>(`${this.baseUrl}/${id}`, payload));
  }

  login(payload: LoginRequest): Promise<LoginResponse> {
    return firstValueFrom(this.http.post<LoginResponse>(`${this.baseUrl}/login`, payload));
  }

  buscarPorId(id: number): Promise<ClienteResponse> {
    return firstValueFrom(this.http.get<ClienteResponse>(`${this.baseUrl}/${id}`));
  }

  redefinirSenha(payload: RedefinirSenhaRequest): Promise<void> {
    return firstValueFrom(this.http.post<void>(`${this.baseUrl}/redefinir-senha`, payload));
  }
}
