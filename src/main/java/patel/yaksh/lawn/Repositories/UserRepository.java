package patel.yaksh.lawn.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import patel.yaksh.lawn.Model.User;

import java.util.List;

@RepositoryRestResource
public interface UserRepository extends CrudRepository<User,Integer> {

    User findByEmail(String email);

    List<User> findAll();
}
