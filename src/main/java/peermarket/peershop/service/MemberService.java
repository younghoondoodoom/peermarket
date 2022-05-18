package peermarket.peershop.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import peermarket.peershop.controller.dto.JoinMemberDto;

public interface MemberService extends UserDetailsService {
    Long save(JoinMemberDto joinMemberDto);

}
