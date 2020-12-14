package team5.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class UsageRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private Date date;
	@OneToOne
	private User user;
	@OneToOne
	private Customer customer;
	private String carPlate;
	@OneToMany(mappedBy = "usageRecord")
	private List<UsageRecordDetail> usageRecordDetail;
	
	public UsageRecord() {
		super();
	}
	
	public UsageRecord(Customer customer, String carPlate, Date date, User user) {
		super();
		this.customer = customer;
		this.carPlate = carPlate;
		this.date = date;
		this.user=user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getCarPlate() {
		return carPlate;
	}

	public void setCarPlate(String carPlate) {
		this.carPlate = carPlate;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<UsageRecordDetail> getUsageRecordDetail() {
		return usageRecordDetail;
	}

	public void setUsageRecordDetail(List<UsageRecordDetail> usageRecordDetail) {
		this.usageRecordDetail = usageRecordDetail;
	}

	@Override
	public String toString() {
		return "UsageRecord [id=" + id + ", date=" + date + ", user=" + user + ", customer=" + customer + ", carPlate="
				+ carPlate + ", usageRecordDetail=" + usageRecordDetail + "]";
	}

	
}
