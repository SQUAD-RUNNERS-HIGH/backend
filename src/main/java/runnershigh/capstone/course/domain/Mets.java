package runnershigh.capstone.course.domain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import runnershigh.capstone.course.consts.MetConst;
import runnershigh.capstone.course.consts.Velocity;

@Getter
@Slf4j
public class Mets {

    private double min;
    private double max;

    public Mets(final Slope slope) {
        this.min = calculate(Velocity.MIN_METER_PER_MINUTE.getScala(), slope.getGrade());
        this.max = calculate(Velocity.MAX_METER_PER_MINUTE.getScala(), slope.getGrade());
    }

    private double calculate(final double velocity, final double grade) {
        return (velocity * MetConst.OXYGEN_COST_COEFFICIENT.getScala())
            + (grade * velocity * MetConst.VERTICAL_COMPONENT.getScala()
            * MetConst.HORIZONTAL_COMPONENT.getScala()) + MetConst.BMR.getScala();
    }
}
