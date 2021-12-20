package mg.ankoay.hotelmanage.bl.services;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "open_cashiers", schema = "restomanage")
public class OpenCashier {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Timestamp date_open;
	private Double fund;
	private Integer id_user;
	private Timestamp date_closed;
	
	// is already open

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getDate_open() {
		return date_open;
	}

	public void setDate_open(Timestamp date_open) {
		this.date_open = date_open;
	}

	public Double getFund() {
		return fund;
	}

	public void setFund(Double fund) {
		this.fund = fund;
	}

	public Integer getId_user() {
		return id_user;
	}

	public void setId_user(Integer id_user) {
		this.id_user = id_user;
	}

	public Timestamp getDate_closed() {
		return date_closed;
	}

	public void setDate_closed(Timestamp date_closed) {
		this.date_closed = date_closed;
	}

}
