package mg.ankoay.hotelmanage.bl.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import mg.ankoay.hotelmanage.bl.services.OrderDetail;

public interface OrderDetailRepository extends PagingAndSortingRepository<OrderDetail, Integer> {
	//long deleteById_order(long idOrder);
}
