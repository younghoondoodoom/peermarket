package peermarket.peershop.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import peermarket.peershop.controller.dto.JoinMemberDto;
import peermarket.peershop.entity.Member;
import peermarket.peershop.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public String home() {
        return "/home";
    }

    @GetMapping("/member/signup")
    public String signup(Model model) {
        model.addAttribute("joinMemberDto", new JoinMemberDto());
        return "/user/register";
    }

    @PostMapping("/member/signup")
    public String signup(@Valid JoinMemberDto joinMemberDto, BindingResult result) {
        if (result.hasErrors()) {
            return "/user/register";
        }
        String encodePassword = bCryptPasswordEncoder.encode(joinMemberDto.getPassword());
        Member member = new Member(joinMemberDto.getEmail(), encodePassword,
            joinMemberDto.getUsername());
        member.setRole("ROLE_USER");
        memberService.save(member);
        return "redirect:/";
    }

    @GetMapping("/member/loginForm")
    public String login() {
        return "/user/login";
    }

}
