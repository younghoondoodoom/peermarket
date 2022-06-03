package peermarket.peershop.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import peermarket.peershop.entity.Member;

public interface MemberService extends UserDetailsService {
    Long save(Member member);

}
