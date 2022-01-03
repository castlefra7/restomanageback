package mg.ankoay.hotelmanage.bl.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import mg.ankoay.hotelmanage.bl.services.ProductCategory;

public interface ProductCategoryRepository  extends PagingAndSortingRepository<ProductCategory, Integer> {
	Page<ProductCategory> findByNameLike(String name, Pageable pageable);
	long countByNameLike(String name);
}
