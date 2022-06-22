package peermarket.peershop.controller;

import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import peermarket.peershop.entity.ChatRoom;
import peermarket.peershop.entity.Member;
import peermarket.peershop.repository.MemberRepository;
import peermarket.peershop.security.CurrentMember;
import peermarket.peershop.security.PrincipalDetails;
import peermarket.peershop.service.ChatRoomService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final MemberRepository memberRepository;

    /**
     * Room 생성
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/room")
    public String createRoom(@RequestParam String roomName, @RequestParam String contactMemberName,
        @CurrentMember PrincipalDetails currentMember) {
        ChatRoom chatRoom = new ChatRoom(roomName);
        ArrayList<Member> members = new ArrayList<>();
        Member contactMember = memberRepository.findByUsername(contactMemberName).get();
        Member member = currentMember.getMember();
        members.add(member);
        members.add(contactMember);
        Long savedRoomId = chatRoomService.saveChatRoom(chatRoom, members);
        return "redirect:/chat/room/" + savedRoomId;
    }


}
