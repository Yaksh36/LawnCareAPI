package patel.yaksh.lawn.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import patel.yaksh.lawn.Model.Service;
import patel.yaksh.lawn.Service.Service_service;

@RestController
@RequestMapping(path = "/service")
public class ServiceController {

    @Autowired
    private Service_service service;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public Service save(@RequestBody Service s){
        service.save(s);
        return s;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Service findById(@PathVariable int id){
        Service s = service.findById(id).orElse(null);
        return s;
    }
}
