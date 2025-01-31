package runnershigh.capstone.user.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import runnershigh.capstone.user.enums.Gender;

@Getter
@Embeddable
public class Physical {

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Long age;
    private Double height;
    private Double weight;

    public Double calculateBmi(Double height, Double weight) {
        double heightInMeters = height * 0.01;
        return weight / heightInMeters * heightInMeters;
    }
}
