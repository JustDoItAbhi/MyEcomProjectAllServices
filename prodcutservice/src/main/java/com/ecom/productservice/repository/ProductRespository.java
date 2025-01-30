package com.ecom.productservice.repository;

import com.ecom.productservice.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRespository extends JpaRepository<Products,Long> {// PRODUCT REPOSITORY
}
