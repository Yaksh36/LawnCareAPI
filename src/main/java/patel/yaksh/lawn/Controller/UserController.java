package patel.yaksh.lawn.Controller;

import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import patel.yaksh.lawn.Model.User;
import patel.yaksh.lawn.Model.UserNotFoundException;
import patel.yaksh.lawn.Service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final static Logger logger = Logger.getLogger(UserController.class.getName());

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    private void save(@RequestBody User user){
        userService.save(user);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    private List<User> findAll(){
        logger.info("Grabbing all the users...");
        List<User> users = userService.findAll();
        logger.info("Grabbed " + users.size() + " users");
        return userService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    private User findById(@PathVariable int id){
        logger.info("Grabbing user by id " + id);
        User user = userService.findById(id);
        if (user != null){
            logger.info("Grabbed user " + user.getEmail());
            return user;
        }else {
            throw new UserNotFoundException();
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{id}")
    private void partialUpdate(@RequestBody User user,@PathVariable int id){
        User result = userService.patch(user,id);
        if (result == null){
            throw new UserNotFoundException();
        }
    }

    @PostMapping("/forgotPassword")
    public String forgotPassword(@RequestBody Map<String,Object> payload){

        String email = payload.get("email").toString();
        User user = userService.findByEmail(email);

        if (user != null){

            RandomString rs =new RandomString();
            String password = rs.nextString();
            user.setPassword(passwordEncoder.encode(password));
            userService.save(user);
            Map<String,String> message = new HashMap<>();
            message.put("email",email);
            message.put("body", "Your password has been reset! The temporary password is: " + password);

            //Send RabbitMQ message
            userService.sendPasswordMessage(message);

            return "Your password has been reset! The temporary password is " + password ;
        }

       return "Password reset failed. Try again";
    }


}
