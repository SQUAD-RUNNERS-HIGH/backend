package runnershigh.capstone.personalrank.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import runnershigh.capstone.personalrank.domain.PersonalRank;

@Repository
public interface PersonalRankRepository extends JpaRepository<PersonalRank, Long> {

    Slice<PersonalRank> findByCourseId(String courseId, Pageable pageable);
}
