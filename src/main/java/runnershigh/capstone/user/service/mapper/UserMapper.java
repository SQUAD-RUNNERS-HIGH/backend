package runnershigh.capstone.user.service.mapper;


import org.springframework.stereotype.Component;
import runnershigh.capstone.geocoding.dto.FormattedAddressResponse;
import runnershigh.capstone.user.domain.Gender;
import runnershigh.capstone.user.domain.Physical;
import runnershigh.capstone.user.domain.User;
import runnershigh.capstone.user.domain.UserLocation;
import runnershigh.capstone.user.dto.UserLocationResponse;
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
            .userLocation(toUserLocation(formattedAddressResponse))
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

    public UserLocation toUserLocation(FormattedAddressResponse formattedAddressResponse) {
        return UserLocation.builder()
            .country(formattedAddressResponse.country())
            .province(formattedAddressResponse.province())
            .city(formattedAddressResponse.city())
            .dong(formattedAddressResponse.dong())
            .build();
    }

    public UserPhysicalResponse toUserPhysicalResponse(Physical physical) {
        return new UserPhysicalResponse(physical.getGender().toString(), physical.getAge(),
            physical.getHeight(), physical.getWeight());
    }

    public UserLocationResponse toUserLocationResponse(UserLocation userLocation) {
        return new UserLocationResponse(userLocation.getCountry(), userLocation.getProvince(),
            userLocation.getCity(), userLocation.getDong());
    }
}
