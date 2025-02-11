package runnershigh.capstone.jwt.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

    @Id
    private String token;

    private Long userId;

}
