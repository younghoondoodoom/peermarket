package peermarket.peershop.repsoitory;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import peermarket.peershop.entity.Item;
import peermarket.peershop.entity.ItemReview;
import peermarket.peershop.entity.Member;
import peermarket.peershop.repository.ItemRepository;
import peermarket.peershop.repository.ItemReviewRepository;
import peermarket.peershop.repository.MemberRepository;

@Transactional
@SpringBootTest
public class ItemReviewRepositoryTest {

    @Autowired
    ItemReviewRepository itemReviewRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 리뷰등록() throws Exception {
        //given
        Member member = new Member("test@test.com", "test123!", "test");
        memberRepository.save(member);
        Item item = new Item(member, "item", "test", "item", 10, 10000L);
        itemRepository.save(item);
        ItemReview review = new ItemReview(member, item, 5, "test good"); //is deleted default는 true

        //when
        ItemReview itemReview = itemReviewRepository.save(review);

        //then
        assertThat(itemReview).isNotNull();
        assertThat(itemReview.getMember().getEmail()).isEqualTo("test@test.com");
        assertThat(itemReview.getItem().getItemName()).isEqualTo("item");
        assertThat(itemReview.getRating()).isEqualTo(5);
        assertThat(itemReview.getCreatedAt()).isNotNull();
        assertThat(itemReview.getLastModifiedAt()).isNotNull();
        assertThat(itemReview.getIsDeleted()).isEqualTo(true);

    }

}
