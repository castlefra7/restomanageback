package mg.ankoay.hotelmanage.controllers;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mg.ankoay.hotelmanage.bl.repositories.OpenCashierRepository;
import mg.ankoay.hotelmanage.bl.repositories.OrderDetailRepository;
import mg.ankoay.hotelmanage.bl.repositories.OrderRepository;
import mg.ankoay.hotelmanage.bl.repositories.ProductCategoryRepository;
import mg.ankoay.hotelmanage.bl.repositories.ProductRepository;
import mg.ankoay.hotelmanage.bl.repositories.TablePlaceRepository;
import mg.ankoay.hotelmanage.bl.repositories.stat.StatSellingAmountRepository;
import mg.ankoay.hotelmanage.bl.services.OpenCashier;
import mg.ankoay.hotelmanage.bl.services.Order;
import mg.ankoay.hotelmanage.bl.services.Product;
import mg.ankoay.hotelmanage.bl.services.ProductCategory;
import mg.ankoay.hotelmanage.bl.services.TablePlace;
import mg.ankoay.hotelmanage.bl.services.User;
import mg.ankoay.hotelmanage.bl.services.stats.StatSelling;

@RestController
@RequestMapping(path = "/api/back")
public class BackRestController {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductCategoryRepository productCategoryRepository;
	@Autowired
	private TablePlaceRepository tablePlaceRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OpenCashierRepository openCashierRepository;
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	@Autowired
	private StatSellingAmountRepository statSellingAmountRepository;

	private static Logger logger = LoggerFactory.getLogger(BackRestController.class);

	@GetMapping("/stat/selling-sum")
	public ResponseBody<StatSelling> sellingSum(
			@RequestParam(name = "date", required = false, defaultValue = "") String date) {
		ResponseBody<StatSelling> response = new ResponseBody<>();
		try {
			StatSelling stat = new StatSelling();
			SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
			java.sql.Date dt = new java.sql.Date(sdt.parse(date).getTime());

			// TODO: Maybe pass the id_point_of_sale in the parameter too
			stat.setSumSellingAmount(statSellingAmountRepository.sumSellingAmount(dt));
			response.setData(Arrays.asList(stat));
		} catch (Exception ex) {
			response.getStatus().setCode(500);
			response.getStatus().setMessage(ex.getMessage());
			ex.printStackTrace();
		}

		return response;
	}

	@PutMapping("/orders")
	public ResponseBody<Object> updateOrder(@RequestBody Order attr) {
		ResponseBody<Object> response = new ResponseBody<>();
		try {
			attr.update(orderRepository);
		} catch (Exception ex) {
			response.getStatus().setCode(500);
			response.getStatus().setMessage(ex.getMessage());
			ex.printStackTrace();
		}
		return response;
	}

	@GetMapping("/orders/unpaid")
	public ResponseBody<Order> allUnpaidOrders(@RequestParam(name = "date", required = true) String date) {
		ResponseBody<Order> response = new ResponseBody<>();
		try {
			SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
			java.sql.Date dt = new java.sql.Date(sdt.parse(date).getTime());

			response.setData(orderRepository.findAllUnpaidOrders(dt));
		} catch (Exception ex) {
			response.getStatus().setCode(500);
			response.getStatus().setMessage(ex.getMessage());
			ex.printStackTrace();
		}

		return response;
	}

	@GetMapping("/orders/paid")
	public ResponseBody<Order> allPaidOrders(@RequestParam(name = "date", required = true) String date) {
		ResponseBody<Order> response = new ResponseBody<>();
		try {
			SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
			java.sql.Date dt = new java.sql.Date(sdt.parse(date).getTime());

			response.setData(orderRepository.findAllPaidOrders(dt));
		} catch (Exception ex) {
			response.getStatus().setCode(500);
			response.getStatus().setMessage(ex.getMessage());
			ex.printStackTrace();
		}

		return response;
	}

	@PostMapping("/open-cashier")
	public ResponseBody<Object> insertOpenCashier(@RequestBody OpenCashier attr) {
		ResponseBody<Object> response = new ResponseBody<>();
		attr.setDate_open(new Timestamp(new Date().getTime()));
		openCashierRepository.save(attr);
		return response;
	}

	@PutMapping("/orders/pay")
	public ResponseBody<Object> payOrder(@RequestBody Order attr) {
		ResponseBody<Object> response = new ResponseBody<>();
		try {
			attr.pay(orderRepository);
		} catch (Exception ex) {
			response.getStatus().setCode(500);
			response.getStatus().setMessage(ex.getMessage());
			ex.printStackTrace();
		}
		return response;
	}

	@PostMapping("/orders-pay")
	public ResponseBody<Object> insertPayOrder(@RequestBody Order attr) {
		ResponseBody<Object> response = new ResponseBody<>();
		attr.setDate_order(new Timestamp(new Date().getTime()));
		attr.setDate_payment(new Timestamp(new Date().getTime()));

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();

		attr.setId_user(user.getId());

		orderRepository.save(attr);

		return response;
	}

	@PostMapping("/orders")
	public ResponseBody<Object> insertOrder(@RequestBody Order attr) {
		ResponseBody<Object> response = new ResponseBody<>();
		attr.setDate_order(new Timestamp(new Date().getTime()));

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();

		attr.setId_user(user.getId());

		orderRepository.save(attr);
		return response;
	}

	@GetMapping("/orders")
	public ResponseBody<Order> allOrders() {
		ResponseBody<Order> response = new ResponseBody<>();
		response.setData(orderRepository.findAll());
		return response;
	}

	@GetMapping("/tables")
	public ResponseBody<TablePlace> allTables() {
		ResponseBody<TablePlace> response = new ResponseBody<>();
		response.setData(tablePlaceRepository.findAll());
		return response;
	}

	@GetMapping("/product-categories")
	public ResponseBody<ProductCategory> allProductCategories() {
		ResponseBody<ProductCategory> response = new ResponseBody<>();
		response.setData(productCategoryRepository.findAll());
		return response;
	}

	@GetMapping("/products")
	public ResponseBody<Product> allProducts() {
		ResponseBody<Product> response = new ResponseBody<>();
		response.setData(productRepository.findAll());
		return response;
	}
}
