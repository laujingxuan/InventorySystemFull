package team5.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class UsageRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String customerId;
	private String carPlate;
	private Date date;
	
	@OneToMany(mappedBy = "usageRecord")
	private List<UsageRecordDetail> usageRecordDetail;
	
	public UsageRecord() {
		super();
	}
	
	public UsageRecord(String customerId, String carPlate, Date date, List<UsageRecordDetail> usageRecordDetail) {
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
		return "UsageRecord [id=" + id + ", customerId=" + customerId + ", carPlate=" + carPlate + ", date=" + date
				+ ", usageRecordDetail=" + usageRecordDetail + "]";
	}

	
}
