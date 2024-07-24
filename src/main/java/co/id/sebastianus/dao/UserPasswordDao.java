package co.id.sebastianus.dao;

import co.id.sebastianus.entity.User;
import co.id.sebastianus.entity.UserPassword;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserPasswordDao extends CrudRepository<UserPassword, String> {
    UserPassword findByUser(User user);
}
