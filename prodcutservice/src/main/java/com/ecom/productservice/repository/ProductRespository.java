package com.ecom.productservice.repository;

import com.ecom.productservice.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRespository extends JpaRepository<Products,Long> {// PRODUCT REPOSITORY
    Optional<Products> findById( long id);

//    List<Products> findNameKeyword(String keyword);
    @Query("SELECT e FROM Products e WHERE e.name LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Products>searchByKeyword(@Param("keyword") String keyword);

}
