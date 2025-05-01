package com.likelion12th.shop.repository;

import com.likelion12th.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    // 특정 가격보다 저렴한 상품 내림차순 정렬
    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);
}