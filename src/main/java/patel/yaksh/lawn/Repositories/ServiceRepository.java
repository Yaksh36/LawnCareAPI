package patel.yaksh.lawn.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import patel.yaksh.lawn.Model.Service;

@RepositoryRestResource
public interface ServiceRepository extends JpaRepository<Service,Integer> {
}
