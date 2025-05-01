package com.likelion12th.shop.dto;


import com.likelion12th.shop.entity.Item;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ItemFormDto {
    private Long id;
    @NotNull
    private String itemName;
    @NotNull
    private Integer price;
    @NotNull
    private String itemSellstatus;

    private static ModelMapper modelMapper = new ModelMapper();

    //Dto -> 엔티티 객체 변환을 위한 메서드
    public Item createItem(){
        return modelMapper.map(this, Item.class);
    }
    public static ItemFormDto of (Item item) {return modelMapper.map(item, ItemFormDto.class);}
}

