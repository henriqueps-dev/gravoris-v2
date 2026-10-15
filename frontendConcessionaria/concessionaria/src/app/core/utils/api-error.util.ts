import { HttpErrorResponse } from '@angular/common/http';
import { ApiErrorResponse } from '../models/api';

export type AuthErrorContext = 'login' | 'register' | 'redefinirSenha' | 'atualizar';

export function mapApiError(error: unknown, context: AuthErrorContext, fallback: string): string {
  if (!(error instanceof HttpErrorResponse)) {
    return fallback;
  }

  if (error.status === 0) {
    return 'Servidor indisponível. Verifique se o backend está em http://localhost:8080';
  }

  if (error.status === 500) {
    return 'Erro interno do servidor';
  }

  if (error.status === 401 || (context === 'login' && isInvalidCredentials(error))) {
    return 'E-mail ou senha incorretos';
  }

  if (error.status === 409) {
    return mapConflictError(error);
  }

  if (error.status === 400) {
    return mapBadRequestError(error, context);
  }

  const body = error.error as ApiErrorResponse | undefined;
  return body?.error || fallback;
}

function mapConflictError(error: HttpErrorResponse): string {
  const message = getErrorMessage(error).toLowerCase();

  if (message.includes('email')) {
    return 'E-mail já cadastrado';
  }
  if (message.includes('cpf')) {
    return 'CPF já cadastrado';
  }

  return getErrorMessage(error) || 'Dados já cadastrados';
}

function mapBadRequestError(error: HttpErrorResponse, context: AuthErrorContext): string {
  const message = getErrorMessage(error).toLowerCase();

  if (message.includes('cpf')) {
    return 'CPF inválido';
  }
  if (message.includes('email')) {
    return 'E-mail inválido';
  }
  if (message.includes('must not be blank') || message.includes('não pode estar em branco')) {
    return 'Campos inválidos. Verifique os dados informados';
  }

  const bodyMessage = getErrorMessage(error);
  if (bodyMessage) {
    return bodyMessage;
  }

  if (context === 'register' || context === 'atualizar') {
    return 'Campos inválidos. Verifique os dados informados';
  }
  return 'E-mail ou senha incorretos';
}

function isInvalidCredentials(error: HttpErrorResponse): boolean {
  const message = getErrorMessage(error).toLowerCase();
  return message.includes('credenciais') || message.includes('incorret');
}

function getErrorMessage(error: HttpErrorResponse): string {
  const body = error.error as ApiErrorResponse | string | undefined;
  if (typeof body === 'string') return body;
  return body?.error ?? '';
}
