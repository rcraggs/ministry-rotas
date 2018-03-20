package rcraggs.rota.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rcraggs.rota.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    List<User> findUsersByRole(User.UserRole role);
    User findById(long id);
}