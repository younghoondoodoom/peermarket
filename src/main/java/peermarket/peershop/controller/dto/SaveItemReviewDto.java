package peermarket.peershop.controller.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveItemReviewDto {

    @NotEmpty(message = "댓글을 입력해주세요.")
    private String comment;

    @NotNull(message = "평점을 입력해주세요.")
    private Integer rating;

}
