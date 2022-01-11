package mg.ankoay.hotelmanage.bl.repositories;

import java.sql.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import mg.ankoay.hotelmanage.bl.services.Expense;

public interface ExpenseRepository extends CrudRepository<Expense, Integer> {
	@Query(value= "SELECT o FROM Expense o where date(date_expense) = :dt order by date_expense desc")
	Iterable<Expense> findAllByDate(@Param("dt") Date date);
	
}
