package mg.ankoay.hotelmanage.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mg.ankoay.hotelmanage.bl.repositories.stat.StatSellingAmountRepository;
import mg.ankoay.hotelmanage.bl.services.stats.StatSellingAmount;

@Controller
public class MainController {

	private static Logger logger = LoggerFactory.getLogger(MainController.class);
	

	@GetMapping("/login")
	public String loginPage(@RequestParam(required=false, defaultValue="") String error, Model model) {
		model.addAttribute("error", error);
		return "login";
	}

	@RequestMapping("/")
	public String mainPage() {
		return "redirect:/back/products";
	}

	@GetMapping("/test")
	public String testPage() {
		logger.info("Test EndPoint");
		return "test";
	}
}
