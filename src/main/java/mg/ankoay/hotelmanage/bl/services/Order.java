package mg.ankoay.hotelmanage.bl.services;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import mg.ankoay.hotelmanage.bl.repositories.OrderRepository;

@Entity
@javax.persistence.Table(name = "orders", schema = "restomanage")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_order;
	private Timestamp date_order;
	private Timestamp date_payment;
	@JsonManagedReference
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderDetail> orderDetails;
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "id_table", nullable = false)
	private TablePlace table;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "id_user", nullable = false)
	private User user;

	private String later_payment;

	private static Logger logger = LoggerFactory.getLogger(Order.class);

	public void update(OrderRepository orderRepository) throws Exception {
		// TODO: Prevent modification when today != this.date_order
		if (this.getOrderDetails().size() <= 0)
			throw new Exception("Veuillez spécifier au moins un produit");
		Optional<Order> ord = orderRepository.findById(this.getId_order());
		if (ord.isPresent()) {
			Order value = ord.get();
			if (value.getDate_payment() == null) {

				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				User user = (User) authentication.getPrincipal();

				value.getOrderDetails().removeIf(order -> {
					boolean wasThere = false;
					for (OrderDetail ordDetail : this.getOrderDetails()) {
						if (ordDetail.getProduct().getId() == order.getProduct().getId()) {
							wasThere = true;
						}
					}
					if (!wasThere)
						order.setOrder(null);

					return !wasThere;
				});

				for (OrderDetail ordDetail : this.getOrderDetails()) {
					OrderDetail foundDtl = null;
					for (OrderDetail ordDetailVal : value.getOrderDetails()) {
						if (ordDetail.getProduct().getId() == ordDetailVal.getProduct().getId()) {
							foundDtl = ordDetailVal;
							break;
						}
					}
					if (foundDtl != null) {
						foundDtl.setQuantity(ordDetail.getQuantity());
						logger.info(String.valueOf(ordDetail.getAmount()));
						foundDtl.setAmount(ordDetail.getAmount());
					} else {
						value.getOrderDetails().add(ordDetail);
					}
				}
				// TODO: Add column that inserts who updates the order
				value.setUser(this.getUser());
				value.setTable(this.getTable());

				logger.info("ATOOOOOO: " + this.getLater_payment());
				if (this.getLater_payment() == null || this.getLater_payment().trim().isEmpty()) {
					value.setLater_payment(null);
				} else {
					value.setLater_payment(this.getLater_payment());
				}
				orderRepository.save(value);
			}
		}
	}

	public void pay(OrderRepository orderRepository) {

		Optional<Order> ord = orderRepository.findById(this.getId_order());
		if (ord.isPresent()) {
			Order value = ord.get();
			if (value.getDate_payment() == null) {
				Date now = new Date();
				value.setDate_payment(new Timestamp(now.getTime()));
				orderRepository.save(value);
			}
		}
	}

	public String getLater_payment() {
		return later_payment;
	}

	public void setLater_payment(String later_payment) {
		this.later_payment = later_payment;
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

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
