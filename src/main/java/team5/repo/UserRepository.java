package team5.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import team5.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
	public User findUserByUserName(String un);
}
