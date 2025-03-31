package runnershigh.capstone.user.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Physical {

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Long age;
    private Double height;
    private Double weight;
}
