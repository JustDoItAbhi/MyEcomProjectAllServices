package com.ecom.productservice.repository;

import com.ecom.productservice.entity.Categoryes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRespository extends JpaRepository<Categoryes,Long> {
    Optional<Categoryes> findByCategoryName(String name);
}
