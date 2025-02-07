package runnershigh.capstone.course.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@AllArgsConstructor
public class CourseProperties {
    @Field(name = "@id")
    private String id;
    private String leisure;
    private String type;
}
