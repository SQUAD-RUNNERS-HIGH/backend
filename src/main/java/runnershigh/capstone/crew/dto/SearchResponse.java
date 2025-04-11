package runnershigh.capstone.crew.dto;

import java.util.List;
import org.springframework.data.domain.Page;

public record SearchResponse<T>(
    List<T> content,
    int page,
    int size,
    long totalElements,
    int totalPages,
    boolean last
) {

    public static <T> SearchResponse<T> from(Page<T> page) {
        return new SearchResponse<>(
            page.getContent(),
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.isLast()
        );
    }
}
