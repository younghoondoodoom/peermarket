package peermarket.peershop.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import peermarket.peershop.controller.dto.ItemListDto;
import peermarket.peershop.controller.dto.ItemOneDto;
import peermarket.peershop.controller.dto.ItemReviewDto;
import peermarket.peershop.controller.dto.SaveItemDto;
import peermarket.peershop.controller.dto.SaveItemReviewDto;
import peermarket.peershop.entity.Item;
import peermarket.peershop.entity.ItemReview;
import peermarket.peershop.entity.Member;
import peermarket.peershop.security.CurrentMember;
import peermarket.peershop.security.PrincipalDetails;
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
    public String findOne(@PathVariable("id") Long id,
        @PageableDefault(size = 10, sort = "createdAt",
            direction = Direction.DESC) Pageable pageable, Model model) {
        Item item = itemService.findOne(id);
        Page<ItemReviewDto> itemReviews = itemService.findReviews(id, pageable).map(
            ItemReviewDto::new);
        ItemOneDto itemDto = new ItemOneDto(item);
        model.addAttribute("item", itemDto);
        model.addAttribute("itemReviews", itemReviews);
        model.addAttribute("saveItemReviewDto", new SaveItemReviewDto());
        return "/item/detail";
    }

    @PostMapping("/item/{id}/review")
    public String registerReview(@Valid SaveItemReviewDto saveItemReviewDto,
        @CurrentMember PrincipalDetails currentMember,
        @PathVariable Long id,
        BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/item/" + id;
        }
        if (currentMember == null) {
            return "redirect:/item/" + id;
        }
        Item item = itemService.findOne(id);
        Member member = currentMember.getMember();
        ItemReview itemReview = new ItemReview(member, item, saveItemReviewDto.getRating(),
            saveItemReviewDto.getComment());
        itemService.saveItemReview(itemReview);
        return "redirect:/item/" + id;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("item/register")
    public String getRegisterItemForm(Model model) {
        model.addAttribute("saveItemDto", new SaveItemDto());
        return "/item/register";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("item/register")
    public String registerItem(@Valid SaveItemDto saveItemDto, BindingResult result,
        @CurrentMember PrincipalDetails currentMember) {
        if (result.hasErrors()) {
            return "/item/register";
        }
        Member member = currentMember.getMember();
        Item registerItem = new Item(member, saveItemDto.getItemName(), saveItemDto.getImgUrl(),
            saveItemDto.getDescription(), saveItemDto.getStockQuantity(), saveItemDto.getPrice());
        Long id = itemService.saveItem(registerItem);
        return "redirect:/item/" + id;
    }
}
