package team5.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Entity
public class Supplier {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String supplierName;
	private long supplierAddress;
	private String phone;
	private String email;
	
	@OneToOne(mappedBy = "supplier")
	private Product  product;
	
	public Supplier(String supplierName, long supplierAddress, String phone, String email) {
		super();
		this.supplierName = supplierName;
		this.supplierAddress = supplierAddress;
		this.phone = phone;
		this.email = email;
	}
}
