package com.jwt.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductApi {

    private ProductRepository repository;

    @GetMapping
    @RolesAllowed({"ROLE_CUSTOMER","ROLE_EDITOR"})
    public List<Product> listAllProduct(){
        return repository.findAll();
    }

    @PostMapping
    @RolesAllowed("ROLE_EDITOR")
    public ResponseEntity<Product> addProduct(@RequestBody @Valid Product product)
    {
        Product savedProduct=repository.save(product);
        URI uri= URI.create("/products/"+savedProduct.getId());
        return  ResponseEntity.created(uri).body(savedProduct);
    }
}
