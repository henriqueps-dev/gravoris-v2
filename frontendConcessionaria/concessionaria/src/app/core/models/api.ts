export interface ProdutoResponse {
  id: number;
  name: string;
  brand: string;
  model: string;
  price: number;
  imageUrl: string;
  description: string;
  year: number;
  mileage: number;
  category: string;
  stock: number;
  engine: string;
  power: number;
  torque: string;
  topSpeed: number;
  acceleration: string;
}

/** Payload esperado pelo backend em POST e PUT /api/clientes */
export interface ClienteRequest {
  name: string;
  email: string;
  password?: string;
  phone: string;
  cpf: string;
}

/** Payload esperado pelo backend em POST /api/clientes/login */
export interface LoginRequest {
  email: string;
  password: string;
}

/** Payload esperado pelo backend em POST /api/clientes/redefinir-senha */
export interface RedefinirSenhaRequest {
  email: string;
  newPassword: string;
}

export interface ClienteResponse {
  id: number;
  name: string;
  email: string;
  phone: string;
  cpf: string;
  dataNascimento: string | null;
  ativo: boolean;
}

export interface LoginResponse {
  id: number;
  name: string;
  email: string;
  token: string;
}

export interface PedidoItemRequest {
  productId: number;
  quantity: number;
  price: number;
}

export interface PedidoRequest {
  clienteId: number;
  items: PedidoItemRequest[];
}

export interface PedidoItemResponse {
  produtoId: number;
  nome: string;
  marca: string;
  modelo: string;
  imagemUrl: string;
  quantidade: number;
  precoUnitario: number;
}

export interface PedidoResponse {
  id: number;
  total: number;
  status: string;
  dataPedido: string;
  itens: PedidoItemResponse[];
}

export interface ApiErrorResponse {
  error: string;
  status: number;
  timestamp: string;
}
