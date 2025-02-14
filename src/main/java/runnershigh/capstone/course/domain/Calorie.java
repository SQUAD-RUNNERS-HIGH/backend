package runnershigh.capstone.course.domain;

import lombok.Getter;
import runnershigh.capstone.user.domain.Physical;

@Getter
public class Calorie {
    private double expectedCalories;

    public Calorie(final Slope slope, final Physical physical) {
        this.expectedCalories = calculate();
    }

    private double calculate(){
        return 0;
    }
}
