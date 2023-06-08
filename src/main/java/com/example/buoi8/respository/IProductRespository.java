package com.example.buoi8.respository;

import com.example.buoi8.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRespository extends JpaRepository<Product, Integer> {
}
