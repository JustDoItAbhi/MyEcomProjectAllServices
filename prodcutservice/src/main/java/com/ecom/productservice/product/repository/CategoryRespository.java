package com.ecom.productservice.product.repository;

import com.ecom.productservice.entity.Categoryes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRespository extends JpaRepository<Categoryes,Long> {
}
