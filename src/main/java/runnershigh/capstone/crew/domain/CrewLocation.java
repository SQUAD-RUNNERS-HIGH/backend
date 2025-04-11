package runnershigh.capstone.crew.domain;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class CrewLocation {

    private String country;
    private String province;
    private String city;
    private String dong;
}
