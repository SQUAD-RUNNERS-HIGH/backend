package runnershigh.capstone.course.domain;

import jakarta.persistence.Id;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "elevations")
@Getter
public class Elevation {

    @Id
    private ObjectId id;
    @Field(name = "course_id")
    private ObjectId courseId;
    private List<CourseElevation> courseElevations;

    @Builder
    public Elevation(final ObjectId courseId, final List<CourseElevation> courseElevations) {
        this.courseId = courseId;
        this.courseElevations = courseElevations;
    }
}
