package rcraggs.rota.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import rcraggs.rota.model.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class RotaUserDetails implements UserDetails {

    private User user;

    public RotaUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        final Set<GrantedAuthority> auths = new HashSet<>();

        if (user!=null) {
            auths.add(new SimpleGrantedAuthority(user.getRoleAsString()));
        }
        return auths;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}