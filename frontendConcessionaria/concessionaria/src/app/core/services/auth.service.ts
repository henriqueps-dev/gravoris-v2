import { Injectable, computed, inject, signal } from '@angular/core';
import { Router } from '@angular/router';
import { ClienteRequest, ClienteResponse, LoginRequest, LoginResponse } from '../models/api';
import { AuthSession } from '../models/user';
import { mapApiError } from '../utils/api-error.util';
import { ClienteService } from './cliente.service';
import { ToastService } from './toast.service';

const SESSION_KEY = 'gravoris-session';
const REMEMBER_KEY = 'gravoris-remember';

/**
 * Equivalente Angular ao authService.js — orquestra login, cadastro e sessão.
 */
@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly clienteService = inject(ClienteService);
  private readonly toast = inject(ToastService);
  private readonly router = inject(Router);
  private readonly session = signal<AuthSession | null>(this.loadSession());

  readonly currentUser = computed(() => this.session());
  readonly isLoggedIn = computed(() => this.session() !== null);

  readonly userInitial = computed(() => {
    const name = this.session()?.fullName ?? '';
    return name.trim().charAt(0).toUpperCase() || '?';
  });

  async login(email: string, password: string, remember = false): Promise<boolean> {
    const payload: LoginRequest = {
      email: email.trim().toLowerCase(),
      password
    };

    try {
      const response = await this.clienteService.login(payload);
      this.setSession(response, remember);
      this.toast.success('Login realizado com sucesso');
      return true;
    } catch (error) {
      this.toast.error(mapApiError(error, 'login', 'E-mail ou senha incorretos'));
      return false;
    }
  }

  async register(data: {
    fullName: string;
    email: string;
    phone: string;
    cpf: string;
    password: string;
    confirmPassword: string;
  }): Promise<boolean> {
    if (
      !data.fullName.trim() ||
      !data.email.trim() ||
      !data.phone.trim() ||
      !data.cpf.trim() ||
      !data.password ||
      !data.confirmPassword
    ) {
      this.toast.error('Campos inválidos. Verifique os dados informados');
      return false;
    }

    if (data.password !== data.confirmPassword) {
      this.toast.error('As senhas não coincidem');
      return false;
    }

    if (data.password.length < 6) {
      this.toast.error('A senha deve ter pelo menos 6 caracteres');
      return false;
    }

    const cpf = data.cpf.replace(/\D/g, '');
    if (cpf.length !== 11) {
      this.toast.error('CPF inválido');
      return false;
    }

    const payload: ClienteRequest = {
      name: data.fullName.trim(),
      email: data.email.trim().toLowerCase(),
      password: data.password,
      phone: data.phone.trim(),
      cpf
    };

    try {
      const cliente = await this.clienteService.cadastrar(payload);

      this.setSession(
        {
          id: cliente.id,
          name: cliente.name,
          email: cliente.email,
          token: ''
        },
        true
      );
      this.toast.success('Conta criada com sucesso');
      return true;
    } catch (error) {
      this.toast.error(mapApiError(error, 'register', 'Não foi possível criar a conta'));
      return false;
    }
  }

  async redefinirSenha(email: string, newPassword: string): Promise<boolean> {
    if (!email.trim() || !newPassword) {
      this.toast.error('Campos inválidos. Verifique os dados informados');
      return false;
    }
    if (newPassword.length < 6) {
      this.toast.error('A senha deve ter pelo menos 6 caracteres');
      return false;
    }

    try {
      await this.clienteService.redefinirSenha({
        email: email.trim().toLowerCase(),
        newPassword
      });
      this.toast.success('Senha redefinida com sucesso!');
      return true;
    } catch (error) {
      this.toast.error(mapApiError(error, 'redefinirSenha', 'Não foi possível redefinir a senha. Verifique o e-mail informado.'));
      return false;
    }
  }

  async atualizarPerfil(data: {
    name: string;
    email: string;
    phone: string;
    cpf: string;
  }): Promise<ClienteResponse | null> {
    const clienteId = this.getClienteId();
    if (!clienteId) {
      this.toast.error('Usuário não autenticado');
      return null;
    }

    if (!data.name.trim() || !data.email.trim() || !data.phone.trim() || !data.cpf.trim()) {
      this.toast.error('Campos inválidos. Verifique os dados informados');
      return null;
    }

    const cpf = data.cpf.replace(/\D/g, '');
    if (cpf.length !== 11) {
      this.toast.error('CPF inválido');
      return null;
    }

    const payload: ClienteRequest = {
      name: data.name.trim(),
      email: data.email.trim().toLowerCase(),
      phone: data.phone.trim(),
      cpf
    };

    try {
      const response = await this.clienteService.atualizar(clienteId, payload);
      const current = this.session();
      if (current) {
        const updatedSession: AuthSession = {
          ...current,
          fullName: response.name,
          email: response.email
        };
        this.session.set(updatedSession);
        this.persistSession(updatedSession, current.remember);
      }
      this.toast.success('Perfil atualizado com sucesso!');
      return response;
    } catch (error) {
      this.toast.error(mapApiError(error, 'atualizar', 'Não foi possível atualizar o perfil'));
      return null;
    }
  }

  logout(): void {
    this.session.set(null);
    this.clearStorage();
    void this.router.navigate(['/']);
  }

  getClienteId(): number | null {
    const id = this.session()?.userId;
    return id ? Number(id) : null;
  }

  private setSession(response: LoginResponse, remember: boolean): void {
    const session: AuthSession = {
      userId: String(response.id),
      email: response.email,
      fullName: response.name,
      token: response.token,
      remember
    };
    this.session.set(session);
    this.persistSession(session, remember);
  }

  private loadSession(): AuthSession | null {
    if (typeof localStorage === 'undefined' && typeof sessionStorage === 'undefined') {
      return null;
    }
    try {
      const remember = localStorage?.getItem(REMEMBER_KEY) === 'true';
      const storage = remember ? localStorage : sessionStorage;
      const raw = storage?.getItem(SESSION_KEY);
      return raw ? (JSON.parse(raw) as AuthSession) : null;
    } catch {
      return null;
    }
  }

  private persistSession(session: AuthSession, remember: boolean): void {
    if (typeof localStorage === 'undefined') return;

    localStorage.setItem(REMEMBER_KEY, String(remember));
    const storage = remember ? localStorage : sessionStorage;
    const other = remember ? sessionStorage : localStorage;

    storage.setItem(SESSION_KEY, JSON.stringify(session));
    other.removeItem(SESSION_KEY);
  }

  private clearStorage(): void {
    if (typeof localStorage === 'undefined') return;
    localStorage.removeItem(SESSION_KEY);
    localStorage.removeItem(REMEMBER_KEY);
    sessionStorage.removeItem(SESSION_KEY);
  }
}
