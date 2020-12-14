package team5.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team5.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("Select u from User u where u.userName LIKE :un")
	public User findUserByUserName(@Param("un") String un);
}
