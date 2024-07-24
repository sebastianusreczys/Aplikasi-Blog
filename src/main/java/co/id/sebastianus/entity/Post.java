package co.id.sebastianus.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "posts")
@Data
@JsonIgnoreProperties("category")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;

    private String slug;

    private String quote;

    @Column(columnDefinition = "TEXT")
    private String body;

    private String photo;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate created_at = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "id_category")
    @JsonBackReference // Breaks the circular reference
    private Category category;

    @ManyToOne
    @JoinColumn(name = "id_author")
    @JsonBackReference
    private Author author;

}
