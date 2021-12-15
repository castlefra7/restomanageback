package mg.ankoay.hotelmanage.bl.services;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "orders", schema = "restomanage")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_order;
// TODO: Order by date_order	
	private Timestamp date_order;
	private Integer id_table;
	private Timestamp date_payment;
	@JsonManagedReference
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private Set<OrderDetail> orderDetails;

	public Integer getId_order() {
		return id_order;
	}

	public void setId_order(Integer id_order) {
		this.id_order = id_order;
	}

	public Timestamp getDate_order() {
		return date_order;
	}

	public void setDate_order(Timestamp date_order) {
		this.date_order = date_order;
	}

	public Integer getId_table() {
		return id_table;
	}

	public void setId_table(Integer id_table) {
		this.id_table = id_table;
	}

	public Timestamp getDate_payment() {
		return date_payment;
	}

	public void setDate_payment(Timestamp date_payment) {
		this.date_payment = date_payment;
	}

	public Set<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

}
