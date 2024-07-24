package co.id.sebastianus.dao;

import co.id.sebastianus.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, String> {

}
