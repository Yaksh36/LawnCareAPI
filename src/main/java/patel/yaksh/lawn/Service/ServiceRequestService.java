package patel.yaksh.lawn.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import patel.yaksh.lawn.Model.ServiceNotFoundException;
import patel.yaksh.lawn.Model.ServiceRequest;
import patel.yaksh.lawn.Repositories.ServiceRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceRequestService {

    @Autowired
    private ServiceRepository repository;

    public Optional<ServiceRequest> findById(int id){
        return repository.findById(id);
    }

    public void save(ServiceRequest serviceRequest){
        repository.save(serviceRequest);
    }

    public List<ServiceRequest> getOpenRequests(){
        return repository.findAllByisAcceptedIsFalse();
    }

    public List<ServiceRequest> getOpenRequestsByPostal(String postal){
        return repository.findByPostalCode(postal);
    }

    public List<ServiceRequest> getOpenRequestsByCityAndState(String city, String state){
        return repository.findByCityAndState(city, state);
    }

    public void markComplete(int serviceId){
        ServiceRequest serviceRequest = repository.findById(serviceId).orElse(null);
        if (serviceRequest != null){
            serviceRequest.setCompleted(true);
            repository.save(serviceRequest);
        }else {
            throw new ServiceNotFoundException();
        }
    }

    public void claimService(int serviceId, int providerId){
        ServiceRequest serviceRequest = repository.findById(serviceId).orElse(null);
        if (serviceRequest != null && serviceRequest.getProviderId() == -1) {
            serviceRequest.setAccepted(true);
            serviceRequest.setProviderId(providerId);
            repository.save(serviceRequest);
        }else {
            throw new ServiceNotFoundException();
        }
    }

    public void delete(int id){
        repository.deleteById(id);
    }

}
