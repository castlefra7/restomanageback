package mg.ankoay.hotelmanage.bl.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import mg.ankoay.hotelmanage.bl.services.User;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
	User findByName(String username);
}
