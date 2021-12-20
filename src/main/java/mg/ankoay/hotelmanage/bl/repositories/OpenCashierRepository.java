package mg.ankoay.hotelmanage.bl.repositories;

import org.springframework.data.repository.CrudRepository;

import mg.ankoay.hotelmanage.bl.services.OpenCashier;

public interface OpenCashierRepository extends CrudRepository<OpenCashier, Integer> {
	
}
