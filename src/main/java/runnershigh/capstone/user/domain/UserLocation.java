package runnershigh.capstone.user.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class UserLocation {

    private String country;
    private String province;
    private String city;
    private String dong;

    public UserLocation(String country, String province, String city, String dong) {
        this.country = country;
        this.province = province;
        this.city = city;
        this.dong = dong;
    }


}
