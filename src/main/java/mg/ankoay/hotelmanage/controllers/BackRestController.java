package mg.ankoay.hotelmanage.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mg.ankoay.hotelmanage.bl.services.Product;
import mg.ankoay.hotelmanage.bl.services.ProductCategory;
import mg.ankoay.hotelmanage.bl.services.ProductCategoryRepository;
import mg.ankoay.hotelmanage.bl.services.ProductRepository;

@RestController
@RequestMapping(path = "/api/back")
public class BackRestController {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductCategoryRepository productCategoryRepository;
	private static Logger logger = LoggerFactory.getLogger(BackController.class);

	@GetMapping("/product-categories")
	public ResponseBody<ProductCategory> listProductCategories() {
		ResponseBody<ProductCategory> response = new ResponseBody<>();
		response.setData(productCategoryRepository.findAll());
		return response;
	}

	@GetMapping("/products")
	public ResponseBody<Product> listProducts() {
		ResponseBody<Product> response = new ResponseBody<>();
		response.setData(productRepository.findAll());
		return response;
	}
}
