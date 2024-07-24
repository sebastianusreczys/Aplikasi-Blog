package co.id.sebastianus.dao;

import co.id.sebastianus.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, String> {
    User findByUsername(String username);
}
