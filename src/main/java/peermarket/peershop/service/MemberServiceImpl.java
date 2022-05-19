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
import peermarket.peershop.config.auth.PrincipleDetails;
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
    public Long save(Member member) {
        if (!memberRepository.findByUsername(member.getUsername()).isEmpty()) {
            throw new AlreadyExistException("이미 존재하는 이름입니다.");
        } else if (!memberRepository.findByEmail(member.getEmail()).isEmpty()) {
            throw new AlreadyExistException("이미 존재하는 이메일입니디.");
        }
        memberRepository.save(member);
        return member.getId();
    }


    // Security session = Authentication(principleDetails(member))
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        if (memberOptional.isPresent()) {
            return new PrincipleDetails(memberOptional.get());
        }
        return null;
    }
}
