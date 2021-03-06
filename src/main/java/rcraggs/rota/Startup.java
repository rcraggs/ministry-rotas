package rcraggs.rota;


import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import rcraggs.rota.model.User;
import rcraggs.rota.repository.UserRepository;

@Component
public class Startup implements ApplicationRunner {

    final
    UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public Startup(UserRepository repository) {
        this.repository = repository;
    }

    public void run(ApplicationArguments args) {

        // Add 10 default users and admins
        for (int i=1; i<=10; i++){
            User admin = new User("admin" + i, passwordEncoder.encode("admin123"), User.UserRole.ADMIN);
            admin.setSurname("Surname" + i);
            admin.setForename("Forename" + i);
            admin.setEmail("admin"+i+"@example.com");

            repository.save(admin);
        }

        for (int i=1; i<=10; i++){
            User admin = new User("user" + i, passwordEncoder.encode("user123"), User.UserRole.USER);
            admin.setSurname("UserSurname" + i);
            admin.setForename("UserForename" + i);
            admin.setEmail("user"+i+"@example.com");
            repository.save(admin);
        }
    }
}