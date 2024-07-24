package co.id.sebastianus.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "categories")
@Data
@JsonIgnoreProperties("posts")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36)
    private String id;

    @Column(length = 100, nullable = false)
    private String name;

    private String slug;

    private String picture;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category" , orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference // Breaks the circular reference
    private List<Post> posts ;

}
