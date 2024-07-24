package co.id.sebastianus.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "s_user_password")
@Data
public class UserPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "id_user")
    private User user;

    private String password;

}
