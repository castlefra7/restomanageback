package mg.ankoay.hotelmanage.controllers;

import java.sql.Timestamp;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mg.ankoay.hotelmanage.bl.repositories.OpenCashierRepository;
import mg.ankoay.hotelmanage.bl.repositories.OrderRepository;
import mg.ankoay.hotelmanage.bl.repositories.ProductCategoryRepository;
import mg.ankoay.hotelmanage.bl.repositories.ProductRepository;
import mg.ankoay.hotelmanage.bl.repositories.TableRepository;
import mg.ankoay.hotelmanage.bl.services.OpenCashier;
import mg.ankoay.hotelmanage.bl.services.Order;
import mg.ankoay.hotelmanage.bl.services.Product;
import mg.ankoay.hotelmanage.bl.services.ProductCategory;
import mg.ankoay.hotelmanage.bl.services.Table;

@RestController
@RequestMapping(path = "/api/back")
public class BackRestController {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductCategoryRepository productCategoryRepository;
	@Autowired
	private TableRepository tableRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OpenCashierRepository openCashierRepository;
	
	private static Logger logger = LoggerFactory.getLogger(BackRestController.class);
	
	@PostMapping("/open-cashier")
	public ResponseBody<Object> insertOpenCashier(@RequestBody OpenCashier attr) {
		ResponseBody<Object> response = new ResponseBody<>();
		attr.setDate_open(new Timestamp(new Date().getTime()));
		openCashierRepository.save(attr);
		return response;
	}
	
	@PostMapping("/orders")
	public ResponseBody<Object> insertOrder(@RequestBody Order attr) {
		ResponseBody<Object> response =new ResponseBody<>();
		attr.setDate_order(new Timestamp(new Date().getTime()));
		orderRepository.save(attr);
		return response;
	}

	@GetMapping("/orders")
	public ResponseBody<Order> listOrders() {
		ResponseBody<Order> response = new ResponseBody<>();
		response.setData(orderRepository.findAll());
		return response;
	}

	@GetMapping("/tables")
	public ResponseBody<Table> listTables() {
		ResponseBody<Table> response = new ResponseBody<>();
		response.setData(tableRepository.findAll());
		return response;
	}

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
