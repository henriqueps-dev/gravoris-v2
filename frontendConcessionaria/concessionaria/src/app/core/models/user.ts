export interface AuthSession {
  userId: string;
  email: string;
  fullName: string;
  token: string;
  remember: boolean;
}
