package peermarket.peershop;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import peermarket.peershop.entity.Item;
import peermarket.peershop.entity.ItemReview;
import peermarket.peershop.entity.Member;
import peermarket.peershop.repository.ItemRepository;
import peermarket.peershop.repository.ItemReviewRepository;
import peermarket.peershop.repository.MemberRepository;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final ItemReviewRepository itemReviewRepository;

    @PostConstruct
    public void init() {
        createMember();
        createItem();
        createReview();
    }

    private void createReview() {
        for (int i = 0; i < 30; i++) {
            itemReviewRepository.save(new ItemReview(memberRepository.findById(1L).get(),
                itemRepository.findById(1L).get(), 3, "test"));
        }
    }

    private void createItem() {
        for (long i = 0; i < 10; i++) {
            Member member = memberRepository.getById(1L);
            itemRepository.save(new Item(member, "item" + i, "1",
                "아이템" + i + "입니다", 10, 10000L));
        }
    }

    private void createMember() {
        for (int i = 0; i < 5; i++) {
            String encodePassword = bCryptPasswordEncoder.encode("test123");
            Member member = new Member("member" + i + "@test.com", encodePassword, "member" + i);
            member.setRole("ROLE_USER");
            memberRepository.save(member);
        }
    }


}
