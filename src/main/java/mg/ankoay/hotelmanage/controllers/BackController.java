package mg.ankoay.hotelmanage.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mg.ankoay.hotelmanage.bl.repositories.AffiliateRepository;
import mg.ankoay.hotelmanage.bl.repositories.ProductCategoryRepository;
import mg.ankoay.hotelmanage.bl.repositories.ProductRepository;
import mg.ankoay.hotelmanage.bl.repositories.TablePlaceRepository;
import mg.ankoay.hotelmanage.bl.services.Product;
import mg.ankoay.hotelmanage.bl.services.ProductCategory;
import mg.ankoay.hotelmanage.bl.services.TablePlace;

@Controller
@RequestMapping(path = "/back")
public class BackController {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductCategoryRepository productCategoryRepository;
	@Autowired
	private AffiliateRepository affiliateRepository;
	@Autowired
	private TablePlaceRepository tablePlaceRepository;

	private int count_per_page = 3;

	private static Logger logger = LoggerFactory.getLogger(BackController.class);
// Statistics 
	@GetMapping("/statistics")
	public String pageStatistics(Model model) {
		model.addAttribute("curr", "stat");
		//model.addAttribute("product_count", productRepository.count());
		//model.addAttribute("category_count", productCategoryRepository.count());
		//model.addAttribute("table_count", tablePlaceRepository.count());
		
		SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
		
		model.addAttribute("today", sdt.format(new Date()));
		return "statistics";
	}

// TABLE CRUD
	@GetMapping("/insert-table")
	public String pageInsertTable(Model model) {
		model.addAttribute("affiliates", affiliateRepository.findAll());
		TablePlace tbl = new TablePlace();
		tbl.setName("Table 10");
		model.addAttribute("table", tbl);
		return "tables/insert";
	}

	@PostMapping("/tables")
	public String insertTable(@ModelAttribute TablePlace attr) {
		tablePlaceRepository.save(attr);
		return "redirect:/back/tables";
	}

	@GetMapping("/tables")
	public String pageTables(Model model, @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		Pageable pages = PageRequest.of(page, count_per_page);
		Iterable<TablePlace> list = tablePlaceRepository.findAll(pages);
		model.addAttribute("tables", list);
		model.addAttribute("curr", "tbl");
		setPagination(model, page, tablePlaceRepository.count());
		return "tables/list";
	}

// CATEGORY CRUD
	@PostMapping("/update-productcategories")
	public String updateProductCategory(@ModelAttribute ProductCategory attr) {
		Optional<ProductCategory> foundCateg = productCategoryRepository.findById(attr.getId());
		// TODO: Maybe update the enterprise too
		ProductCategory categToSave = foundCateg.get();

		categToSave.setName(attr.getName());
		productCategoryRepository.save(categToSave);
		return "redirect:/back/productcategories";
	}
	
	@GetMapping("/update-productcategories")
	public String pageUpdateProductCategory(Model model, @RequestParam(name="id", required=true) int id) {
		model.addAttribute("affiliates", affiliateRepository.findAll());
		Optional<ProductCategory> ct = productCategoryRepository.findById(id);
		ProductCategory categ = ct.get();
		model.addAttribute("productcategory", categ);
		model.addAttribute("isupdate", true);
		return "productcategories/form";
	}
	
	@GetMapping("/delete-productcategories")
	public String deleteProductCategory(@RequestParam(name="id", required=true) int id) {
		productCategoryRepository.deleteById(id);
		return "redirect:/back/productcategories";
	}
	
	@GetMapping("/insert-productcategory")
	public String pageInsertProductCategory(Model model) {
		model.addAttribute("affiliates", affiliateRepository.findAll());
		ProductCategory categ = new ProductCategory();
		categ.setName("category 1");
		model.addAttribute("productcategory", categ);
		return "productcategories/form";
	}

	@PostMapping("/productcategories")
	public String insertProductCategory(@ModelAttribute ProductCategory attr) {
		productCategoryRepository.save(attr);
		return "redirect:/back/productcategories";
	}

	@GetMapping("/productcategories")
	public String pageProductCategories(Model model,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page, 
			@RequestParam(name="name", required=false, defaultValue="") String name) {
		Pageable pages = PageRequest.of(page, count_per_page);
		
		String _name = String.format("%%%s%%", name);
		
		Iterable<ProductCategory> list = productCategoryRepository.findByNameLike(_name, pages);
		
		model.addAttribute("productcategories", list);
		model.addAttribute("curr", "prdcateg");
		model.addAttribute("name", name);
		
		setPagination(model, page, productCategoryRepository.countByNameLike(_name));
		
		return "productcategories/list";
	}

// PRODUCT CRUD
	@GetMapping("/delete-products")
	public String deleteProduct(@RequestParam(name="id", required=true) int id) {
		productRepository.deleteById(id);
		return "redirect:/back/products";
	}
	
	@PostMapping("/update-products")
	public String updateProduct(@ModelAttribute Product attr) {
		Optional<Product> foundPrd = productRepository.findById(attr.getId());
		// TODO: Maybe update the enterprise too
		Product prdToSave = foundPrd.get();
		ProductCategory category = new ProductCategory();
		category.setId(attr.getCategory().getId());
		prdToSave.setCategory(category);
		prdToSave.setName(attr.getName());
		prdToSave.setPrice(attr.getPrice());
		
		productRepository.save(prdToSave);
		return "redirect:/back/products";
	}
	
	@GetMapping("/update-products")
	public String pageUpdateProduct(Model model, @RequestParam(name="id", required=true) int id) {
		model.addAttribute("product_categories", productCategoryRepository.findAll());
		model.addAttribute("affiliates", affiliateRepository.findAll());
		Optional<Product> prd = productRepository.findById(id);
		//TODO: How to handle this exception properly (maybe create global exception handler) NoSuchElementException
		Product prod = prd.get();
		model.addAttribute("product", prod);
		model.addAttribute("isupdate", true);
		return "products/form";
	}

	@GetMapping("/insert-products")
	public String pageInsertProduct(Model model) {
		model.addAttribute("product_categories", productCategoryRepository.findAll());
		model.addAttribute("affiliates", affiliateRepository.findAll());
		Product prod = new Product();
		prod.setName("produit 1");
		prod.setPrice(15000.0);
		model.addAttribute("product", prod);
		return "products/form";
	}

	@PostMapping("/products")
	public String insertProduct(@ModelAttribute Product attr) {
		productRepository.save(attr);
		return "redirect:/back/products";
	}

	@GetMapping("/products")
	public String pageProducts(Model model,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page, 
			@RequestParam(name="name", required=false, defaultValue="") String name) {
		Pageable pages = PageRequest.of(page, count_per_page);
		
		String _name = String.format("%%%s%%", name);
		
		Iterable<Product> list = productRepository.findByNameLike(_name, pages);
		
		model.addAttribute("products", list);
		model.addAttribute("curr", "prd");
		model.addAttribute("name", name);
		
		setPagination(model, page, productRepository.countByNameLike(_name));
		return "products/list";
	}

	private void setPagination(Model model, int currPage, long count) {
		int numbPages = (int) (Math.ceil((double) (count) / (double) count_per_page));
		model.addAttribute("numberpages", numbPages);
		model.addAttribute("page", currPage);
		if (currPage > 0) {
			model.addAttribute("prev", currPage - 1);
		} else {
			model.addAttribute("prev", 0);
		}
		if (currPage < numbPages - 1) {
			model.addAttribute("next", currPage + 1);
		} else {
			model.addAttribute("next", numbPages - 1);
		}
	}
}
