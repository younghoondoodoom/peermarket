package peermarket.peershop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peermarket.peershop.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

}
