
package team5.repo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team5.model.RoleType;
import team5.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
	public User findByUserName(String userName);
	public ArrayList<User> findByRole(RoleType roleType);
}

