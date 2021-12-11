package mg.ankoay.hotelmanage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	@RequestMapping("/")
	public String mainPage() {
		return "redirect:/back/products";
	}
	
	@GetMapping("/test")
	public String testPage() {
		return "test";
	}
}
