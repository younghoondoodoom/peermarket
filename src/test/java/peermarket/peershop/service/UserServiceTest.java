package peermarket.peershop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import peermarket.peershop.controller.dto.JoinUserDto;
import peermarket.peershop.exception.AlreadyExistException;
import peermarket.peershop.repository.UserRepository;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;


    @Test
    public void 회원가입() throws Exception {
        //given
        JoinUserDto joinUserDto = new JoinUserDto("test123@test.com", "테스트", "test1234",
            "test1234");

        //when
        Long joinId = userService.join(joinUserDto);

        //then
        assertThat(userRepository.findById(joinId).get().getUsername()).isEqualTo(
            joinUserDto.getUsername());
    }

    @Test
    public void 중복이메일테스트() throws Exception {
        //given
        JoinUserDto joinUserDto1 = new JoinUserDto("test123@test.com", "테스트", "test1234",
            "test1234");
        JoinUserDto joinUserDto2 = new JoinUserDto("test123@test.com", "테스트1", "test1234",
            "test1234");

        //when
        userService.join(joinUserDto1);

        //then
        assertThrows(AlreadyExistException.class, () -> {
            userService.join(joinUserDto2);
        });

    }

    @Test
    public void 중복이름테스트() throws Exception {
        //given
        JoinUserDto joinUserDto1 = new JoinUserDto("test123@test.com", "테스트", "test1234",
            "test1234");
        JoinUserDto joinUserDto2 = new JoinUserDto("test1234@test.com", "테스트", "test1234",
            "test1234");

        //when
        userService.join(joinUserDto1);

        //then
        assertThrows(AlreadyExistException.class, () -> {
            userService.join(joinUserDto2);
        });

    }
}