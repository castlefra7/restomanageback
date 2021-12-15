package mg.ankoay.hotelmanage.bl.repositories;

import org.springframework.data.repository.CrudRepository;

import mg.ankoay.hotelmanage.bl.services.OrderDetail;

public interface OrderDetailRepository extends CrudRepository<OrderDetail, Integer> {
	
}
