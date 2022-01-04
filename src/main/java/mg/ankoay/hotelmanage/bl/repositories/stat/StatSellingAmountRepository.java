package mg.ankoay.hotelmanage.bl.repositories.stat;

import java.sql.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import mg.ankoay.hotelmanage.bl.services.stats.StatSellingAmount;

public interface StatSellingAmountRepository extends PagingAndSortingRepository<StatSellingAmount, Integer> {

	@Query(value = "select extract(hour from ord.date_payment), sum(ordDet.amount) as amount from Order ord join OrderDetail ordDet on ord.id_order = ordDet.order.id_order where date(ord.date_payment) = :datepay group by extract(hour from ord.date_payment)")
	Iterable<StatSellingAmount> sellingAmountByHour(@Param("datepay") Date date);

	@Query(value = "select sum(ordDet.amount) as amount from Order ord join OrderDetail ordDet on ord.id_order = ordDet.order.id_order where date(ord.date_payment) = :datepay")
	Double sumSellingAmount(@Param("datepay") Date date);

	@Query(value = "select count(*) as cnt from Order ord where date(ord.date_payment) = :datepay")
	Long sellingCount(@Param("datepay") Date date);

	@Query(value = "select (sum(ordDet.amount) / (select count(*) as cnt from Order ord where date(ord.date_payment) = :datepay)) as amount from Order ord join OrderDetail ordDet on ord.id_order = ordDet.order.id_order where date(ord.date_payment) = :datepay")
	Double avgSellingAmount(@Param("datepay") Date date);
	
	@Query(value= "select cast(date(ord.date_payment) as string), sum(ordDet.amount) as amount from Order ord join OrderDetail ordDet on ord.id_order = ordDet.order.id_order where extract(month from ord.date_payment) = :mnth group by date(ord.date_payment)")
	Iterable<StatSellingAmount> sellingAmountProgress(@Param("mnth") Integer month);
	
	@Query(value = "select ordDet.product.name, sum(ordDet.amount) as amount from Order ord join OrderDetail ordDet on ord.id_order = ordDet.order.id_order where extract(month from ord.date_payment) = :mnth and ord.date_payment is not null group by ordDet.product.name order by sum(ordDet.amount) desc")
	Page<StatSellingAmount> sellingAmountByProd(@Param("mnth") Integer month, Pageable pageable);
	
}
