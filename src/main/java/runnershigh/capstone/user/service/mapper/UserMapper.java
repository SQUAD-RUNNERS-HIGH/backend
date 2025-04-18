package runnershigh.capstone.user.service.mapper;


import org.springframework.stereotype.Component;
import runnershigh.capstone.geocoding.dto.FormattedAddressResponse;
import runnershigh.capstone.location.domain.Location;
import runnershigh.capstone.location.dto.LocationResponse;
import runnershigh.capstone.user.domain.Gender;
import runnershigh.capstone.user.domain.Physical;
import runnershigh.capstone.user.domain.User;
import runnershigh.capstone.user.dto.UserPhysicalRequest;
import runnershigh.capstone.user.dto.UserPhysicalResponse;
import runnershigh.capstone.user.dto.UserRegisterRequest;

@Component
public class UserMapper {

    public User toUser(UserRegisterRequest userRegisterRequest,
        FormattedAddressResponse formattedAddressResponse,
        String hashedPassword,
        String salt) {
        return User.builder()
            .loginId(userRegisterRequest.loginId())
            .password(hashedPassword)
            .passwordSalt(salt)
            .username(userRegisterRequest.username())
            .physical(toPhysical(userRegisterRequest.physical()))
            .userLocation(toUserLocation(formattedAddressResponse,
                userRegisterRequest.userLocation().specificLocation()))
            .build();
    }

    public Physical toPhysical(UserPhysicalRequest userPhysicalRequest) {
        return Physical.builder()
            .gender(Gender.valueOf(userPhysicalRequest.gender()))
            .age(userPhysicalRequest.age())
            .height(userPhysicalRequest.height())
            .weight(userPhysicalRequest.weight())
            .build();
    }

    public Location toUserLocation(FormattedAddressResponse formattedAddressResponse,
        String specificLocation) {
        return Location.builder()
            .country(formattedAddressResponse.country())
            .province(formattedAddressResponse.province())
            .city(formattedAddressResponse.city())
            .dong(formattedAddressResponse.dong())
            .specificLocation(specificLocation)
            .build();
    }

    public UserPhysicalResponse toUserPhysicalResponse(Physical physical) {
        return UserPhysicalResponse.builder()
            .gender(physical.getGender().toString())
            .age(physical.getAge())
            .height(physical.getHeight())
            .weight(physical.getWeight())
            .build();
    }

    public LocationResponse toUserLocationResponse(Location userLocation) {
        return LocationResponse.builder()
            .country(userLocation.getCountry())
            .province(userLocation.getProvince())
            .city(userLocation.getCity())
            .dong(userLocation.getDong())
            .specificLocation(userLocation.getSpecificLocation())
            .build();
    }
}
