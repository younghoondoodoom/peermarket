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
import peermarket.peershop.entity.Member;
import peermarket.peershop.exception.NotFoundException;
import peermarket.peershop.repository.ItemRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;


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
    public void updateItem(Long itemId, String name, String imgUrl, String description, Integer stockQuantity,
        Long price) {
        Optional<Item> findItemOptional = itemRepository.findById(itemId);
        if (findItemOptional.isEmpty()) {
            throw new NotFoundException("해당 아이템이 존재하지 않습니다.");
        }
        Item item = findItemOptional.get();
        item.updateItemInfo(name, imgUrl, description, stockQuantity, price);
    }

    /**
     * item 삭제
     */
    @Transactional
    public void deleteItem(Long itemId) {
        itemRepository.delete(
            itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("해당 상품을 찾을 수 없습니다."))
        );
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


    public Page<Item> findItemsByMember(Member member, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, pageable.getPageSize(), pageable.getSortOr(Sort.by("createdAt").descending()));
        return itemRepository.findByMember(member, pageable);
    }

    public Page<Item> searchItem(String itemName, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, pageable.getPageSize(),
            pageable.getSortOr(Sort.by("createdAt").descending()));
        return itemRepository.findByItemNameContaining(itemName, pageable);
    }

    /**
     * 본인 아이템인지 확인하는 로직
     */
    public boolean isOwnItem(Long itemId, Member member) {
        Optional<Item> item = itemRepository.findById(itemId);
        if (!item.isPresent()) {
            throw new NotFoundException("해당 상품이 존재하지 않습니다.");
        }
        if (item.get().getMember() != member) {
            return true;
        }
        return false;
    }

}
