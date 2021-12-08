import './styles.css';
import ProductPrice from 'components/ProductPrice';
import { Product } from 'types/product';
import CategoryBadge from '../CategoryBadge';
import { Link } from 'react-router-dom';

type Props = {
  product: Product;
};

const ProductCrudCard = ({ product }: Props) => {
  return (
    <div className="product-crud-container">
      <div className="base-card product-crud-card">
        <div className="product-crud-card-top-container">
          <img src={product.imgUrl} alt={product.name} />
        </div>
        <div className="product-crud-card-bottom-description">
          <div className="product-crud-card-bottom-container">
            <h6>{product.name}</h6>
            <ProductPrice price={product.price} />
          </div>
          <div className="product-crud-categories-container">
            {product.categories.map((category) => (
              <CategoryBadge name={category.name} key={category.id} />
            ))}
          </div>
        </div>
        <div className="product-crud-card-buttons-container">
          <button className="btn btn-outline-danger product-crud-card-button product-crud-card-button-first">
            EXCLUIR
          </button>
          <Link to={`/admin/products/${product.id}`}>
            <button className="btn btn-outline-secondary product-crud-card-button">
              EDITAR
            </button>
          </Link>
        </div>
      </div>
    </div>
  );
};

export default ProductCrudCard;
