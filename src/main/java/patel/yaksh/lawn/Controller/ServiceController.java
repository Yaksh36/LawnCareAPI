package patel.yaksh.lawn.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import patel.yaksh.lawn.Model.ServiceNotFoundException;
import patel.yaksh.lawn.Model.ServiceRequest;
import patel.yaksh.lawn.Service.ServiceRequestService;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/service")
public class ServiceController {

    @Autowired
    private ServiceRequestService service;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public void save(@RequestBody ServiceRequest s){
        service.save(s);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ServiceRequest findById(@PathVariable int id){
        ServiceRequest s = service.findById(id).orElse(null);
        if (s!=null) {
            s.add(linkTo(methodOn(ServiceController.class).findById(id)).withSelfRel());
            s.add(linkTo(methodOn(ServiceController.class).findAllOpenRequests("", "", "")).withRel("allOpenRequests"));
            return s;
        }else {
            throw new ServiceNotFoundException();
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/openRequests")
    public CollectionModel<ServiceRequest> findAllOpenRequests(@RequestParam(required = false) String city, @RequestParam(required = false) String state, @RequestParam(required = false) String postal){
        if (city != null && state != null){
            return wrapWithLinks(service.getOpenRequestsByCityAndState("%"+city+"%", "%"+state+"%"));
        }else if (postal != null){
            return wrapWithLinks(service.getOpenRequestsByPostal("%"+postal+"%"));
        }else {
            return wrapWithLinks(service.getOpenRequests());
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{serviceId}/claim/{providerId}")
    public void claimService(@PathVariable int serviceId, @PathVariable int providerId){
        service.claimService(serviceId, providerId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{serviceId}/complete")
    public void markComplete(@PathVariable int serviceId){
         service.markComplete(serviceId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }

    public CollectionModel<ServiceRequest> wrapWithLinks(List<ServiceRequest> serviceRequests){

        serviceRequests.forEach(serviceRequest ->
                serviceRequest.add(linkTo(methodOn(ServiceController.class).findById(serviceRequest.getId())).withSelfRel()));

        Link link = linkTo(ServiceController.class).slash("openRequests").withSelfRel();
        CollectionModel<ServiceRequest> result = new CollectionModel<>(serviceRequests, link);
        return result;
    }
}
