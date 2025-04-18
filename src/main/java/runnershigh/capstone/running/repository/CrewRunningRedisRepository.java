package runnershigh.capstone.running.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands.DistanceUnit;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.stereotype.Repository;
import runnershigh.capstone.running.dto.CrewParticipantInfoRequest;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CrewRunningRedisRepository {

    private final RedisTemplate<String,Object> redisTemplate;
    private static final String CREW_LOCATION_KEY = "location:course:%s:crew:%s";
    private static final String CREW_READY_STATUS_KEY = "ready:course:%s:crew:%s";

    public void addReadyStatus(final String courseId, final String crewId, final String userId, final boolean isReady){
        String readyKey = CREW_READY_STATUS_KEY.formatted(courseId, crewId);
        redisTemplate.opsForHash().put(readyKey,userId,isReady);
    }

    public Map<String,Boolean> getReadyStatus(final String courseId, final String crewId){
        String readyKey = CREW_READY_STATUS_KEY.formatted(courseId, crewId);
        Map<Object, Object> raw = redisTemplate.opsForHash().entries(readyKey);
        Map<String, Boolean> result = new HashMap<>();

        for (Map.Entry<Object, Object> entry : raw.entrySet()) {
            String userId = String.valueOf(entry.getKey());
            Boolean isReady = Boolean.valueOf(String.valueOf(entry.getValue()));
            result.put(userId, isReady);
        }
        return result;
    }

    public void addLocation(final CrewParticipantInfoRequest info, final String courseId, final String crewId){
        final Point point = new Point(info.longitude(), info.latitude());
        String geoKey = CREW_LOCATION_KEY.formatted(courseId, crewId);
        Long add = redisTemplate.opsForGeo().add(geoKey, point, info.userId());
    }

    public List<String> geoSearch(final String courseId, final String crewId, final String userId){
        GeoReference reference = GeoReference.fromMember(userId);
        Distance radius = new Distance(30, DistanceUnit.METERS);
        String geoKey = CREW_LOCATION_KEY.formatted(courseId, crewId);
        GeoResults<GeoLocation<String>> results = redisTemplate.opsForGeo().search(geoKey, reference, radius);
        return results.getContent()
            .stream().map(rs -> rs.getContent().getName()).toList();
    }
}
