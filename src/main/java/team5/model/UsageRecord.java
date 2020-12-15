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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String customerId;
	private String carPlate;
	
	
	@DateTimeFormat (pattern="dd-MM-yyyy")
	private LocalDate date;
	
	private String comments;
	

	@OneToOne
	private User user;
	
	@OneToMany(mappedBy = "usageRecord")
	private List<UsageRecordDetail> usageRecordDetail;
	
	public UsageRecord() {
		super();
	}
	
	public UsageRecord(String customerId, String carPlate, LocalDate date, List<UsageRecordDetail> usageRecordDetail) {
		super();
		this.customerId = customerId;
		this.carPlate = carPlate;
		this.date = date;
		this.usageRecordDetail = usageRecordDetail;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCarPlate() {
		return carPlate;
	}

	public void setCarPlate(String carPlate) {
		this.carPlate = carPlate;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	@Override
	public String toString() {
		return "UsageRecord [id=" + id + ", customerId=" + customerId + ", carPlate=" + carPlate + ", date=" + date
				+ ", usageRecordDetail=" + usageRecordDetail + "]";
	}

	
}
