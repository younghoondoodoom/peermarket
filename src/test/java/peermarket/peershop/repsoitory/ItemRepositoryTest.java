package peermarket.peershop.repsoitory;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import peermarket.peershop.entity.Item;
import peermarket.peershop.entity.Member;
import peermarket.peershop.repository.ItemRepository;
import peermarket.peershop.repository.MemberRepository;

@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 특정유저_아이템_모음() throws Exception {
        //given
        Member member = new Member("test@test.com", "test123!", "test");
        memberRepository.save(member);

        Item item1 = new Item(member, "item1", "imgpath", "item1", 100, 10000L);
        Item item2 = new Item(member, "item2", "imgpath", "item2", 100, 10000L);
        Item item3 = new Item(member, "item3", "imgpath", "item3", 100, 10000L);
        Item item4 = new Item(member, "item4", "imgpath", "item4", 100, 10000L);
        Item item5 = new Item(member, "item5", "imgpath", "item5", 100, 10000L);
        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);
        itemRepository.save(item4);
        itemRepository.save(item5);

        //when
        Pageable pageable = PageRequest.of(0, 2, Sort.by("createdAt").descending());
        Page<Item> findOwnItems = itemRepository.findByMember(member, pageable);

        //then
        assertThat(findOwnItems.getTotalElements()).isEqualTo(5);
        assertThat(findOwnItems.getTotalPages()).isEqualTo(3);
        assertThat(findOwnItems.getSize()).isEqualTo(2);
        assertThat(findOwnItems.getContent().get(0).getId()).isEqualTo(item5.getId());

    }

    @Test
    public void 아이템검색() throws Exception {
        //given
        Member member = new Member("test@test.com", "test123!", "test");
        memberRepository.save(member);

        Item item1 = new Item(member, "item1", "imgpath", "item1", 100, 10000L);
        Item item2 = new Item(member, "item2", "imgpath", "item2", 100, 10000L);
        Item item3 = new Item(member, "item3", "imgpath", "item3", 100, 10000L);
        Item item4 = new Item(member, "item4", "imgpath", "item4", 100, 10000L);
        Item item5 = new Item(member, "item5", "imgpath", "item5", 100, 10000L);
        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);
        itemRepository.save(item4);
        itemRepository.save(item5);

        //when
        Pageable pageable = PageRequest.of(0, 2, Sort.by("createdAt").descending());
        Page<Item> findItems = itemRepository.findByItemNameContaining("item", pageable);
        Page<Item> findNoThing = itemRepository.findByItemNameContaining("noThing", pageable);

        //then
        assertThat(findItems.getTotalElements()).isEqualTo(5);
        assertThat(findItems.getSize()).isEqualTo(2);
        assertThat(findItems.getContent()).contains(item5);

        assertThat(findNoThing.getTotalElements()).isEqualTo(0);
        assertThat(findNoThing.getContent()).doesNotContain(item5);

    }

}