
package team5.repo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team5.model.RoleType;
import team5.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
	public User findByUserName(String userName);
	@Query("Select u from User u where u.userName LIKE :un")
	public User findUserByUserName(@Param("un") String un);
	public ArrayList<User> findByRole(RoleType roleType);
}

