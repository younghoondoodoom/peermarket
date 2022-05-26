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
import peermarket.peershop.exception.NotFoundException;
import peermarket.peershop.repository.ItemRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;


    @Transactional
    public Long saveItem(Item item) {
        Item savedItem = itemRepository.save(item);
        return savedItem.getId();
    }

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

    public Item findOne(Long itemId) {
        Optional<Item> findItemOptional = itemRepository.findById(itemId);
        if (findItemOptional.isEmpty()) {
            throw new NotFoundException("해당 아이템이 존재하지 않습니다.");
        }
        return findItemOptional.get();
    }

    public Page<Item> findItems(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, pageable.getPageSize(), Sort.by("createdAt").descending());
        return itemRepository.findAll(pageable);
    }


}
