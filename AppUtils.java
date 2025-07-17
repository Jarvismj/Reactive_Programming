package com.mjBackend.reactive.utils;

import com.mjBackend.reactive.dto.ProductDto;
import com.mjBackend.reactive.entity.Product;
import org.springframework.beans.BeanUtils;

public class AppUtils {

    public static ProductDto entityToDto(Product product)
    {
          ProductDto productDto = new ProductDto();
          BeanUtils.copyProperties(product,productDto);
          return productDto;
    }

    public static Product DtoToEntity(ProductDto productDto)
    {
        Product product = new Product();
        BeanUtils.copyProperties(productDto,product);
        return product;
    }
}
