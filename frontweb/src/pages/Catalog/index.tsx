import axios, { AxiosRequestConfig } from 'axios';
import Pagination from 'components/Pagination';
import ProductCard from 'components/ProductCard';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { Product } from 'types/product';
import { SpringPage } from 'types/vendor/spring';
import { BASE_URL } from 'utils/requests';
import CardLoader from './CardLoader';
import './styles.css';

const Catalog = () => {
  const [page, setPage] = useState<SpringPage<Product>>();
  const [isLoading, setIsloading] = useState(false);

  useEffect(() => {
    const params: AxiosRequestConfig = {
      method: 'GET',
      url: '/products',
      baseURL: BASE_URL,
      params: {
        page: 0,
        size: 12,
      },
    };

    setIsloading(true);
    axios(params)
      .then((response) => {
        setPage(response.data);
      })
      .finally(() => {
        setIsloading(false);
      });
  }, []);

  return (
    <div className="container my-4 catalog-container">
      <div className="row catalog-title-container">
        <h1>Catálogo de Produtos</h1>
      </div>
      <div className="row">
        {isLoading ? (
          <CardLoader />
        ) : (
          page?.content.map((product) => (
            <div className="col-sm-6 col-lg-4 col-xl-3" key={product.id}>
              <Link to="/products/1">
                <ProductCard product={product} />
              </Link>
            </div>
          ))
        )}
      </div>
      <div className="row">
        <Pagination />
      </div>
    </div>
  );
};

export default Catalog;
