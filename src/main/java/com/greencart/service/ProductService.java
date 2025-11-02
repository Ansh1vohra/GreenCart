package com.greencart.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.greencart.model.Product;
import com.greencart.model.Seller;
import com.greencart.repository.ProductRepository;
import com.greencart.repository.SellerRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private Cloudinary cloudinary;

    public Product addProduct(Product product, MultipartFile imageFile, Long sellerId) throws IOException {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        // ✅ Upload image to Cloudinary
        Map uploadResult = cloudinary.uploader().upload(imageFile.getBytes(),
                ObjectUtils.asMap("folder", "greencart/products"));

        String imageUrl = (String) uploadResult.get("secure_url");
        product.setImageUrl(imageUrl);
        product.setSeller(seller);

        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
