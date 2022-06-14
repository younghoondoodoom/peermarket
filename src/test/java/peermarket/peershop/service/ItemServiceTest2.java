package peermarket.peershop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import peermarket.peershop.entity.Item;
import peermarket.peershop.entity.Member;
import peermarket.peershop.exception.NotFoundException;
import peermarket.peershop.repository.ItemRepository;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest2 {

    @Mock
    ItemRepository itemRepository;

    @InjectMocks
    ItemService itemService;



    @Test
    public void 아이템_리스트() throws Exception {
        //given
        Member member = new Member("test@test.com", "test123!", "test");
        Item item1 = new Item(member, "item1", "imgpath", "item1", 100, 10000L);
        Item item2 = new Item(member, "item2", "imgpath", "item2", 100, 10000L);
        Item item3 = new Item(member, "item3", "imgpath", "item3", 100, 10000L);
        Item item4 = new Item(member, "item4", "imgpath", "item4", 100, 10000L);
        Item item5 = new Item(member, "item5", "imgpath", "item5", 100, 10000L);

        ArrayList<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        items.add(item5);

        Page<Item> pageItem = new PageImpl<>(items);
        Pageable pageable = PageRequest.of(0, 5, Sort.by("createdAt").descending());

        given(itemRepository.findAll(pageable)).willReturn(pageItem);

        //when
        Page<Item> result = itemService.findItems(pageable);

        //then
        assertThat(result.getTotalElements()).isEqualTo(5);
        assertThat(result.getContent()).contains(item1, item2, item3, item4, item5);
        assertThat(result.getSize()).isEqualTo(5);

    }

    @Test
    public void 아이템_디테일() throws Exception {
        //given
        Member member = new Member("test@test.com", "test123!", "test");
        Item item = new Item(member, "item", "imgpath", "item", 100, 10000L);

        Optional<Item> findItem = Optional.ofNullable(item);
        given(itemRepository.findById(item.getId())).willReturn(findItem);

        //when
        Item result = itemService.findOne(item.getId());

        //then
        assertThat(result.getItemName()).isEqualTo("item");
        assertThat(result.getMember().getEmail()).isEqualTo("test@test.com");
        assertThat(result.getPrice()).isEqualTo(10000L);

        assertThrows(NotFoundException.class, () -> {
            itemService.findOne(100000L);
        });
    }

    @Test
    public void 아이템저장() throws Exception {
        //given
        Member member = new Member("test@test.com", "test123!", "test");
        Item item = new Item(member, "item", "imgpath", "item", 100, 10000L);

        given(itemRepository.save(item)).willReturn(item);

        //when
        Long savedItemId = itemService.saveItem(item);

        //then
        assertThat(item.getId()).isEqualTo(savedItemId);

    }

    @Test
    public void 아이템수정() throws Exception {
        //given
        Member member = new Member("test@test.com", "test123!", "test");
        Item item = new Item(member, "item", "imgpath", "item", 100, 10000L);

        Optional<Item> findItem = Optional.ofNullable(item);
        given(itemRepository.findById(item.getId())).willReturn(findItem);

        //when
        itemService.updateItem(item.getId(), "updateItem", "updateImgpath", "updateItem", 1000,
            1000L);

        //then
        Item retrieveItem = itemService.findOne(item.getId());

        assertThat(retrieveItem.getItemName()).isEqualTo("updateItem");
        assertThat(retrieveItem.getImgUrl()).isEqualTo("updateImgpath");
        assertThat(retrieveItem.getDescription()).isEqualTo("updateItem");
        assertThat(retrieveItem.getStockQuantity()).isEqualTo(1000);
        assertThat(retrieveItem.getPrice()).isEqualTo(1000L);

        assertThrows(NotFoundException.class, () -> {
            itemService.updateItem(1000000L, "updateItem", "updateImgpath", "updateItem", 1000,
                1000L);
        });

    }
}
