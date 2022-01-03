package mg.ankoay.hotelmanage.bl.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import mg.ankoay.hotelmanage.bl.services.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {
	@Query(value = "SELECT o FROM Order o where date_payment is not null")
	Iterable<Order> findAllPaidOrders();
	
	@Query(value = "SELECT o FROM Order o where date_payment is null")
	Iterable<Order> findAllUnpaidOrders();
}
