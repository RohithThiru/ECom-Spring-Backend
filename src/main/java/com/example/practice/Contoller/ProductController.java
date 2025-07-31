package com.example.practice.Contoller;

import com.example.practice.Model.Product;
import com.example.practice.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProductList(){
        return new ResponseEntity<>(productService.getProductList(),HttpStatus.OK);
    }

    @GetMapping("/product/{prodId}")
    public ResponseEntity<Product> getProduct(@PathVariable int prodId) {
        Product product = productService.getProductByID(prodId);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable int id){
        Product product = productService.getProductByID(id);
        byte[] image = product.getImage();
        return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(image);
    }

    @PostMapping(value="/product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addProduct(@RequestPart("product") Product product, @RequestPart("imageFile") MultipartFile image) {

        try {
            System.out.println("Hello World");
            Product product1 = productService.addProduct(product, image);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>("fail", HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping(value="/product/{prodId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateProduct(@PathVariable int prodId, @RequestPart("product") Product product, @RequestPart("imageFile") MultipartFile image) {
        System.out.println("Hello World");
        try{
            productService.updateProduct(prodId, product, image);
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>("fail", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/product/{id}")
    public String deleteProduct(@PathVariable int id){
        productService.deleteProduct(id);
        return "Product deleted successfully";
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> getProdyctsBySearch(@RequestParam String keyword){
        List<Product> filteredProducts = productService.getProductsBySearch(keyword);
        return new ResponseEntity<>(filteredProducts,HttpStatus.OK);
    }
}