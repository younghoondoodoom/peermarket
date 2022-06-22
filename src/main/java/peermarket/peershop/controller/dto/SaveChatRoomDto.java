package peermarket.peershop.controller.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;
import peermarket.peershop.entity.Member;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveChatRoomDto {

    private String name;
    private List<Member> members = new ArrayList<>();

    private Set<WebSocketSession> sessions = new HashSet<>();

}
