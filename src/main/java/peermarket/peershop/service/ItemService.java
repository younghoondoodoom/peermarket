package peermarket.peershop.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peermarket.peershop.entity.Item;
import peermarket.peershop.entity.ItemReview;
import peermarket.peershop.entity.Member;
import peermarket.peershop.exception.NotFoundException;
import peermarket.peershop.repository.ItemRepository;
import peermarket.peershop.repository.ItemReviewRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemReviewRepository itemReviewRepository;


    /**
     * item 저장
     */
    @Transactional
    public Long saveItem(Item item) {
        Item savedItem = itemRepository.save(item);
        return savedItem.getId();
    }

    /**
     * item 수정
     */
    @Transactional
    public void updateItem(Long itemId, String name, String imgUrl, String description, Integer price,
        Long stockQuantity) {
        Optional<Item> findItemOptional = itemRepository.findById(itemId);
        if (findItemOptional.isEmpty()) {
            throw new NotFoundException("해당 아이템이 존재하지 않습니다.");
        }

        Item item = findItemOptional.get();
        item.updateItemInfo(name, imgUrl, description, price, stockQuantity);
    }

    /**
     * item 1개 조회
     */
    public Item findOne(Long itemId) {
        Optional<Item> findItemOptional = itemRepository.findById(itemId);
        if (findItemOptional.isEmpty()) {
            throw new NotFoundException("해당 아이템이 존재하지 않습니다.");
        }
        return findItemOptional.get();
    }

    /**
     * item 전체 조회
     * pageable
     */
    public Page<Item> findItems(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, pageable.getPageSize(), Sort.by("createdAt").descending());
        return itemRepository.findAll(pageable);
    }

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

    public Page<Item> findItemsByMember(Member member, Pageable pageable) {
        return itemRepository.findByMember(member, pageable);
    }

}
