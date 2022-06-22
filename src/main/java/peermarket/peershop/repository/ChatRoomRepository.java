package peermarket.peershop.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import peermarket.peershop.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
