package peermarket.peershop.controller.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import peermarket.peershop.entity.Item;

@Getter
@Setter
public class ItemOneDto {

    private Long id;
    private MemberSimpleDto member;
    private String itemName;
    private String imgUrl;
    private String description;
    private Integer stockQuantity;
    private Long price;
    private LocalDateTime lastModifiedAt;

    public ItemOneDto(Item item) {
        this.id = item.getId();
        this.member = new MemberSimpleDto(item.getMember().getId(), item.getMember().getUsername());
        this.itemName = item.getItemName();
        this.imgUrl = item.getImgUrl();
        this.description = item.getDescription();
        this.stockQuantity = item.getStockQuantity();
        this.price = item.getPrice();
        this.lastModifiedAt = item.getLastModifiedAt();
    }

}
