package runnershigh.capstone.user.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Goal {

    private Double goalWeight;
}
