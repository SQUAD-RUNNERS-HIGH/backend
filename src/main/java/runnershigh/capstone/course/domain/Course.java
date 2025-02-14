package runnershigh.capstone.course.domain;

import com.mongodb.client.model.geojson.Geometry;
import jakarta.persistence.Id;
import java.util.Properties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "courses")
@AllArgsConstructor
@Getter
public class Course {

    @Id
    private ObjectId id;
    private CourseProperties properties;
    private CourseGeometry geometry;
}
