package patel.yaksh.lawn.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import patel.yaksh.lawn.Model.ServiceRequest;

import java.util.List;

@RepositoryRestResource
public interface ServiceRepository extends JpaRepository<ServiceRequest,Integer> {

//    List<ServiceRequest> findAllByProviderIdEquals(int id);
    List<ServiceRequest> findAllByisAcceptedIsFalse();

    @Query(
                    "SELECT s " +
                    "FROM ServiceRequest s " +
                    "left join User u on s.requesterId = u.id where u.city like :city and u.state like :state and s.isAccepted = false"
    )
    List<ServiceRequest> findByCityAndState(@Param("city") String city, @Param("state") String state);

    @Query(
            "SELECT s " +
                    "FROM ServiceRequest s " +
                    "left join User u on s.requesterId = u.id where u.postal_code like :postal and s.isAccepted = false"
    )
    List<ServiceRequest> findByPostalCode(@Param("postal") String postal_code);

}
