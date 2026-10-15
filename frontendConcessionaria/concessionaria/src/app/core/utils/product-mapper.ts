import { ProdutoResponse } from '../models/api';
import { Vehicle } from '../models/vehicle';

const DEFAULT_IMAGE =
  'https://images.unsplash.com/photo-1492144534655-ae79c964c9d7?auto=format&fit=crop&w=1200&q=80';

export function mapProdutoToVehicle(produto: ProdutoResponse): Vehicle {
  const image = produto.imageUrl?.startsWith('http') ? produto.imageUrl : DEFAULT_IMAGE;

  return {
    id: String(produto.id),
    name: produto.name,
    brand: produto.brand,
    model: produto.model,
    year: produto.year,
    engine: produto.engine || `${produto.brand} ${produto.model}`,
    power: produto.power,
    torque: produto.torque || '—',
    topSpeed: produto.topSpeed,
    acceleration: produto.acceleration || '—',
    price: Number(produto.price),
    image,
    gallery: [image],
    description: produto.description,
    mileage: produto.mileage,
    category: produto.category,
    stock: produto.stock
  };
}
