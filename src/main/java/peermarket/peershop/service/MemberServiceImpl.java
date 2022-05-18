package peermarket.peershop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peermarket.peershop.controller.dto.JoinMemberDto;
import peermarket.peershop.entity.Member;
import peermarket.peershop.exception.AlreadyExistException;
import peermarket.peershop.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    @Override
    @Transactional
    public Long save(JoinMemberDto joinMemberDto) {
        if (!memberRepository.findByUsername(joinMemberDto.getUsername()).isEmpty()) {
            throw new AlreadyExistException("이미 존재하는 이름입니다.");
        } else if (!memberRepository.findByEmail(joinMemberDto.getEmail()).isEmpty()) {
            throw new AlreadyExistException("이미 존재하는 이메일입니디.");
        }
        Member member = convertToUser(joinMemberDto);
        memberRepository.save(member);
        return member.getId();
    }

    private Member convertToUser(JoinMemberDto joinMemberDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return new Member(joinMemberDto.getEmail(), passwordEncoder.encode(joinMemberDto.getPassword()), joinMemberDto.getUsername());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> userEntityWrapper = memberRepository.findByUsername(username);
        Member userEntity = userEntityWrapper.orElseGet(null);

        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));

        return new User(userEntity.getEmail(), userEntity.getPassword(), authorities);

    }
}
