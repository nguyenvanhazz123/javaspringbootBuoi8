package com.example.buoi8.services;

import com.example.buoi8.entity.Product;
import com.example.buoi8.respository.IProductRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private IProductRespository productRepository;
    public List<Product> GetAll() {
        return productRepository.findAll();
    }

    public void add(Product product){
        productRepository.save(product);
    }

    public Product get(Integer id){
        return productRepository.findById(id).orElse(null);
    }
    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }
    public void delete(Product deleteProduct){
        productRepository.delete(deleteProduct);
    }
    public void edit(Product editedProduct) {
        Product find = getProductById(editedProduct.getId());

        find.setName(editedProduct.getName());
        find.setPrice(editedProduct.getPrice());
        find.setCategory(editedProduct.getCategory());

        if (editedProduct.getImage() != null) {
            find.setImage(editedProduct.getImage());
        }

        productRepository.save(find);
    }
}
