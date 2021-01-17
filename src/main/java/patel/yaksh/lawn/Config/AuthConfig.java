package patel.yaksh.lawn.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
//        userRepository.save(new User("Admin Userz", "admin",PasswordEncoder().encode("testtest1")
//                , "Yes the address", UserType.ADMIN,"ADMIN" ));
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
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }



}
