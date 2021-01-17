package patel.yaksh.lawn.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import patel.yaksh.lawn.Model.User;
import patel.yaksh.lawn.Repositories.UserRepository;
import patel.yaksh.lawn.Service.UserService;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    private User save(User user){
        userService.save(user);
        return user;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    private Iterable<User> findAll(){
        return userService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    private User findById(@PathVariable int id){
        return userService.findById(id);
    }

}
