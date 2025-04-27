package runnershigh.capstone.crew.dto.response;

import java.util.List;
import org.springframework.data.domain.Page;

public record CrewSearchPagingResponse<T>(
    List<T> content,
    int page,
    int size,
    long totalElements,
    int totalPages,
    boolean last
) {

    public static <T> CrewSearchPagingResponse<T> from(Page<T> page) {
        return new CrewSearchPagingResponse<>(
            page.getContent(),
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.isLast()
        );
    }
}
