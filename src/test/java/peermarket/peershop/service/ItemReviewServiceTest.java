package peermarket.peershop.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import peermarket.peershop.entity.Item;
import peermarket.peershop.entity.ItemReview;
import peermarket.peershop.entity.Member;
import peermarket.peershop.repository.ItemReviewRepository;

@SpringBootTest
@Transactional
class ItemReviewServiceTest {

    @Autowired
    ItemService itemService;
    @Autowired
    ItemReviewRepository itemReviewRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    ItemReviewService itemReviewService;

    @Test
    public void 아이템_댓글_저장() throws Exception {
        //given
        Member member = new Member("test@test.com", "test123!", "test");
        memberService.save(member);
        Item item = new Item(member, "item", "imgpath", "item1", 100, 10000L);
        Long itemId = itemService.saveItem(item);

        //when
        ItemReview itemReview1 = new ItemReview(member, item, 4, "테스트입니다.");
        Long reviewId1 = itemReviewService.saveItemReview(itemReview1);

        //then
        Optional<ItemReview> findItemReview = itemReviewRepository.findById(reviewId1);
        Item findItem = itemService.findOne(itemId);

        assertThat(findItemReview.get().getMember().getId()).isEqualTo(member.getId());
        assertThat(findItemReview.get().getItem().getId()).isEqualTo(item.getId());
        assertThat(findItemReview.get().getRating()).isEqualTo(4);
        assertThat(findItemReview.get().getComment()).isEqualTo("테스트입니다.");

    }

    @Test
    public void 댓글리스트() throws Exception {
        //given
        Member member = new Member("test@test.com", "test123!", "test");
        memberService.save(member);
        Item item = new Item(member, "item", "imgpath", "item1", 100, 10000L);
        Long itemId = itemService.saveItem(item);

        ItemReview itemReview1 = new ItemReview(member, item, 4, "테스트입니다.");
        ItemReview itemReview2 = new ItemReview(member, item, 3, "테스트입니다.");
        ItemReview itemReview3 = new ItemReview(member, item, 3, "테스트입니다.");
        Long reviewId1 = itemReviewService.saveItemReview(itemReview1);
        Long reviewId2 = itemReviewService.saveItemReview(itemReview2);
        Long reviewId3 = itemReviewService.saveItemReview(itemReview3);

        //when
        Pageable pageable = PageRequest.of(0, 20, Sort.by("createdAt").descending());
        Page<ItemReview> reviewList = itemReviewService.findReviews(item.getId(), pageable);

        //then
        assertThat(reviewList.getTotalElements()).isEqualTo(3);
        assertThat(reviewList.getContent().get(0).getId()).isEqualTo(itemReview3.getId());
        assertThat(reviewList.getTotalPages()).isEqualTo(1);

    }

}