export type Brand = string;

export interface Vehicle {
  id: string;
  name: string;
  brand: Brand;
  model?: string;
  year: number;
  engine: string;
  power: number;
  torque: string;
  topSpeed: number;
  acceleration: string;
  price: number;
  image: string;
  gallery: string[];
  description: string;
  mileage?: number;
  category?: string;
  stock?: number;
}

export type SortOption = 'price-asc' | 'price-desc' | 'power-asc' | 'power-desc';

export interface VehicleFilters {
  brand: Brand | null;
  minSpeed: number | null;
  minPrice: number | null;
  maxPrice: number | null;
  sort: SortOption;
}
