package mg.ankoay.hotelmanage.bl.services.stats;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "stat_sell_count_by_prod_full", schema = "restomanage")
@Immutable
public class StatSellingCount {
	@Id
	private Integer id;

	private String name;
	private Integer numbers;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getNumbers() {
		return numbers;
	}
}
