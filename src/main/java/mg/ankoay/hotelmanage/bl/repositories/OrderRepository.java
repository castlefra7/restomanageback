package mg.ankoay.hotelmanage.bl.repositories;

import java.sql.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import mg.ankoay.hotelmanage.bl.services.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {
	
	@Query(value= "SELECT o FROM Order o where later_payment is not null and date_payment is null and date(date_order) = :dt")
	Iterable<Order> findAllUnPaidLaterPays(@Param("dt") Date date);
	
	@Query(value = "SELECT o FROM Order o where date_payment is not null and date(date_order) = :dt order by date_payment desc")
	Iterable<Order> findAllPaidOrders(@Param("dt") Date date);
	
	@Query(value = "SELECT ord FROM Order ord where date_payment is null and date(date_order) = :dt order by date_order desc")
	Iterable<Order> findAllUnpaidOrders(@Param("dt") Date date);
}
