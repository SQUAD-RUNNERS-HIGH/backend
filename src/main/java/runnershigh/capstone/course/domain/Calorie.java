package runnershigh.capstone.course.domain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import runnershigh.capstone.user.domain.Physical;

@Getter
@Slf4j
public class Calorie {

    private static final double LITER_TO_CALORIE = 5;
    private static final double MILLILITER_TO_LITER = 1000;
    private double minCalorie;
    private double maxCalorie;

    public Calorie(final Mets mets, final Physical physical, final Course course) {
        this.minCalorie = calculate(mets.getMin(), physical.getWeight(), course.
            calculateMinRunningTimeMinute());
        this.maxCalorie = calculate(mets.getMax(), physical.getWeight(),
            course.calculateMaxRunningTimeMinute());
    }

    private double calculate(final double mets, final double weight,
        final double runningTimeMinute) {
        return (mets * weight / MILLILITER_TO_LITER) * LITER_TO_CALORIE * runningTimeMinute;
    }
}
