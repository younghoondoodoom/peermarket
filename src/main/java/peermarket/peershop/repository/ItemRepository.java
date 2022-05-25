package peermarket.peershop.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import peermarket.peershop.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByItemName(String itemName);

}
