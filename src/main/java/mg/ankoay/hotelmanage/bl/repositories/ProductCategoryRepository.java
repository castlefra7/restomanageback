package mg.ankoay.hotelmanage.bl.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import mg.ankoay.hotelmanage.bl.services.ProductCategory;

public interface ProductCategoryRepository  extends PagingAndSortingRepository<ProductCategory, Integer> {

}
