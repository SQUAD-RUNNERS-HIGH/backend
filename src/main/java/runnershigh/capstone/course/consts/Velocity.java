package runnershigh.capstone.course.consts;

import java.util.function.Function;
import lombok.Getter;

@Getter
public enum Velocity {
    MIN_METER_PER_MINUTE(133.3),
    MAX_METER_PER_MINUTE(200.0);

    private double scala;

    Velocity(final double scala) {
        this.scala = scala;
    }
}
