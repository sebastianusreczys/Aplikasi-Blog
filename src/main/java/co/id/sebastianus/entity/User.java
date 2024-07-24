package co.id.sebastianus.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "s_user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotEmpty(message = "harap diisi")
    private String username;
    private boolean active;


    // Relasi ke entitas UserPassword
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserPassword userPassword;

    // Relasi ke entitas Authors
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Author author;


    @ManyToOne
    @JoinColumn(name = "id_role")
    private Role role;
}
