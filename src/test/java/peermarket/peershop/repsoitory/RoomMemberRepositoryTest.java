package peermarket.peershop.repsoitory;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import peermarket.peershop.entity.ChatRoom;
import peermarket.peershop.entity.Member;
import peermarket.peershop.entity.RoomMember;
import peermarket.peershop.repository.ChatMessageRepository;
import peermarket.peershop.repository.ChatRoomRepository;
import peermarket.peershop.repository.MemberRepository;
import peermarket.peershop.repository.RoomMemberRepository;

@DataJpaTest
public class RoomMemberRepositoryTest {

    @Autowired
    RoomMemberRepository roomMemberRepository;
    @Autowired
    ChatRoomRepository chatRoomRepository;
    @Autowired
    ChatMessageRepository chatMessageRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 채팅_멤버_저장() throws Exception {
        //given
        Member member1 = new Member("test1@test.com", "test123!", "test");
        Member member2 = new Member("test2@test.com", "test123!", "test");
        memberRepository.save(member1);
        memberRepository.save(member2);

        ChatRoom chatRoom = new ChatRoom("testChatRoom");
        chatRoomRepository.save(chatRoom);

        RoomMember roomMember1 = new RoomMember(member1, chatRoom);
        RoomMember roomMember2 = new RoomMember(member2, chatRoom);

        List<RoomMember> roomMembers = new ArrayList<>();
        roomMembers.add(roomMember1);
        roomMembers.add(roomMember2);

        //when
        List<RoomMember> savedRoomMembers = roomMemberRepository.saveAll(roomMembers);

        //then
        assertThat(member1.getUsername())
            .isEqualTo(savedRoomMembers.get(0).getMember().getUsername());
        assertThat(member2.getUsername())
            .isEqualTo(savedRoomMembers.get(1).getMember().getUsername());

    }

}
