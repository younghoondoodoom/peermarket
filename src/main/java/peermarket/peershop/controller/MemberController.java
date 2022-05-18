package peermarket.peershop.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import peermarket.peershop.controller.dto.JoinMemberDto;
import peermarket.peershop.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/")
    public String home() {
        return "/home";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("joinUserDto", new JoinMemberDto());
        return "/user/register";
    }

    @PostMapping("/signup")
    public String signup(@Valid JoinMemberDto joinMemberDto, BindingResult result) {
        if (result.hasErrors()) {
            return "/user/register";
        }
        memberService.save(joinMemberDto);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "/user/login";
    }

}
