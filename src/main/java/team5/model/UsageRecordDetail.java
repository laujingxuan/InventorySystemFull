package team5.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class UsageRecordDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	private Product product;
	
	@ManyToOne
	private  UsageRecord usageRecord;
	
	
	private int quantityUsed;
	
	
	public UsageRecordDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UsageRecordDetail(Product product, UsageRecord usageRecord, int quantityUsed) {
		super();
		this.product = product;
		this.usageRecord = usageRecord;
		this.quantityUsed = quantityUsed;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getQuantityUsed() {
		return quantityUsed;
	}
	public void setQuantityUsed(int quantityUsed) {
		this.quantityUsed = quantityUsed;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public UsageRecord getUsageRecord() {
		return usageRecord;
	}
	public void setUsageRecord(UsageRecord usageRecord) {
		this.usageRecord = usageRecord;
	}
	
}
