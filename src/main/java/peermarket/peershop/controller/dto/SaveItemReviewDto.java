package peermarket.peershop.controller.dto;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveItemReviewDto {

    @NotEmpty
    private Long id;

    @NotEmpty
    private Long itemId;

    @NotEmpty(message = "댓글을 입력해주세요.")
    private String comment;

    @NotEmpty(message = "평점을 입력해주세요.")
    private Integer rating;

}
