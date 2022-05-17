package peermarket.peershop.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import peermarket.peershop.controller.dto.JoinUserDto;
import peermarket.peershop.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("joinUserDto", new JoinUserDto());
        return "/user/register";
    }

    @PostMapping("/register")
    public String register(@Valid JoinUserDto joinUserDto, BindingResult result) {
        if (result.hasErrors()) {
            return "/user/register";
        }
        return null;
    }
}
