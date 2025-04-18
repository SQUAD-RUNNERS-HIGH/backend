package runnershigh.capstone.crew.repository.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import runnershigh.capstone.crew.domain.Crew;
import runnershigh.capstone.crew.domain.QCrew;
import runnershigh.capstone.crew.dto.CrewSearchCondition;

@Repository
public class CrewRepositoryCustomImpl implements CrewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CrewRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Crew> findCrewByCondition(CrewSearchCondition request, Pageable pageable) {

        QCrew crew = QCrew.crew;

        BooleanBuilder builder = new BooleanBuilder();

        if (request.region() != null && !request.region().isBlank()) {
            builder.and(crew.crewLocation.specificLocation.containsIgnoreCase(request.region()));
        }

        if (request.name() != null && !request.name().isBlank()) {
            builder.and(crew.name.containsIgnoreCase(request.name()));
        }

        List<Crew> content = queryFactory
            .selectFrom(crew)
            .where(builder)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total = queryFactory
            .select(crew.count())
            .from(crew)
            .where(builder)
            .fetchOne();

        long totalCount = (total != null) ? total : 0L;

        return new PageImpl<>(content, pageable, totalCount);
    }
}
