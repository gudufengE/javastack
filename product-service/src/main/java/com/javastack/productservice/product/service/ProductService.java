package com.javastack.productservice.product.service;

import com.javastack.productservice.product.entity.Product;
import com.javastack.productservice.product.entity.ProductComment;
import com.javastack.productservice.product.repository.ProductCommentRepository;
import com.javastack.productservice.product.repository.ProductRepository;
import com.javastack.publiccomponent.utils.DateUtils;
import com.javastack.publiccomponent.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCommentRepository productCommentRepository;

    private static List<Product> products = new ArrayList<>();
    private static List<ProductComment> productComments = new ArrayList<>();

    static {
        products = new ArrayList<Product>();
        int commentsNumPerProduct = 3; //每个产品的评论数
        for (int i = 0; i < 15; i++) {
            String id = IdUtils.id();
            Product u = new Product("name_" + id, "/products/cover/00" + i + ".png", 100 + i);
            long productid = i;
            u.setId(Long.parseLong(productid + ""));

            //构造用户评论数据
            if ((i >= 0) && (i < 15)) {  //用户id，即作者id
                long authorId = productid;
                if (productComments.isEmpty()) {
                    for (int j = 0; j < commentsNumPerProduct; j++) {
                        ProductComment productComment = new ProductComment(productid, authorId, "非常不错的商品" + j, DateUtils.getNowDate());
                        productComment.setId(Long.parseLong(j + ""));
                        productComments.add(productComment);
                    }
                } else {
                    long maxProductCommentId = (productComments.get(productComments.size() - 1).getId() + 1);
                    for (long j = maxProductCommentId; j < maxProductCommentId + commentsNumPerProduct; j++) {
                        ProductComment productComment = new ProductComment(productid, authorId, "非常不错的商品" + j, DateUtils.getNowDate());
                        productComment.setId(Long.parseLong(j + ""));
                        productComments.add(productComment);
                    }
                }
            }

            products.add(u);
        }
    }


    public Product getProduct(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        for (Product product : products) {
            if (product.getId().toString().equals(id)) {
                return product;
            }
        }

        return null;
    }

    public List<ProductComment> findByProductIdOrderByCreated(Long productid){
        List<ProductComment> results = new ArrayList<>();
        for (Product product : products){
            if (product.getId().longValue() == productid.longValue()){
                for (ProductComment productComment : productComments){
                    if (productComment.getProductId() == product.getId()) {
                        results.add(productComment);
                    }
                }
                break;
            }
        }

        return results;
    }


    public List<Product> list() {
        return products;
        //        return this.productRepository.findAll(); //TODO
    }
}
