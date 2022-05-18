package peermarket.peershop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import peermarket.peershop.controller.dto.JoinMemberDto;
import peermarket.peershop.exception.AlreadyExistException;
import peermarket.peershop.repository.MemberRepository;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberServiceImpl userService;


    @Test
    public void 회원가입() throws Exception {
        //given
        JoinMemberDto joinMemberDto = new JoinMemberDto("test123@test.com", "테스트", "test1234",
            "test1234");

        //when
        Long joinId = userService.save(joinMemberDto);

        //then
        assertThat(memberRepository.findById(joinId).get().getUsername()).isEqualTo(
            joinMemberDto.getUsername());
    }

    @Test
    public void 중복이메일테스트() throws Exception {
        //given
        JoinMemberDto joinMemberDto1 = new JoinMemberDto("test123@test.com", "테스트", "test1234",
            "test1234");
        JoinMemberDto joinMemberDto2 = new JoinMemberDto("test123@test.com", "테스트1", "test1234",
            "test1234");

        //when
        userService.save(joinMemberDto1);

        //then
        assertThrows(AlreadyExistException.class, () -> {
            userService.save(joinMemberDto2);
        });

    }

    @Test
    public void 중복이름테스트() throws Exception {
        //given
        JoinMemberDto joinMemberDto1 = new JoinMemberDto("test123@test.com", "테스트", "test1234",
            "test1234");
        JoinMemberDto joinMemberDto2 = new JoinMemberDto("test1234@test.com", "테스트", "test1234",
            "test1234");

        //when
        userService.save(joinMemberDto1);

        //then
        assertThrows(AlreadyExistException.class, () -> {
            userService.save(joinMemberDto2);
        });

    }
}