package peermarket.peershop.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import peermarket.peershop.entity.Member;

// 시큐리티 설정에서 loginProcessingUrl("/member/login")
// 로그인 요청이 오면 자동으로 UserDetailsService 타입으로 ioc되어 있는 loadUserByUsername 함수가 실행
public interface MemberService extends UserDetailsService {
    Long save(Member member);

}
