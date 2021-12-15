package mg.ankoay.hotelmanage.bl.repositories;

import org.springframework.data.repository.CrudRepository;

import mg.ankoay.hotelmanage.bl.services.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {
	
}
