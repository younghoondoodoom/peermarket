package peermarket.peershop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peermarket.peershop.controller.dto.JoinUserDto;
import peermarket.peershop.entity.User;
import peermarket.peershop.exception.AlreadyExistException;
import peermarket.peershop.repository.UserRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    /**
     * 회원가입
     */
    @Transactional
    public Long join(JoinUserDto joinUserDto) {
        if (!userRepository.findByUsername(joinUserDto.getUsername()).isEmpty()) {
            throw new AlreadyExistException("이미 존재하는 이름입니다.");
        } else if (!userRepository.findByEmail(joinUserDto.getEmail()).isEmpty()) {
            throw new AlreadyExistException("이미 존재하는 이메일입니디.");
        }
        User user = convertToUser(joinUserDto);
        userRepository.save(user);
        return user.getId();
    }

    private User convertToUser(JoinUserDto joinUserDto) {
        return new User(joinUserDto.getEmail(), passwordEncoder.encode(joinUserDto.getPassword()), joinUserDto.getUsername());
    }

}
