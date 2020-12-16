package team5.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.FutureOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class UsageRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String carPlate;
	private String customerName;

	@DateTimeFormat (pattern="dd-MM-yyyy")
	private Date date;

	private String comments;
	private String userName;
	@OneToOne
	private Customer customer;

	@OneToMany(mappedBy = "usageRecord")
	private List<UsageRecordDetail> usageRecordDetail;

	public UsageRecord() {
		super();
	}

	public UsageRecord(String carPlate, String customerName, Date date, String comments, String userName,
			Customer customer, List<UsageRecordDetail> usageRecordDetail) {
		super();
		this.carPlate = carPlate;
		this.customerName = customerName;
		this.date = date;
		this.comments = comments;
		this.userName = userName;
		this.customer = customer;
		this.usageRecordDetail = usageRecordDetail;
	}

	public UsageRecord(Customer customer, String carPlate, Date date, User user) {
		super();
		this.customer = customer;
		this.carPlate = carPlate;
		this.date = date;
		this.userName=user.getUserName();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(User user) {
		this.userName = user.getUserName();
	}
	
	

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "UsageRecord [id=" + id + ", carPlate=" + carPlate + ", customerName=" + customerName + ", date=" + date
				+ ", comments=" + comments + ", userName=" + userName + ", customer=" + customer
				+ ", usageRecordDetail=" + usageRecordDetail + "]";
	}

	
}
