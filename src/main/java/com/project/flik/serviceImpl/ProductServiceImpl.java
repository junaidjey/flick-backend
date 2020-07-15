package com.project.flik.serviceImpl;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.flik.dto.ChartDto;
import com.project.flik.dto.MenuDto;
import com.project.flik.dto.ProductDto;
import com.project.flik.dto.ProductDto2;
import com.project.flik.model.Company;
import com.project.flik.model.Product;
import com.project.flik.model.SubCategory;
import com.project.flik.repository.ProductRepository;
import com.project.flik.service.ProductService;
import com.project.flik.service.storage.StorageService;

@Service(value = "productService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	StorageService storageService;

	public List<ChartDto> getChart() {
		List<ChartDto> list = new ArrayList<>();
		// userDao.findAll().iterator().forEachRemaining(list::add);
		int[] a = new int[5];
		a[0] = 10;// initialization
		a[1] = 20;
		a[2] = 70;
		a[3] = 40;
		a[4] = 50;

		int[] b = new int[5];
		b[0] = 10;// initialization
		b[1] = 200;
		b[2] = 40;
		b[3] = 80;
		b[4] = 500;

		int[] c = new int[5];
		c[0] = 10;// initialization
		c[1] = 200;
		c[2] = 140;
		c[3] = 180;
		c[4] = 1200;

		int[] d = new int[5];
		d[0] = 110;// initialization
		d[1] = 0;
		d[2] = 10;
		d[3] = 80;
		d[4] = 0;

		ChartDto chartDto1 = new ChartDto();
		chartDto1.setData(a);
		chartDto1.setLabel("A");
		list.add(chartDto1);

		ChartDto chartDto2 = new ChartDto();
		chartDto2.setData(b);
		chartDto2.setLabel("B");
		list.add(chartDto2);

		ChartDto chartDto3 = new ChartDto();
		chartDto3.setData(c);
		chartDto3.setLabel("C");
		list.add(chartDto3);

		ChartDto chartDto4 = new ChartDto();
		chartDto4.setData(d);
		chartDto4.setLabel("D");
		list.add(chartDto4);

		return list;
	}

	@Override
	public List<ProductDto> getTopProduct() {
		Currency cur1 = Currency.getInstance("INR");
		List<ProductDto> productsList = new ArrayList<>();
		ProductDto productDto = new ProductDto();
		productDto.setId(1);
		productDto.setName("8866");
		productDto.setCompany("Paragone");
		productDto.setCategory("Slipper");
		productDto.setAmount(1000.0);
		productDto.setCurrency("INR");
		productDto.setCurrencySymbol(cur1.getSymbol());
		productsList.add(productDto);

		ProductDto productDto2 = new ProductDto();
		productDto2.setId(2);
		productDto2.setName("2222");
		productDto2.setCompany("VKC");
		productDto2.setCategory("Slipper");
		productDto2.setAmount(200.0);
		productDto2.setCurrency("INR");
		productDto2.setCurrencySymbol(cur1.getSymbol());
		productsList.add(productDto2);
		return productsList;
	}

	@Override
	public List<MenuDto> getMenu() {
		MenuDto dto = new MenuDto();
		dto.setPath("/dashboard");
		dto.setPath("Dashboard");
		dto.setIcon("mdi mdi-file");
		dto.setExtralink("false");
		List<MenuDto> list = new ArrayList<>();
		list.add(dto);
		return list;
	}

	@Override
	public void saveProduct(ProductDto2 productDto) {
		Product product = new Product();
		Company company = new Company();
		company.setCompanyName("Paragone");
		SubCategory subCategory = new SubCategory();
		product.setProductName(productDto.getProductName());
		product.setCompany(company);
		product.setDescription(productDto.getProductDescription());
		product.setSubCategory(subCategory);
		String directory = null;
		for (int i = 0; i < productDto.getProductImage().length; i++) {
			directory = storageService.base64ToImage(productDto.getProductImage(), productDto.getProductName(),
					company.getCompanyName());
		}
		product.setImage(directory);
		productRepository.save(product);
	}
}
