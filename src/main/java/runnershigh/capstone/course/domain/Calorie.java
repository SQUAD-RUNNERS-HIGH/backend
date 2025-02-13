package runnershigh.capstone.course.domain;

import lombok.Getter;
import runnershigh.capstone.user.domain.Physical;

@Getter
public class Calorie {
    private double expectedCalories;

    public Calorie(final double expectedCalories, final Physical physical) {

    }
}
