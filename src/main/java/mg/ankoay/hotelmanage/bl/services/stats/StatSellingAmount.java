package mg.ankoay.hotelmanage.bl.services.stats;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "stat_sell_amount_by_prod_full", schema = "restomanage")
@Immutable
public class StatSellingAmount {
	@Id
	private Integer id;

	private String name;
	private Double amount;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Double getAmount() {
		return amount;
	}

}
