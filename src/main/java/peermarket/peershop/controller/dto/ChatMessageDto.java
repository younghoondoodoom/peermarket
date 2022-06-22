package peermarket.peershop.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import peermarket.peershop.entity.status.MessageType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {

    private Long roomId;
    private MessageType messageType;
    private String content;

}
