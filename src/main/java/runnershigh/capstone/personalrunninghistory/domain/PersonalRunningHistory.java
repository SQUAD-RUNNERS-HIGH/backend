package runnershigh.capstone.personalrunninghistory.domain;

import jakarta.persistence.Id;
import java.util.List;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
public class PersonalRunningHistory {

    @Id
    private ObjectId id;

    private List<Double> progress;
    private Double runningTime;

    public PersonalRunningHistory(final List<Double> progress, final Double runningTime) {
        this.progress = progress;
        this.runningTime = runningTime;
    }
}
