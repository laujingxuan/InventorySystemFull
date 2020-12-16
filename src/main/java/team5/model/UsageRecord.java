package team5.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class UsageRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String carPlate;

	@DateTimeFormat (pattern="dd-MM-yyyy")
	private Date date;

	private String comments;
	private String userName;
	private String customerName;


	@OneToMany(mappedBy = "usageRecord")
	private List<UsageRecordDetail> usageRecordDetail;

	public UsageRecord() {
		super();
	}


	public UsageRecord(String carPlate, Date date, String userName, String customerName
			) {
		super();
		this.carPlate = carPlate;
		this.date = date;
		this.userName = userName;
		this.customerName = customerName;

	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "UsageRecord [id=" + id + ", carPlate=" + carPlate + ", date=" + date + ", comments=" + comments
				+ ", userName=" + userName + ", customerName=" + customerName + ", usageRecordDetail="
				+ usageRecordDetail + "]";
	}



}
