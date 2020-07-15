package com.project.flik.service;

import java.util.List;

import com.project.flik.dto.ChartDto;
import com.project.flik.dto.MenuDto;
import com.project.flik.dto.ProductDto;
import com.project.flik.dto.ProductDto2;

public interface ProductService{

	List<ChartDto> getChart();

	List<ProductDto> getTopProduct();

	List<MenuDto> getMenu();

	void saveProduct(ProductDto2 product);

}
