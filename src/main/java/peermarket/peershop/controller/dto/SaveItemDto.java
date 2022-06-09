package peermarket.peershop.controller.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveItemDto {

    @NotEmpty(message = "상품 이름은 필수 입력 사항입니다.")
    private String itemName;

    @NotEmpty(message = "이미지는 필수 입력 사항입니다.")
    private String imgUrl;

    @NotEmpty(message = "설명란은 필수 입력 사항입니다.")
    private String description;

    @NotNull(message = "재고는 필수 입력 사항입니다.")
    @Min(value = 1, message = "재고는 1개 이상이어야 합니다.")
    private Integer stockQuantity;

    @NotNull(message = "가격은 필수 입력 사항입니다.")
    private Long price;

}
