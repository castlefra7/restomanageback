package mg.ankoay.hotelmanage.bl.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import mg.ankoay.hotelmanage.bl.services.Affiliate;


// TODO: why Integer
public interface AffiliateRepository extends PagingAndSortingRepository<Affiliate, Integer> {

}
