package com.example.buoi8.controller;

import com.example.buoi8.entity.Product;
import com.example.buoi8.services.CategoryService;
import com.example.buoi8.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestMapping("products")
@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;
    @GetMapping("")
    public String index(Model model)
    {
        model.addAttribute("listproduct", productService.GetAll());
        return "products/index.html";
    }
    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword , Model model){
        List<Product> find = new ArrayList<>();
        if(keyword != ""){
            find =  productService.GetAll().stream().filter(p->p.getName().toLowerCase().contains(keyword)).toList();
        }else{
            find = productService.GetAll();
        }
        model.addAttribute("listproduct", find);
        return "products/index";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model)
    {
        model.addAttribute("product", productService.get(id));
        model.addAttribute("listCategory", categoryService.getAllCategories() );
        return "products/edit";
    }
    @PostMapping("/edit")
    public String edit(@Valid Product editProduct,
                       @RequestParam MultipartFile imageProduct,
                       BindingResult result,
                       Model model)
    {
        if (result.hasErrors()) {
            model.addAttribute("product", editProduct);
            model.addAttribute("listCategory", categoryService.getAllCategories() );
            return "admin/products/edit";
        }
        if (imageProduct != null && imageProduct.getSize() > 0) {
            try {
                File saveFile = new ClassPathResource("static/images").getFile();
                String newImageFile = UUID.randomUUID() + ".png";
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + newImageFile);
                Files.copy(imageProduct.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                String imagePath =  newImageFile;
                editProduct.setImage(imagePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        productService.edit(editProduct);
        return "redirect:/products";
    }

}
