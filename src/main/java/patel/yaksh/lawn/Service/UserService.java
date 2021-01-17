package patel.yaksh.lawn.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import patel.yaksh.lawn.Model.User;
import patel.yaksh.lawn.Repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void save(User user){
        userRepository.save(user);
    }

    public User findById(int id){
        return userRepository.findById(id).orElse(null);
    }

    public Iterable<User> findAll(){
        return userRepository.findAll();
    }
}
