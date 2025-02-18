package runnershigh.capstone.course.domain;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import runnershigh.capstone.course.consts.Velocity;

@Document(collection = "courses")
@AllArgsConstructor
@Getter
public class Course {

    @Id
    private ObjectId id;
    private CourseProperties properties;
    private CourseGeometry geometry;

    public double calculateMinRunningTimeMinute(){
        return properties.getPerimeter() / Velocity.MAX_METER_PER_MINUTE.getScala();
    }

    public double calculateMaxRunningTimeMinute(){
        return properties.getPerimeter() / Velocity.MIN_METER_PER_MINUTE.getScala();
    }
}
