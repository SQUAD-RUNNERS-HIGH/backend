package runnershigh.capstone.course.domain;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import runnershigh.capstone.course.consts.Velocity;

@Document(collection = "courses")
@Getter
public class Course {

    @Id
    private ObjectId id;
    private CourseProperties properties;
    private CourseGeometry geometry;

    public Course(final CourseProperties properties, final CourseGeometry geometry) {
        this.properties = properties;
        this.geometry = geometry;
    }

    public double calculateMinRunningTimeMinute(){
        return properties.getPerimeter() / Velocity.MAX_METER_PER_MINUTE.getScala();
    }

    public double calculateMaxRunningTimeMinute(){
        return properties.getPerimeter() / Velocity.MIN_METER_PER_MINUTE.getScala();
    }
}
