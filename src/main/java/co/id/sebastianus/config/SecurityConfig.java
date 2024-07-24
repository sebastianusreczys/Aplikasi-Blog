package co.id.sebastianus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private DataSource dataSource;

    private static final String SQL_LOGIN
            = "select u.username as username,p.password as password, active "
            + "from s_user u "
            + "inner join s_user_password p on p.id_user = u.id "
            + "where username = ?";

    private static final String SQL_PERMISSION
            = "select u.username, p.permission_value as authority "
            + "from s_user u "
            + "inner join s_role r on u.id_role = r.id "
            + "inner join s_role_permission rp on rp.id_role = r.id "
            + "inner join s_permission p on rp.id_permission = p.id "
            + "where u.username = ?";


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(this.dataSource);
        manager.setUsersByUsernameQuery(SQL_LOGIN);
        manager.setAuthoritiesByUsernameQuery(SQL_PERMISSION);
        return manager;
    }
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/", "/users/edit","/category/**", "/post/**", "/auth/users/**", "/dashboard/post/upload/**", "/blogs/**", "/js/**", "/img/**", "/css/**").permitAll()
                        .requestMatchers("/dashboard/category/categories").hasAnyAuthority("EDIT_CATEGORY", "HAPUS_CATEGORY", "TAMBAH_CATEGORY")
                        // Izinkan akses ke beberapa endpoint tanpa otentikasi
                        // Semua request lainnya memerlukan otentikasi
                        .anyRequest().authenticated()
                )
                .formLogin((login) -> login
                        .loginPage("/auth/users/login") // Tentukan halaman login kustom
                        .defaultSuccessUrl("/dashboard", true).failureForwardUrl("/login?error=true")
                        .permitAll() // Izinkan akses ke halaman login
                );

        return http.build();
    }
    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }


}
