package runnershigh.capstone.user.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class UserLocation {

    private String country;
    private String province;
    private String city;
    private String dong;


}
