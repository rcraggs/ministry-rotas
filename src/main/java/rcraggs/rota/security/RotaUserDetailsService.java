package rcraggs.rota.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rcraggs.rota.model.User;
import rcraggs.rota.repository.UserRepository;

@Service
public class RotaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public RotaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new RotaUserDetails(user);
    }
}