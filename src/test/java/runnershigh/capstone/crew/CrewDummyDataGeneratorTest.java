package runnershigh.capstone.crew;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import runnershigh.capstone.builders.UserTestBuilder;
import runnershigh.capstone.crew.domain.Crew;
import runnershigh.capstone.location.domain.Location;
import runnershigh.capstone.user.domain.User;

@SpringBootTest
@Transactional
@Disabled("더미데이터 10만건 밀어 넣는 테스트 - 로컬에서만 실행")
class CrewDummyDataGeneratorTest {

    @Autowired
    EntityManager em;

    @Test
    @Rollback(false)
    void insert_dummy_crews_100000() {
        int count = 100_000;
        int jeongwangCount = 10;

        User user = UserTestBuilder.builder().build();
        em.persist(user);

        // 무작위 인덱스를 정왕동용으로 생성
        Set<Integer> jeongwangCountIndexes = new HashSet<>();
        Random random = new Random();
        while (jeongwangCountIndexes.size() < jeongwangCount) {
            jeongwangCountIndexes.add(random.nextInt(count));
        }

        IntStream.range(0, count).forEach(i -> {
            Location location;
            if (jeongwangCountIndexes.contains(i)) {
                location = new Location(i + "대한민국", "경기도", "시흥시", "정왕동",
                    "대한민국 경기도 시흥시 정왕동 2121-1");
            } else {
                location = new Location(i + "대한민국", "서울시", "강남구", "역삼동",
                    "서울시 강남구 역삼동 2121-1");
            }

            Crew crew = Crew.builder()
                .name("크루_" + i)
                .description("설명_" + i)
                .maxCapacity(100)
                .image("image_url_" + i)
                .crewLocation(location)
                .crewLeader(user)
                .crewParticipant(new HashSet<>())
                .build();

            em.persist(crew);

            if (i % 1000 == 0) {
                em.flush();
                em.clear();
            }
        });

        Long insertedCount = em.createQuery("select count(c) from Crew c", Long.class)
            .getSingleResult();
        assertThat(insertedCount).isGreaterThanOrEqualTo(count);
    }

}
