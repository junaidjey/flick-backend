package com.project.flik.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.flik.dto.ChartDto;
import com.project.flik.dto.MenuDto;
import com.project.flik.dto.ProductDto;
import com.project.flik.dto.ProductDto2;
import com.project.flik.service.ProductService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private ProductService productService;
	@PostMapping("/getChart")
    public List<ChartDto> chart(){
    	System.out.println(productService.getChart().toString());
    	 return productService.getChart();
    }
	@PostMapping("/topSellingProducts")
    public List<ProductDto> topSellingProducts(){
    	System.out.println(productService.getTopProduct().toString());
    	 return productService.getTopProduct();
    }
	
	@PostMapping("/menu")
    public List<MenuDto> menu(){
		System.out.println("gg");
    	 return productService.getMenu();
    }
	
	@PostMapping("/products")
    public List<MenuDto> products(){
    	 return productService.getMenu();
    }
//	
//	@PostMapping("/addProduct")
//    public void addProduct(@Valid @RequestBody ProductDto2 product ){
//    	 System.out.println("Product"+product.toString());
//    	 productService.saveProduct(product);
//    }
	
	@PostMapping("/addProduct")
	@ResponseBody
    public void addProduct(ProductDto2 product){
     	System.out.println("Product"+product);
    	productService.saveProduct(product);
    }
}
