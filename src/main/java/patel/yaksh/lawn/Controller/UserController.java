package patel.yaksh.lawn.Controller;

import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import patel.yaksh.lawn.Model.User;
import patel.yaksh.lawn.Model.UserNotFoundException;
import patel.yaksh.lawn.Repositories.UserRepository;
import patel.yaksh.lawn.Service.UserService;

import java.util.Map;


@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    private void save(@RequestBody User user){
        userService.save(user);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    private Iterable<User> findAll(){
        return userService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    private User findById(@PathVariable int id){
        User user = userService.findById(id);
        if (user != null){
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
            // Create the email
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
//            mailMessage.setFrom("help@lawncare.com");
            mailMessage.setSubject("Password reset");
            mailMessage.setText("Your password has been reset! The temporary password is" + password);

            user.setPassword(passwordEncoder.encode(password));
            userService.save(user);

            // Send the email
            mailSender.send(mailMessage);
            return "Your password has been reset! The temporary password is" + password;
        }

       return "Password reset failed. Try again";
    }


}
