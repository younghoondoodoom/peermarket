package peermarket.peershop.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peermarket.peershop.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByItemName(String itemName);

}
