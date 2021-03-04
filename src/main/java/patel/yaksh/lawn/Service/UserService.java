package patel.yaksh.lawn.Service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import patel.yaksh.lawn.Config.RabbitConfig;
import patel.yaksh.lawn.Model.User;
import patel.yaksh.lawn.Repositories.UserRepository;

import java.util.HashMap;
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

    public void save(User user){
        if (!user.getPassword().startsWith("$2a")){
            user.setPassword(PasswordEncoder().encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    public User findById(int id){
        return userRepository.findById(id).orElse(null);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public Iterable<User> findAll(){
        return userRepository.findAll();
    }

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
