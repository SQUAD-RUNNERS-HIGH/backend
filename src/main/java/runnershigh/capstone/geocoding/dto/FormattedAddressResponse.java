package runnershigh.capstone.geocoding.dto;

public record FormattedAddressResponse(
    String country,
    String province,
    String city,
    String dong) {

}
