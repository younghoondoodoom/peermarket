package peermarket.peershop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import peermarket.peershop.controller.dto.ChatMessageDto;
import peermarket.peershop.entity.ChatMessage;
import peermarket.peershop.entity.ChatRoom;
import peermarket.peershop.entity.Member;
import peermarket.peershop.entity.status.MessageType;
import peermarket.peershop.repository.ChatMessageRepository;
import peermarket.peershop.repository.ChatRoomRepository;
import peermarket.peershop.security.CurrentMember;
import peermarket.peershop.security.PrincipalDetails;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    @PreAuthorize("isAuthenticated()")
    @MessageMapping("/chat/enter")
    public void enter(ChatMessageDto chatMessageDto, @CurrentMember PrincipalDetails currentMember) {
        Member member = currentMember.getMember();
        if (chatMessageDto.getMessageType().equals(MessageType.JOIN)) {
            chatMessageDto.setContent(member.getUsername() + "님이 입장하였습니다.");
        }
        ChatRoom chatRoom = chatRoomRepository.findById(chatMessageDto.getRoomId()).get();
        ChatMessage chatMessage = new ChatMessage(member, chatMessageDto.getMessageType(),
            chatMessageDto.getContent(),
            chatRoom);
        messagingTemplate.convertAndSend("app/chat/room/" + chatMessage.getChatRoom().getId(), chatMessage);
        chatMessageRepository.save(chatMessage);
    }

}
