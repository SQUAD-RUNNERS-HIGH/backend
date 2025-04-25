package runnershigh.capstone.crew.dto.response;

import java.util.List;
import org.springframework.data.domain.Page;

public record CrewSearchResponse<T>(
    List<T> content,
    int page,
    int size,
    long totalElements,
    int totalPages,
    boolean last
) {

    public static <T> CrewSearchResponse<T> from(Page<T> page) {
        return new CrewSearchResponse<>(
            page.getContent(),
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.isLast()
        );
    }
}
