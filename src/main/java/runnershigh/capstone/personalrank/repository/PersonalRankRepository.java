package runnershigh.capstone.personalrank.repository;

import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import runnershigh.capstone.personalrank.domain.PersonalRank;
import runnershigh.capstone.user.domain.User;

@Repository
public interface PersonalRankRepository extends JpaRepository<PersonalRank, Long> {

    Slice<PersonalRank> findByCourseId(String courseId, Pageable pageable);

    Optional<PersonalRank> findByUserAndCourseId(User user , String courseId);
}
