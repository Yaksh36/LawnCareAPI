package patel.yaksh.lawn.Service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import patel.yaksh.lawn.Config.RabbitConfig;
import patel.yaksh.lawn.Model.User;
import patel.yaksh.lawn.Repositories.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendPasswordMessage(Map<String,String> message) {
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_FORGOT_PASSWORD, message);
    }

    public PasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @CacheEvict(value = "users", allEntries = true)
    public void save(User user){
        if (!user.getPassword().startsWith("$2a")){
            user.setPassword(PasswordEncoder().encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    @Cacheable(value = "users", key = "#id")
    public User findById(int id){
        return userRepository.findById(id).orElse(null);
    }

    @Cacheable(value = "users", key = "#email")
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Cacheable(value = "users")
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @CacheEvict(value = "users", allEntries = true)
    public User patch(User user, int id){
       User temp =  userRepository.findById(id).orElse(null);
       if (temp != null){

           if (user.getName() != null){
               temp.setName(user.getName());
           }

           if (user.getStreet_address() != null){
               temp.setStreet_address(user.getStreet_address());
           }

           if (user.getCity() != null){
               temp.setCity(user.getCity());
           }

           if (user.getState() != null){
               temp.setState(user.getState());
           }

           if (user.getPostal_code() != null){
               temp.setPostal_code(user.getPostal_code());
           }

           if(user.getPassword() != null){
               temp.setPassword(user.getPassword());

               Map<String,String> message = new HashMap<>();
               message.put("email",user.getEmail());
               message.put("body", "Your password has been successfully change to: " + user.getPassword());
               sendPasswordMessage(message);
           }

           userRepository.save(temp);
       }

       return temp;
    }


}
