
package com.javastack.productservice.product.repository;


import com.javastack.productservice.product.entity.ProductComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 商品评论管理repository
 */
public interface ProductCommentRepository extends JpaRepository<ProductComment, Long> {
    List<ProductComment> findByProductIdOrderByCreated(Long productId);
}
