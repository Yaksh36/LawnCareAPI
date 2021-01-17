package patel.yaksh.lawn.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import patel.yaksh.lawn.Repositories.ServiceRepository;

import java.util.Optional;

@Service
public class Service_service {

    @Autowired
    private ServiceRepository repository;

    public Optional<patel.yaksh.lawn.Model.Service> findById(int id){
        return repository.findById(id);
    }

    public void save(patel.yaksh.lawn.Model.Service service){
        repository.save(service);
    }

}
