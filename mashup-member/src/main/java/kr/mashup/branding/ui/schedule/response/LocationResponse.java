package kr.mashup.branding.ui.schedule.response;

import kr.mashup.branding.domain.schedule.Location;
import lombok.Getter;

@Getter
public class LocationResponse {

    private final Double latitude;
    private final Double longitude;
    private final String address;
    private final String placeName;
    private final String roadAddress;
    private final String detailAddress;

    public LocationResponse(Double latitude, Double longitude, String address, String placeName, String roadAddress, String detailAddress) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.placeName = placeName;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
    }

    public static LocationResponse from(Location location) {
        return new LocationResponse(
                location.getLatitude(),
                location.getLongitude(),
                location.getRoadAddress(),
                location.getDetailAddress(),
                location.getRoadAddress(),
                location.getDetailAddress()
        );
    }
}
