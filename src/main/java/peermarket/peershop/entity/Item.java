package peermarket.peershop.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import peermarket.peershop.entity.base.BaseTimeEntity;
import peermarket.peershop.entity.status.ItemStatus;
import peermarket.peershop.exception.NotEnoughStockException;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String itemName;

    @Column(length = 2000)
    private String imgUrl;

    private String description;

    private Integer stockQuantity;

    private Long price;

    private String ratingAverage = "0";

    private Integer ratingCount = 0;

    @Enumerated(EnumType.STRING)
    private ItemStatus status = ItemStatus.ON_SALE;

    @OneToMany
    @JoinColumn(name = "item_id")
    private List<CategoryItem> categoryItems = new ArrayList<>();

    public Item(Member member, String itemName, String imgUrl, String description, Integer stockQuantity, Long price) {
        this.member = member;
        this.itemName = itemName;
        this.imgUrl = imgUrl;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.price = price;
    }


    /**
     * 재고 증가
     */
    public void addStock(int stockQuantity) {
        this.stockQuantity += stockQuantity;
    }

    /**
     * 재고 감소
     */
    public void removeStock(int stockQuantity) {
        int restStock = this.stockQuantity - stockQuantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("재고가 마이너스 입니다.");
        }
        this.stockQuantity = restStock;
    }

    /**
     * 평균 평점 수정
     */
    public void updateRatingAverage(String ratingAverage) {
        this.ratingAverage = ratingAverage;
    }

    public void updateRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public void updateItemInfo(String itemName, String imgUrl, String description,
        Integer stockQuantity, Long price) {
        this.itemName = itemName;
        this.imgUrl = imgUrl;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.price = price;
    }

}
