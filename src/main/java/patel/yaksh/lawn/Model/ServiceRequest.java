package patel.yaksh.lawn.Model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Data
@NoArgsConstructor
public class ServiceRequest extends RepresentationModel<ServiceRequest> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @NotNull
    public int requesterId;
    @NotNull
    public Date date;
    @NotNull
    public Time time;
    //@Column(name = "is_accepted")
    public boolean accepted = false;
    public int providerId = -1;
    public boolean completed = false;

    public ServiceRequest(int requesterId, Date date, Time time) {
        this.requesterId = requesterId;
        this.date = date;
        this.time = time;
    }
}
