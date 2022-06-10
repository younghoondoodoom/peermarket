package peermarket.peershop.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peermarket.peershop.entity.Item;
import peermarket.peershop.entity.Member;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findByItemNameContaining(@Param("itemName") String itemName, Pageable pageable);
    Page<Item> findByMember(Member member, Pageable pageable);

}
