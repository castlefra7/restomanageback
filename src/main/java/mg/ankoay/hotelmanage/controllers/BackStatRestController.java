package mg.ankoay.hotelmanage.controllers;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mg.ankoay.hotelmanage.bl.repositories.stat.StatSellingAmountRepository;
import mg.ankoay.hotelmanage.bl.repositories.stat.StatSellingCountRepository;
import mg.ankoay.hotelmanage.bl.services.stats.StatSelling;

@RestController
@RequestMapping(path = "/api/back/stat")
public class BackStatRestController {
	@Autowired
	private StatSellingAmountRepository statSellingAmountRepository;
	@Autowired
	private StatSellingCountRepository statSellingCountRepository;
	
	@GetMapping("/selling")
	public ResponseBody<StatSelling> sellings() {
		ResponseBody<StatSelling> response = new ResponseBody<>();
		StatSelling stat = new StatSelling();
		stat.setSellAmount(statSellingAmountRepository.findAll());
		stat.setSellCount(statSellingCountRepository.findAll());
		response.setData(Arrays.asList(stat));
		return response;
	}

}
