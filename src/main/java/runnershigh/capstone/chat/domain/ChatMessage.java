package runnershigh.capstone.chat.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long senderId;

    private String content;

    @Enumerated(EnumType.STRING)
    private ChatMessageType chatMessageType;

    private LocalDateTime sentAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @PrePersist
    protected void onSend() {
        this.sentAt = LocalDateTime.now();
    }

    @Builder
    public ChatMessage(Long senderId, String content, ChatRoom chatRoom,
        ChatMessageType chatMessageType) {
        this.senderId = senderId;
        this.content = content;
        this.chatRoom = chatRoom;
        this.chatMessageType = chatMessageType;
    }
}
