package mg.ankoay.hotelmanage.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mg.ankoay.hotelmanage.bl.services.Product;
import mg.ankoay.hotelmanage.bl.services.ProductRepository;

@Controller
@RequestMapping(path = "/back")
public class BackController {
	@Autowired
	private ProductRepository productRepository;
	private static Logger logger = LoggerFactory.getLogger(BackController.class);
	private int count_per_page = 1;
	
	@PostMapping("/products")
	public String insertProduct() {
		
		return "redirect:/back/products";
	}

	@GetMapping("/products")
	public String pageProducts(Model model,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		Pageable pages = PageRequest.of(page, count_per_page);
		Iterable<Product> products = productRepository.findAll(pages);
		int numbPages = (int) (Math.floor((double) (productRepository.count()) / (double) count_per_page));
		model.addAttribute("products", products);
		model.addAttribute("numberpages", numbPages);
		setPagination(model, page, numbPages);
		return "products";
	}

	private void setPagination(Model model, int currPage, int numbPages) {
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
