package runnershigh.capstone.chat.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import runnershigh.capstone.crew.domain.Crew;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime lastChatTimeStamp;
    private String lastChat;

    @OneToOne(fetch = FetchType.LAZY)
    private Crew crew;

    @Builder
    public ChatRoom(Crew crew) {
        this.crew = crew;
        this.lastChatTimeStamp = LocalDateTime.now();
        this.lastChat = "Default Last Chat";
    }

    public void saveLastChatTimeStampAndLastChat(LocalDateTime sendAt, String lastChat) {
        this.lastChatTimeStamp = sendAt;
        this.lastChat = lastChat;
    }

    public void validationCrewParticipant(Long userId) {
        crew.validationCrewParticipant(userId);
    }
}
