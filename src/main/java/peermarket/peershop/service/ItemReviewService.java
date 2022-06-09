package peermarket.peershop.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peermarket.peershop.entity.Item;
import peermarket.peershop.entity.ItemReview;
import peermarket.peershop.exception.NotFoundException;
import peermarket.peershop.repository.ItemRepository;
import peermarket.peershop.repository.ItemReviewRepository;

@Service
@RequiredArgsConstructor
public class ItemReviewService {

    private final ItemReviewRepository itemReviewRepository;
    private final ItemRepository itemRepository;


    /**
     * 댓글 저장
     */
    @Transactional
    public Long saveItemReview(ItemReview itemReview) {
        ItemReview review = itemReviewRepository.save(itemReview);

        Item item = itemReview.getItem();
        int count = itemReviewRepository.countReview(item) ;
        double averageRating = itemReviewRepository.getAverageRating(item);
        String average = String.format("%.1f", averageRating);

        item.updateRatingCount(count);
        item.updateRatingAverage(average);

        return review.getId();
    }

    /**
     * 댓글 가져오기
     * pageable
     */
    public Page<ItemReview> findReviews(Long itemId, Pageable pageable) {
        Optional<Item> findItem = itemRepository.findById(itemId);
        if (findItem.isEmpty()) {
            throw new NotFoundException("해당 아이템이 존재하지 않습니다.");
        }
        return itemReviewRepository.findByItem(findItem.get(), pageable);
    }

}
