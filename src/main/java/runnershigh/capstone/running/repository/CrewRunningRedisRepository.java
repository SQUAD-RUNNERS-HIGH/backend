package runnershigh.capstone.running.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.connection.RedisGeoCommands.DistanceUnit;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.stereotype.Repository;
import runnershigh.capstone.running.dto.request.CrewParticipantInfoRequest;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CrewRunningRedisRepository {

    private final RedisTemplate<String,Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private static final String CREW_LOCATION_KEY = "location:course:%s:crew:%s";
    private static final String CREW_READY_STATUS_KEY = "ready:course:%s:crew:%s";
    private static final String CREW_START_COORDINATE_KEY = "start:course:%s:crew:%s";

    public void addReadyStatus(final String courseId, final String crewId, final String userId,
        final boolean isReady,final String userName){
        String readyKey = CREW_READY_STATUS_KEY.formatted(courseId, crewId);
        ReadyStatus readyStatus = new ReadyStatus(isReady, userName);
        try {
            String readyStatusJson = objectMapper.writeValueAsString(readyStatus);
            redisTemplate.opsForHash().put(readyKey,userId,readyStatusJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String,ReadyStatus> getReadyStatus(final String courseId, final String crewId){
        String readyKey = CREW_READY_STATUS_KEY.formatted(courseId, crewId);
        Map<Object, Object> raw = redisTemplate.opsForHash().entries(readyKey);
        Map<String, ReadyStatus> result = new HashMap<>();

        for (Map.Entry<Object, Object> entry : raw.entrySet()) {
            String userId = String.valueOf(entry.getKey());
            try {
                ReadyStatus readyStatus = objectMapper.readValue(String.valueOf(entry.getValue()), ReadyStatus.class);
                result.put(userId, readyStatus);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    public void addLocation(final CrewParticipantInfoRequest info, final String courseId, final String crewId){
        final Point point = new Point(info.longitude(), info.latitude());
        String geoKey = CREW_LOCATION_KEY.formatted(courseId, crewId);
        redisTemplate.opsForGeo().add(geoKey, point, info.userId());
    }

    public List<ParticipantLocation> geoSearch(final String courseId, final String crewId, final String userId){
        GeoReference reference = GeoReference.fromMember(userId);
        Distance radius = new Distance(30, DistanceUnit.METERS);
        String geoKey = CREW_LOCATION_KEY.formatted(courseId, crewId);
        RedisGeoCommands.GeoSearchCommandArgs args = RedisGeoCommands.GeoSearchCommandArgs.newGeoSearchArgs()
            .includeCoordinates();
        GeoResults<GeoLocation<String>> results = redisTemplate.opsForGeo().search(geoKey, reference, radius,args);
        return results.getContent()
            .stream().map(rs -> new ParticipantLocation(rs.getContent().getName(),rs.getContent().getPoint().getX(),
                rs.getContent().getPoint().getY())).toList();
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReadyStatus{
        private boolean isReady;
        private String username;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ParticipantLocation{
        private String userId;
        private double longitude;
        private double latitude;
    }

}
