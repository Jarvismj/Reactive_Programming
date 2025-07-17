package com.mjBackend.reactive.service;


import com.mjBackend.reactive.controller.ProductController;
import com.mjBackend.reactive.dto.ProductDto;
import com.mjBackend.reactive.repository.ProductRepository;
import com.mjBackend.reactive.utils.AppUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import com.mjBackend.reactive.entity.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    Logger log = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository repository;

    public Flux<ProductDto> getProducts() {

        return repository.findAll().map(AppUtils::entityToDto);
    }

    public Mono<ProductDto> getProductById(String id) {
        return repository.findById(id).map(AppUtils::entityToDto);
    }

    public Flux<ProductDto> getProductInRange(double min, double max) {
        return repository.findByPriceBetween(Range.closed(min, max));
    }

    public Mono<ProductDto> saveProduct(Mono<ProductDto> productDtoMono) {
        log.info("Service Method For Saving....");
        return productDtoMono.map(AppUtils::DtoToEntity).flatMap(repository::insert).map(AppUtils::entityToDto);
    }

    public Mono<ProductDto> updateProduct(Mono<ProductDto> productDtoMono, String id) {
     return   repository.findById(id).flatMap(p -> productDtoMono.map(AppUtils::DtoToEntity)).doOnNext(e -> e.setId(id)).
                flatMap(repository::save).map(AppUtils::entityToDto);
    }

    public Mono<Void> deleteProduct(String id)
    {
        return repository.deleteById(id);
    }
}
