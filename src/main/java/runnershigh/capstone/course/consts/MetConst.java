package runnershigh.capstone.course.consts;

import lombok.Getter;

@Getter
public enum MetConst {
    OXYGEN_COST_COEFFICIENT(0.2),
    HORIZONTAL_COMPONENT(0.5),
    VERTICAL_COMPONENT(1.8),
    BMR(3.5);

    private final double scala;

    MetConst(final double scala) {
        this.scala = scala;
    }
}
