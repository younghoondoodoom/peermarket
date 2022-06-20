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
import org.springframework.web.bind.annotation.RequestParam;
import peermarket.peershop.controller.dto.ItemListDto;
import peermarket.peershop.controller.dto.ItemOneDto;
import peermarket.peershop.controller.dto.ItemReviewDto;
import peermarket.peershop.controller.dto.SaveItemDto;
import peermarket.peershop.controller.dto.SaveItemReviewDto;
import peermarket.peershop.controller.dto.UpdateItemDto;
import peermarket.peershop.entity.Item;
import peermarket.peershop.entity.ItemReview;
import peermarket.peershop.entity.Member;
import peermarket.peershop.security.CurrentMember;
import peermarket.peershop.security.PrincipalDetails;
import peermarket.peershop.service.ItemReviewService;
import peermarket.peershop.service.ItemService;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ItemReviewService itemReviewService;

//    @GetMapping("/")
//    public String findItems(@PageableDefault Pageable pageable, Model model) {
//        Page<ItemListDto> items = itemService.findItems(pageable).map(ItemListDto::new);
//        model.addAttribute("items", items);
//        return "/home";
//    }

    @GetMapping("/item/{id}")
    public String findOne(@PathVariable("id") Long id,
        @PageableDefault(size = 10, sort = "createdAt",
            direction = Direction.DESC) Pageable pageable,
        @CurrentMember PrincipalDetails currentMember, Model model) {
        Item item = itemService.findOne(id);
        Page<ItemReviewDto> itemReviews = itemReviewService.findReviews(id, pageable).map(
            ItemReviewDto::new);
        ItemOneDto itemDto = new ItemOneDto(item);

        model.addAttribute("isOwner",
            currentMember != null ? item.getMember().getId() == currentMember.getMember().getId()
                : false);
        model.addAttribute("item", itemDto);
        model.addAttribute("itemReviews", itemReviews);
        model.addAttribute("saveItemReviewDto", new SaveItemReviewDto());
        return "/item/detail";
    }

    @PostMapping("/item/{id}/review")
    public String registerReview(@Valid SaveItemReviewDto saveItemReviewDto, BindingResult result,
        @CurrentMember PrincipalDetails currentMember,
        @PathVariable Long id) {
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
        itemReviewService.saveItemReview(itemReview);
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("item/findItemByMember")
    public String findItemByMember(@CurrentMember PrincipalDetails currentMember,
        @PageableDefault(sort = "createdAt", direction = Direction.DESC) Pageable pageable,
        Model model) {
        Member member = currentMember.getMember();
        Page<Item> findItems = itemService.findItemsByMember(member, pageable);
        model.addAttribute("items", findItems);
        return "/item/list";
    }

    @PreAuthorize("isAuthenticated() and @itemService.isOwnItem(#id, #currentMember.getMember())")
    @GetMapping("item/{id}/update")
    public String getUpdateItemForm(@CurrentMember PrincipalDetails currentMember, Model model,
        @PathVariable("id") Long id) {
        Item item = itemService.findOne(id);
        model.addAttribute("item", item);
        model.addAttribute("updateItemDto", new UpdateItemDto());
        return "item/update";
    }

    @PreAuthorize("isAuthenticated() and @itemService.isOwnItem(#id, #currentMember.getMember())")
    @PostMapping("item/{id}/update")
    public String updateItem(@Valid UpdateItemDto updateItemDto, BindingResult result,
        @CurrentMember PrincipalDetails currentMember, @PathVariable("id") Long id, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("id", id);
            return "redirect:/item/{id}/update";
        }
        itemService.updateItem(id, updateItemDto.getItemName(), updateItemDto.getImgUrl(),
            updateItemDto.getDescription(),
            updateItemDto.getStockQuantity(), updateItemDto.getPrice());
        return "redirect:/item/" + id;
    }

    @PreAuthorize("isAuthenticated() and @itemService.isOwnItem(#id, #currentMember.getMember())")
    @PostMapping("item/{id}/delete")
    public String deleteItem(@PathVariable("id") Long id,
        @CurrentMember PrincipalDetails currentMember) {
        itemService.deleteItem(id);
        return "redirect:/";
    }

    @GetMapping("item/search")
    public String searchItem(@RequestParam(value = "itemName") String itemName, Model model,
        @PageableDefault(sort = "createdAt", direction = Direction.DESC) Pageable pageable) {
        Page<Item> findItems = itemService.searchItem(itemName, pageable);
        model.addAttribute("items", findItems);
        return "/item/list";
    }

}
