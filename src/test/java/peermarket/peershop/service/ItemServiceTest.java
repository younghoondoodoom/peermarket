package peermarket.peershop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import peermarket.peershop.entity.Item;
import peermarket.peershop.entity.Member;
import peermarket.peershop.exception.NotFoundException;
import peermarket.peershop.repository.ItemReviewRepository;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemService itemService;
    @Autowired
    ItemReviewRepository itemReviewRepository;
    @Autowired
    MemberService memberService;


    @Test
    public void 아이템_리스트() throws Exception {
        //given
        Member member = new Member("test@test.com", "test123!", "test");
        memberService.save(member);

        Item item1 = new Item(member, "item1", "imgpath", "item1", 100, 10000L);
        Item item2 = new Item(member, "item2", "imgpath", "item2", 100, 10000L);
        Item item3 = new Item(member, "item3", "imgpath", "item3", 100, 10000L);
        Item item4 = new Item(member, "item4", "imgpath", "item4", 100, 10000L);
        Item item5 = new Item(member, "item5", "imgpath", "item5", 100, 10000L);
        itemService.saveItem(item1);
        itemService.saveItem(item2);
        itemService.saveItem(item3);
        itemService.saveItem(item4);
        itemService.saveItem(item5);

        //when
        Pageable pageable = PageRequest.of(0, 1);
        Page<Item> items = itemService.findItems(pageable);

        //then
        assertThat(items.getSize()).isEqualTo(1);
        assertThat(items.getContent().get(0)).isEqualTo(item5);

    }

    @Test
    public void 아이템_디테일() throws Exception {
        //given
        Member member = new Member("test@test.com", "test123!", "test");
        memberService.save(member);

        Item item1 = new Item(member, "item1", "imgpath", "item1", 100, 10000L);
        Item item2 = new Item(member, "item2", "imgpath", "item2", 100, 10000L);
        Item item3 = new Item(member, "item3", "imgpath", "item3", 100, 10000L);
        Item item4 = new Item(member, "item4", "imgpath", "item4", 100, 10000L);
        Item item5 = new Item(member, "item5", "imgpath", "item5", 100, 10000L);
        Long id1 = itemService.saveItem(item1);
        Long id2 = itemService.saveItem(item2);
        Long id3 = itemService.saveItem(item3);
        Long id4 = itemService.saveItem(item4);
        Long id5 = itemService.saveItem(item5);

        //when
        Item findItem1 = itemService.findOne(id1);
        Item findItem2 = itemService.findOne(id2);
        Item findItem3 = itemService.findOne(id3);
        Item findItem4 = itemService.findOne(id4);
        Item findItem5 = itemService.findOne(id5);

        //then
        assertThat(findItem1.getItemName()).isEqualTo("item1");
        assertThat(findItem2.getImgUrl()).isEqualTo("imgpath");
        assertThat(findItem3.getDescription()).isEqualTo("item3");
        assertThat(findItem4.getPrice()).isEqualTo(10000L);
        assertThat(findItem5.getStockQuantity()).isEqualTo(100);

        assertThrows(NotFoundException.class, () -> {
            itemService.findOne(10000L);
        });

    }

    @Test
    public void 아이템_등록() throws Exception {
        //given
        Member member = new Member("test@test.com", "test123!", "test");
        memberService.save(member);

        //when
        Item item = new Item(member, "item", "imgpath", "item1", 100, 10000L);
        Long itemId = itemService.saveItem(item);

        //then
        assertThat(itemId).isEqualTo(item.getId());

    }

    @Test
    public void 본인아이템조회() throws Exception {
        //given
        Member member = new Member("test@test.com", "test123!", "test");
        memberService.save(member);

        Item item1 = new Item(member, "item1", "imgpath", "item1", 100, 10000L);
        Item item2 = new Item(member, "item2", "imgpath", "item2", 100, 10000L);
        Item item3 = new Item(member, "item3", "imgpath", "item3", 100, 10000L);
        Item item4 = new Item(member, "item4", "imgpath", "item4", 100, 10000L);
        Item item5 = new Item(member, "item5", "imgpath", "item5", 100, 10000L);
        Long id1 = itemService.saveItem(item1);
        Long id2 = itemService.saveItem(item2);
        Long id3 = itemService.saveItem(item3);
        Long id4 = itemService.saveItem(item4);
        Long id5 = itemService.saveItem(item5);

        //when
        Pageable pageable = PageRequest.of(0, 2, Sort.by("createdAt").descending());
        Page<Item> findOwnItems = itemService.findItemsByMember(member, pageable);

        //then
        assertThat(findOwnItems.getContent().get(0).getMember().getId()).isEqualTo(member.getId());
        assertThat(findOwnItems.getTotalElements()).isEqualTo(5);

    }

    @Test
    public void 아이템수정() throws Exception {
        //given
        Member member = new Member("test@test.com", "test123!", "test");
        memberService.save(member);
        Item item1 = new Item(member, "item1", "imgpath", "item1", 100, 10000L);
        itemService.saveItem(item1);

        //when
        itemService.updateItem(item1.getId(), "updateItem", "updateImgPath", "updateItem", 10,
            1000L);

        //then
        Item findItem = itemService.findOne(item1.getId());
        assertThat(findItem.getItemName()).isEqualTo("updateItem");
        assertThat(findItem.getPrice()).isEqualTo(1000L);

    }

    @Test
    public void 아이템삭제() throws Exception {
        //given
        Member member = new Member("test@test.com", "test123!", "test");
        memberService.save(member);
        Item item1 = new Item(member, "item1", "imgpath", "item1", 100, 10000L);
        itemService.saveItem(item1);

        //when
        Long id = item1.getId();
        itemService.deleteItem(id);

        //then
        assertThrows(NotFoundException.class, () -> itemService.findOne(id));

    }

}