package peermarket.peershop.security;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import peermarket.peershop.entity.Member;
import peermarket.peershop.repository.MemberRepository;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        Member member = memberRepository.findByUsername(authentication.getName()).get();
        member.updateLastLogin(LocalDateTime.now());
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
