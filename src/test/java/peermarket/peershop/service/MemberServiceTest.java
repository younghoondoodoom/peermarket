package peermarket.peershop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import peermarket.peershop.entity.Member;
import peermarket.peershop.exception.AlreadyExistException;
import peermarket.peershop.repository.MemberRepository;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;


    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member("test@test.com", "test1234", "테스트");

        //when
        Long joinId = memberService.save(member);

        //then
        assertThat(memberRepository.findById(joinId).get().getUsername()).isEqualTo(
            member.getUsername());
    }

    @Test
    public void 중복이메일테스트() throws Exception {
        //given
        Member member1 = new Member("test@test.com", "test1234", "테스트");
        Member member2 = new Member("test@test.com", "test1234", "테스트1");

        //when
        memberService.save(member1);

        //then
        assertThrows(AlreadyExistException.class, () -> {
            memberService.save(member2);
        });

    }

    @Test
    public void 중복이름테스트() throws Exception {
        //given
        Member member1 = new Member("test@test.com", "test1234", "테스트");
        Member member2 = new Member("test1@test.com", "test1234", "테스트");

        //when
        memberService.save(member1);

        //then
        assertThrows(AlreadyExistException.class, () -> {
            memberService.save(member2);
        });

    }
}