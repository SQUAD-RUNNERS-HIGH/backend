package runnershigh.capstone.running.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PersonalRunningRedisRepository {

    private final RedisTemplate<String,Object> redisTemplate;
}
