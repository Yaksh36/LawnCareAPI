package patel.yaksh.lawn.Model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String password;
    private String street_address;
    private String city;
    private String state;
    private String postal_code;
    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.HOME_OWNER;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> authorities = new ArrayList<>();

    public User(String name, String email, String password, String street_address, String city, String state, String postal_code, UserType userType, String...authorities) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.street_address = street_address;
        this.city = city;
        this.state = state;
        this.postal_code = postal_code;
        this.userType = userType;
        this.authorities.addAll(Arrays.asList(authorities));
    }

    @Override
    public List<GrantedAuthority> getAuthorities(){
        List<GrantedAuthority> auths = this.authorities.stream().map(s -> (GrantedAuthority) () -> s).collect(Collectors.toList());
        auths.add(new SimpleGrantedAuthority(userType.toString()));
        return auths;
    }

    @Override
    public String getUsername() {
        return this.email;
    }


    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return true;
    }
}
