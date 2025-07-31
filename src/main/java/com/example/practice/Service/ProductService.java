package com.example.practice.Service;

import com.example.practice.Model.Product;
import com.example.practice.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    public List<Product> getProductList() {
        return productRepo.findAll();
    }

    public Product getProductByID(int prodId) {
        return productRepo.findById(prodId).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile image) {
        product.setImageName(image.getOriginalFilename());
        product.setImageType(image.getContentType());
        try {
            product.setImage(image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productRepo.save(product);
    }

    public void updateProduct(int prodId, Product product, MultipartFile image) {
        try {
            product.setImage(image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        product.setImageName(image.getOriginalFilename());
        product.setImageType(image.getContentType());
        productRepo.save(product);
    }

    public List<Product> getProductsBySearch(String keyword) {
        return productRepo.getProductsBySearch(keyword);
    }

    public void deleteProduct(int id) {
        productRepo.deleteById(id);
    }

}
