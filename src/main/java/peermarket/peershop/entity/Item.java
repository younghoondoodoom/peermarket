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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import peermarket.peershop.entity.base.BaseTimeEntity;
import peermarket.peershop.entity.status.ItemStatus;
import peermarket.peershop.exception.NotEnoughStockException;

@Getter
@Entity
@NoArgsConstructor
public class Item extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String itemName;

    @Column(length = 2000)
    private String imgUrl;

    private String description;

    private int stockQuantity;

    private int price;

    public Item(Member member, String itemName, String imgUrl, String description,
        int stockQuantity,
        int price) {
        this.member = member;
        this.itemName = itemName;
        this.imgUrl = imgUrl;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.price = price;
    }

    @Enumerated(EnumType.STRING)
    private ItemStatus status = ItemStatus.ON_SALE;

    @OneToMany
    @JoinColumn(name = "item_id")
    private List<CategoryItem> categoryItems = new ArrayList<>();

    /**
     * m2m 카테고리 추가
     */
    public void addCategoryItem(CategoryItem... categoryItems) {
        Collections.addAll(this.categoryItems, categoryItems);
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

    public void updateItemInfo(String itemName, String imgUrl, String description,
        int stockQuantity, int price) {
        this.itemName = itemName;
        this.imgUrl = imgUrl;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.price = price;
    }

}
