package peermarket.peershop.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import peermarket.peershop.entity.ItemReview;

@Getter
@Setter
@NoArgsConstructor
public class ItemReviewDto {

    private Long id;

    private String memberName;

    private Integer rating;

    private String comment;

    private LocalDateTime lastModifiedAt;

    public ItemReviewDto(ItemReview itemReview) {
        this.id = itemReview.getId();
        this.memberName = itemReview.getMember().getUsername();
        this.rating = itemReview.getRating();
        this.comment = itemReview.getComment();
        this.lastModifiedAt = itemReview.getLastModifiedAt();
    }
}
