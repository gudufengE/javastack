
package com.javastack.productservice.product.repository;


import com.javastack.productservice.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 产品管理repository
 *
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

}
