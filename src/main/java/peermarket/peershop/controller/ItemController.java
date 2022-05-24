package peermarket.peershop.controller;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import peermarket.peershop.controller.dto.ItemListDto;
import peermarket.peershop.repository.ItemRepository;
import peermarket.peershop.service.ItemService;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ItemRepository itemRepository;

    @GetMapping("/")
    public String findItems(@PageableDefault Pageable pageable, Model model) {
        Page<ItemListDto> items = itemService.findItems(pageable).map(ItemListDto::new);
        model.addAttribute("items", items);
        return "/home";
    }


}