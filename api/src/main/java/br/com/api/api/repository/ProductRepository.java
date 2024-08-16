package br.com.api.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.api.api.model.product.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
    
}
