package patel.yaksh.lawn.Model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Time;

@Entity
@Data
@NoArgsConstructor
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @NotNull
    public int requesterId;
    @NotNull
    public Date date;
    @NotNull
    public Time time;
    public boolean isAccepted;
    public int providerId;
    public boolean isCompleted;

    public Service(int requesterId, Date date, Time time, boolean isAccepted, int providerId, boolean isCompleted) {
        this.requesterId = requesterId;
        this.date = date;
        this.time = time;
        this.isAccepted = isAccepted;
        this.providerId = providerId;
        this.isCompleted = isCompleted;
    }
}
