package user.sec.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import user.sec.user.models.ERole;
import user.sec.user.models.Role;

import java.util.Optional;
@CrossOrigin(origins = "*", maxAge = 3600)
public interface RoleRepository extends JpaRepository<Role,Long> {

Optional<Role> findByName(ERole name);

}
