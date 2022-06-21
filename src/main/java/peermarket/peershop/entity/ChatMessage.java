package peermarket.peershop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import peermarket.peershop.entity.base.BaseTimeEntity;
import peermarket.peershop.entity.status.MessageType;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatMessage_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatRoom_id")
    private ChatRoom chatRoom;

    public ChatMessage(Long id, String name, Member member, MessageType messageType, String content,
        ChatRoom chatRoom) {
        this.id = id;
        this.name = name;
        this.member = member;
        this.messageType = messageType;
        this.content = content;
        this.chatRoom = chatRoom;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
            "id=" + id +
            ", content='" + content + '\'' +
            "} " + super.toString();
    }
}
