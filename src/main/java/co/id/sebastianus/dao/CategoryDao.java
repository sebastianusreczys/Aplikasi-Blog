package co.id.sebastianus.dao;

import co.id.sebastianus.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryDao extends JpaRepository<Category, String> {
    Category findBySlug(String slug);
}
