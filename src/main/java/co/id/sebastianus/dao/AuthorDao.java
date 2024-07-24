package co.id.sebastianus.dao;

import co.id.sebastianus.entity.Author;
import co.id.sebastianus.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorDao extends JpaRepository<Author, String> {
    Author findByUser(User user);
}
