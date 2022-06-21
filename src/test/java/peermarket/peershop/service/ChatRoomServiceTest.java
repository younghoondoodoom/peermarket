package peermarket.peershop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import peermarket.peershop.entity.ChatRoom;
import peermarket.peershop.entity.Member;
import peermarket.peershop.repository.ChatRoomRepository;
import peermarket.peershop.repository.RoomMemberRepository;

@ExtendWith(MockitoExtension.class)
class ChatRoomServiceTest {

    @Mock
    ChatRoomRepository chatRoomRepository;
    @Mock
    RoomMemberRepository roomMemberRepository;
    @InjectMocks
    ChatRoomService chatRoomService;

    @Test
    public void 채팅방저장() throws Exception {
        //given
        String roomName = "testRoom";
        ChatRoom chatRoom = new ChatRoom(roomName);

        ArrayList<Member> members = new ArrayList<>();
        Member member1 = new Member("test1@test.com", "test123!", "test1");
        Member member2 = new Member("test2@test.com", "test123!", "test2");
        members.add(member1);
        members.add(member2);

        given(chatRoomRepository.save(chatRoom)).willReturn(chatRoom);

        //when
        Long savedRoomId = chatRoomService.saveChatRoom(chatRoom, members);

        //then
        assertThat(savedRoomId).isEqualTo(chatRoom.getId());

    }

}