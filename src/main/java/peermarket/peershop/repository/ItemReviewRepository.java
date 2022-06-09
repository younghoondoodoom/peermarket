package peermarket.peershop.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peermarket.peershop.entity.Item;
import peermarket.peershop.entity.ItemReview;

public interface ItemReviewRepository extends JpaRepository<ItemReview, Long> {

    @Query("select count(r) from ItemReview r where r.isDeleted = false and r.item = :item")
    Integer countReview(@Param("item") Item item);

    @Query("select avg(r.rating) from ItemReview r where r.isDeleted = false and r.item = :item")
    Double getAverageRating(@Param("item") Item item);

    Page<ItemReview> findByItem(Item item, Pageable pageable);

}
