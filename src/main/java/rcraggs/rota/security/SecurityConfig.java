package rcraggs.rota.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final RotaUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(RotaUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//.antMatchers("/webpa").hasAnyRole("ADMIN", "TUTOR")

        http.authorizeRequests()
            .antMatchers("/login").permitAll()
            .antMatchers("/").authenticated()
            .and()
            .formLogin().loginPage("/login")
            .defaultSuccessUrl("/")
            .failureUrl("/?error")
            .usernameParameter("username").passwordParameter("password")
            .and()
            .logout()
                .logoutSuccessUrl("/?logout")
                .logoutUrl("/logout")
            .and()
            .exceptionHandling().accessDeniedPage("/login");

            http.csrf().disable();
            http.headers().frameOptions().disable();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }
}
