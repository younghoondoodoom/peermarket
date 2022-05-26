package peermarket.peershop.controller;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import peermarket.peershop.controller.dto.JoinMemberDto;
import peermarket.peershop.entity.Item;
import peermarket.peershop.entity.Member;
import peermarket.peershop.repository.ItemRepository;
import peermarket.peershop.repository.MemberRepository;
import peermarket.peershop.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

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

    @PostConstruct
    public void init() {
        for (int i = 0; i < 5; i++) {
            String encodePassword = bCryptPasswordEncoder.encode("test123");
            Member member = new Member("member" + i + "@test.com", encodePassword, "member" + i);
            member.setRole("ROLE_USER");
            memberRepository.save(member);
        }
        for (long i = 0; i < 10; i++) {
            Member member = memberRepository.getById(1L);
            itemRepository.save(new Item(member, "item" + i, "1",
                "아이템" + i + "입니다", 10, 10000L));

        }
    }

}
