package co.id.sebastianus.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "s_role")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String description;

    private String name;
}
