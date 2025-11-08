package com.example.productservice_oct25.projections;

public interface ProductWithTitleAndPrice {
    String getTitle();
    Double getPrice();
}


/*
We created this interface as a projection to fetch only specific fields (title and price)
from the Product entity.This helps in improving performance by avoiding fetching the
entire product object from the database.Only those two getters are declared because
we only need those two columns in the query result.*/
