package com.likelion12th.shop.entity;

import com.likelion12th.shop.Exception.OutOfStockException;
import com.likelion12th.shop.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.ToString;

import javax.management.relation.Role;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@ToString
public class Item {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private int price;
    private int stock;
    private String itemDetail;

    @Enumerated(EnumType.STRING)
    private OrderStatus itemSellStatus;

    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;

    private String itemImg;
    private String itemImgPath;


    public void removeStock(int stock){
        int restStock = this.stock - stock;
        if (restStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고 수량:" + this.stock + ")");
        }
        this.stock = restStock;
    }

    public void addStock(int stock){
        this.stock += stock;
    }
}




