package patel.yaksh.lawn.Service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import patel.yaksh.lawn.Config.RabbitConfig;
import patel.yaksh.lawn.Model.ServiceNotFoundException;
import patel.yaksh.lawn.Model.ServiceRequest;
import patel.yaksh.lawn.Model.User;
import patel.yaksh.lawn.Repositories.ServiceRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ServiceRequestService {

    @Autowired
    private ServiceRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendServiceMessageToRequester(Map<String,String> message) {
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_SERVICE, message);
    }

    public void sendServiceMessageToRequesterAndProvider(Map<String,String> message) {
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_SERVICE, message);
    }


    public Optional<ServiceRequest> findById(int id){
        return repository.findById(id);
    }

    public void save(ServiceRequest serviceRequest){
        repository.save(serviceRequest);
        Map<String,String> message = new HashMap<>();
        User requester = userService.findById(serviceRequest.getRequesterId());
        if (requester != null) {
            message.put("email", requester.getEmail());
            message.put("body", "Your requested service has been created! The id of the request is: " + serviceRequest.getId());
            sendServiceMessageToRequester(message);
        }
    }

    public List<ServiceRequest> getOpenRequests(){
        return repository.findAllByAcceptedIsFalseAndCompletedIsFalse();
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

            //Send message
            Map<String,String> message = new HashMap<>();
            User provider = userService.findById(serviceRequest.getProviderId());
            User requester = userService.findById(serviceRequest.getRequesterId());
            if (requester != null && provider != null) {

                message.put("subject","Service Completed!");
                message.put("requesterEmail", requester.getEmail());
                message.put("requesterBody", String.format("Your service has been completed by %s and their contact is %s.",
                        provider.getName(), provider.getEmail()));

                message.put("providerEmail", provider.getEmail());
                message.put("providerBody", "You have marked the service as completed");

                sendServiceMessageToRequesterAndProvider(message);
            }
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

            //Send message
            Map<String,String> message = new HashMap<>();
            User provider = userService.findById(providerId);
            User requester = userService.findById(serviceRequest.getRequesterId());
            if (requester != null && provider != null) {

                message.put("subject","Service Accepted!");
                message.put("requesterEmail", requester.getEmail());
                message.put("requesterBody", String.format("Your service has been accepted by %s and their contact is %s. Your contact information has also been forwarded to the provider",
                        provider.getName(), provider.getEmail()));

                message.put("providerEmail", provider.getEmail());
                message.put("providerBody", String.format("You have accepted service for %s and their contact is %s. Your contact information has also been forwarded to the requester",
                        requester.getName(), requester.getEmail()));

                sendServiceMessageToRequesterAndProvider(message);
            }

        }else {
            throw new ServiceNotFoundException();
        }
    }

    public void delete(int id){
        repository.deleteById(id);
    }

}
