package mg.ankoay.hotelmanage.bl.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import mg.ankoay.hotelmanage.bl.services.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer>  {

}
