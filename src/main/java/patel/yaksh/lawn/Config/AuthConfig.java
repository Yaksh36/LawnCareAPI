package patel.yaksh.lawn.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import patel.yaksh.lawn.Model.User;
import patel.yaksh.lawn.Model.UserType;
import patel.yaksh.lawn.Repositories.UserRepository;

import javax.annotation.PostConstruct;

@Configuration
@EnableWebSecurity
public class AuthConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

//    @PostConstruct
//    public void init(){
//        userRepository.save(new User("Admin User", "admin@gmail.com",PasswordEncoder().encode("admin")
//                , "123 Main St", "Salt Lake City","Utah", "84111", UserType.ADMIN,"ADMIN" ));
//    }

    @Bean
    public UserDetailsService UserDetailsService() {
        return email -> {
            // TODO Auto-generated method stub
            return userRepository.findByEmail(email);
        };
    }

    @Bean
    public PasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/user/").permitAll()
                .antMatchers("/api/service/**").hasAnyAuthority(UserType.CONTRACTOR.toString(),UserType.ADMIN.toString())
                .antMatchers(HttpMethod.POST,"/api/service/**").hasAnyAuthority(UserType.CONTRACTOR.toString(),UserType.HOME_OWNER.toString(),UserType.ADMIN.toString())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

//        .antMatchers("/marvel/auth").authenticated()
//                .antMatchers(HttpMethod.POST,"/**").hasAnyAuthority("USER","ADMIN")
//                .antMatchers(HttpMethod.PUT,"/**").hasAnyAuthority("USER","ADMIN")
//                .antMatchers(HttpMethod.DELETE,"/**").hasAnyAuthority("USER","ADMIN")
//                .antMatchers("/user/**").hasAuthority("ADMIN")
//                .antMatchers(HttpMethod.GET).permitAll()


}
