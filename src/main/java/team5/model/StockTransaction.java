package team5.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class StockTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;	// StockTransationId
	
	@ManyToOne
	private Product product;
	
	@ManyToOne
	private  UsageRecord usageRecord;	// to be set as null be default 
	
	private long qtyChange;	// absolute value of quantity change 
	
	private String type;	// a hidden field. "restock"(+) and "use"(-) only
	
	public StockTransaction() {
		super();
	}

	// combination of parameters means that stock is withdrawn
	public StockTransaction(long id, Product product, UsageRecord usageRecord, long qtyChange) {
		super();
		this.id = id;
		this.product = product;
		this.usageRecord = usageRecord;
		this.qtyChange = qtyChange;
		this.type = "use";
	}

	// combination of parameters means that stock is withdrawn
	public StockTransaction(Product product, UsageRecord usageRecord, long qtyChange) {
		super();
		this.product = product;
		this.usageRecord = usageRecord;
		this.qtyChange = qtyChange;
		this.type = "use";
	}

	// combination of parameters means that stock is added
	public StockTransaction(long id, Product product, long qtyChange) {
		super();
		this.id = id;
		this.product = product;
		this.qtyChange = qtyChange;
		this.type = "restock";
	}

	// combination of parameters means that stock is added
	public StockTransaction(Product product, long qtyChange) {
		super();
		this.product = product;
		this.qtyChange = qtyChange;
		this.type = "restock";
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getQuantityUsed() {
		return qtyChange;
	}
	public void setQuantityUsed(long quantityUsed) {
		this.qtyChange = quantityUsed;
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
