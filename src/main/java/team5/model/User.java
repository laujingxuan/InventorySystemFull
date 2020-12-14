package team5.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;



@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	private String userName;
	private String password;
	
	private RoleType role;
	
	public User() {};
	
	public User(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	public User(String userName, String password, RoleType role) {
		super();
		this.userName = userName;
		this.password = password;
		this.role = role;
	}
	
    public long getId() { return id; }
	public void setId(long id) { this.id = id; }
	
	public String getUserName() { return userName; }
	public void setUserName(String userName) { this.userName = userName; }
	
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	
	public RoleType getRole() { return role; }
	public void setRole(RoleType role) { this.role = role; }

}
