package mg.ankoay.hotelmanage.controllers;

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
import mg.ankoay.hotelmanage.bl.repositories.TableRepository;
import mg.ankoay.hotelmanage.bl.services.Product;
import mg.ankoay.hotelmanage.bl.services.ProductCategory;
import mg.ankoay.hotelmanage.bl.services.Table;

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
	private TableRepository tableRepository;

	private int count_per_page = 3;

// Statistics 
	@GetMapping("/statistics")
	public String pageStatistics(Model model) {
		model.addAttribute("curr", "stat");
		model.addAttribute("product_count", productRepository.count());
		model.addAttribute("category_count", productCategoryRepository.count());
		model.addAttribute("table_count", tableRepository.count());
		return "statistics";
	}

// TABLE CRUD
	@GetMapping("/insert-table")
	public String pageInsertTable(Model model) {
		model.addAttribute("affiliates", affiliateRepository.findAll());
		Table tbl = new Table();
		tbl.setName("Table 10");
		model.addAttribute("table", tbl);
		return "tables/insert";
	}

	@PostMapping("/tables")
	public String insertTable(@ModelAttribute Table attr) {
		tableRepository.save(attr);
		return "redirect:/back/tables";
	}

	@GetMapping("/tables")
	public String pageTables(Model model, @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		Pageable pages = PageRequest.of(page, count_per_page);
		Iterable<Table> list = tableRepository.findAll(pages);
		model.addAttribute("tables", list);
		model.addAttribute("curr", "tbl");
		setPagination(model, page, tableRepository.count());
		return "tables/list";
	}

// CATEGORY CRUD
	@GetMapping("/insert-productcategory")
	public String pageInsertProductCategory(Model model) {
		model.addAttribute("affiliates", affiliateRepository.findAll());
		ProductCategory categ = new ProductCategory();
		categ.setName("category 1");
		model.addAttribute("productcategory", categ);
		return "productcategories/insert";
	}

	@PostMapping("/productcategories")
	public String insertProductCategory(@ModelAttribute ProductCategory attr) {
		productCategoryRepository.save(attr);
		return "redirect:/back/productcategories";
	}

	@GetMapping("/productcategories")
	public String pageProductCategories(Model model,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		Pageable pages = PageRequest.of(page, count_per_page);
		Iterable<ProductCategory> list = productCategoryRepository.findAll(pages);
		model.addAttribute("productcategories", list);
		model.addAttribute("curr", "prdcateg");
		setPagination(model, page, productCategoryRepository.count());
		return "productcategories/list";
	}

// PRODUCT CRUD	

	@GetMapping("/insert-products")
	public String pageInsertProduct(Model model) {
		model.addAttribute("product_categories", productCategoryRepository.findAll());
		model.addAttribute("affiliates", affiliateRepository.findAll());
		Product prod = new Product();
		prod.setName("produit 1");
		prod.setPrice(15000.0);
		model.addAttribute("product", prod);
		return "products/insert";
	}

	@PostMapping("/products")
	public String insertProduct(@ModelAttribute Product attr) {
		productRepository.save(attr);
		return "redirect:/back/products";
	}

	@GetMapping("/products")
	public String pageProducts(Model model,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		Pageable pages = PageRequest.of(page, count_per_page);
		Iterable<Product> list = productRepository.findAll(pages);
		model.addAttribute("products", list);
		model.addAttribute("curr", "prd");
		setPagination(model, page, productRepository.count());
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
