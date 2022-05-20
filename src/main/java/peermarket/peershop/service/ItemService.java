package peermarket.peershop.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, String name, String imgUrl, String description, int price,
        int stockQuantity) {
        Optional<Item> findItemOptional = itemRepository.findById(itemId);
        if (!findItemOptional.isPresent()) {
            throw new NotFoundException("해당 아이템이 존재하지 않습니다.");
        }

        Item item = findItemOptional.get();
        item.updateItemInfo(name, imgUrl, description, price, stockQuantity);
    }

    public List<Item> findItemsSortByCreateAt() {
        Sort sort = Sort.by(Direction.DESC, "createdAt");
        return itemRepository.findAll(sort);
    }

    public Item findOne(Long itemId) {
        Optional<Item> findItemOptional = itemRepository.findById(itemId);
        if (!findItemOptional.isPresent()) {
            throw new NotFoundException("해당 아이템이 존재하지 않습니다.");
        }
        return findItemOptional.get();
    }


}
