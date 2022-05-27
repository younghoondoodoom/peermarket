package peermarket.peershop.controller;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import peermarket.peershop.controller.dto.ItemListDto;
import peermarket.peershop.controller.dto.ItemOneDto;
import peermarket.peershop.controller.dto.ItemReviewDto;
import peermarket.peershop.controller.dto.SaveItemReviewDto;
import peermarket.peershop.entity.Item;
import peermarket.peershop.entity.ItemReview;
import peermarket.peershop.entity.Member;
import peermarket.peershop.repository.ItemRepository;
import peermarket.peershop.service.ItemService;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/")
    public String findItems(@PageableDefault Pageable pageable, Model model) {
        Page<ItemListDto> items = itemService.findItems(pageable).map(ItemListDto::new);
        model.addAttribute("items", items);
        return "/home";
    }

    @GetMapping("/item/{id}")
    public String findOne(@PathVariable("id") Long id, Pageable pageable, Model model) {
        Item item = itemService.findOne(id);
        Page<ItemReviewDto> itemReviews = itemService.findReviews(id, pageable).map(
            ItemReviewDto::new);
        ItemOneDto itemDto = new ItemOneDto(item);
        model.addAttribute("item", itemDto);
        model.addAttribute("itemReviews", itemReviews);
        model.addAttribute("SaveItemReviewDto", new SaveItemReviewDto());
        return "/item/detail";
    }

    @PostMapping("/item/review")
    public String registerReview(@Valid SaveItemReviewDto saveItemReviewDto, @AuthenticationPrincipal Member member,BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/item/" + saveItemReviewDto.getItemId();
        }
        Item item = itemService.findOne(saveItemReviewDto.getItemId());
        ItemReview itemReview = new ItemReview(member, item, saveItemReviewDto.getRating(),
            saveItemReviewDto.getComment());
        itemService.saveItemReview(itemReview);
        return "redirect:/item/" + saveItemReviewDto.getItemId();
    }
}
