package com.example.productservice_oct25.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductServiceDTOs {
    private long id;           // ✅ added id field
    private String title;
    private double price;
    private String description;
    private String category;
    private String imageUrl;   // ✅ renamed to match service usage
}
