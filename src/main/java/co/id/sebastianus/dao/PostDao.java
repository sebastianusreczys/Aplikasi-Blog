package co.id.sebastianus.dao;

import co.id.sebastianus.entity.Author;
import co.id.sebastianus.entity.Category;
import co.id.sebastianus.entity.Post;
import co.id.sebastianus.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostDao extends JpaRepository<Post, String> {


    Optional<Post> findBySlug(String slug);

    Page<Post> findByCategory(Category category, Pageable pageable);

    @Modifying
    @Query("DELETE FROM Post p WHERE p.id = :id")
    void deleteById(@Param("id") String id);

    Page<Post> findByAuthor(Author author, Pageable pageable);

    @Query("SELECT p.category.name, COUNT(p) FROM Post p GROUP BY p.category.name")
    List<Object[]> countPostsByCategory();

    @Query("SELECT COUNT(p) FROM Post p WHERE p.author.id = :authorId")
    long countPostsByAuthorId(@Param("authorId") String authorId);

    List<Post> findByTitleContainingIgnoreCase(String title);
}
