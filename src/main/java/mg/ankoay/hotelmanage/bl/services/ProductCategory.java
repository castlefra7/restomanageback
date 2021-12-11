package mg.ankoay.hotelmanage.bl.services;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_categories", schema = "restomanage")
public class ProductCategory extends Base {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private Integer id_affiliate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId_affiliate() {
		return id_affiliate;
	}

	public void setId_affiliate(Integer id_affiliate) {
		this.id_affiliate = id_affiliate;
	}

}
