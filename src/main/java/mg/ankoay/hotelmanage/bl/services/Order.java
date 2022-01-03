package mg.ankoay.hotelmanage.bl.services;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import mg.ankoay.hotelmanage.bl.repositories.OrderRepository;

@Entity
@javax.persistence.Table(name = "orders", schema = "restomanage")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_order;
// TODO: Order by date_order	
	private Timestamp date_order;
	private Timestamp date_payment;
	@JsonManagedReference
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private Set<OrderDetail> orderDetails;
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "id_table", nullable = false)
	private TablePlace table;
	
	private Integer id_user;
	
	public Integer getId_user() {
		return id_user;
	}

	public void setId_user(Integer id_user) {
		this.id_user = id_user;
	}

	public void pay(OrderRepository orderRepository) throws Exception {
		
		Optional<Order> ord = orderRepository.findById(this.getId_order());
		if(ord.isPresent()) { 
			Order value = ord.get();
			Date now = new Date();
			value.setDate_payment(new Timestamp(now.getTime()));
			orderRepository.save(value);
		} else {
			throw new Exception("Veuillez spécifier une commande valide");
		}
	}

	public TablePlace getTable() {
		return table;
	}

	public void setTable(TablePlace table) {
		this.table = table;
	}

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
