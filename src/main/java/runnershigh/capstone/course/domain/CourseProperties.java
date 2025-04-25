package runnershigh.capstone.course.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
public class CourseProperties {
    @Field(name = "@id")
    private String id;
    private String leisure;
    private String type;
    @Field(name = "name")
    private String name;
    private double perimeter;

    public CourseProperties(final String name, final double perimeter) {
        this.name = name;
        this.perimeter = perimeter;
    }
}
