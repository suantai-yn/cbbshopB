package com.example.cbbshop.model;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductMedia {

    private Integer id;
    private Integer productId;
    private String mediaType;
    private String mediaUrl;
    private String mediaFormat;
    private Integer mediaSize;
    private String createdAt;

}
