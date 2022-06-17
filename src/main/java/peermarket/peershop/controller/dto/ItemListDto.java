package peermarket.peershop.controller.dto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.asm.Advice.Local;
import peermarket.peershop.entity.Item;

@Getter
@Setter
@AllArgsConstructor
public class ItemListDto {

    private Long id;
    private String itemName;
    private String imgUrl;
    private Long price;
    private Integer stockQuantity;
    private LocalDateTime createdAt;

    public ItemListDto(Item item) {
        this.id = item.getId();
        this.itemName = item.getName();
        this.imgUrl = item.getImgUrl();
        this.price = item.getPrice();
        this.stockQuantity = item.getStockQuantity();
        this.createdAt = item.getCreatedAt();
    }

}
