package kr.mashup.branding.domain.schedule;

import javax.persistence.Embeddable;

import lombok.Getter;

@Embeddable
@Getter
public class Location {
    private Double latitude;
    private Double longitude;

    private static final double EARTH_RADIUS = 6371000; // 지구 반지름 (미터)

    /**
     * Haversine 공식을 사용하여 두 지점 간의 거리 계산
     * <a href="https://en.wikipedia.org/wiki/Haversine_formula">Haversine Formula - Wikipedia</a>
     *
     * @param targetLatitude  현재 위도
     * @param targetLongitude 현재 경도
     * @return 두 지점 간의 거리
     */
    private double calculateDistance(double targetLatitude, double targetLongitude) {

        double deltaLatitude = Math.toRadians(targetLatitude - latitude);
        double deltaLongitude = Math.toRadians(targetLongitude - longitude);

        double haversineA = Math.sin(deltaLatitude / 2) * Math.sin(deltaLatitude / 2) +
                Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(targetLatitude)) *
                        Math.sin(deltaLongitude / 2) * Math.sin(deltaLongitude / 2);

        double centralAngle = 2 * Math.atan2(Math.sqrt(haversineA), Math.sqrt(1 - haversineA));

        return EARTH_RADIUS * centralAngle;
    }

    /**
     * 두 지점 간의 거리가 maxDistance 이하인지 확인하는 메서드
     *
     * @param targetLatitude  현재 위도
     * @param targetLongitude 현재 경도
     * @param maxDistance      두 지점 간의 최대 허용 거리
     * @return 거리가 maxDistance 이하인 경우 true, 그렇지 않으면 false
     */
    public boolean isWithinDistance(double targetLatitude, double targetLongitude, double maxDistance) {

        double distance = calculateDistance(targetLatitude, targetLongitude);
        return distance <= maxDistance;
    }
}
