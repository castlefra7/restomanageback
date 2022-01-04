package mg.ankoay.hotelmanage.controllers;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mg.ankoay.hotelmanage.bl.repositories.stat.StatSellingAmountRepository;
import mg.ankoay.hotelmanage.bl.repositories.stat.StatSellingCountRepository;
import mg.ankoay.hotelmanage.bl.services.stats.StatSelling;

@RestController
@RequestMapping(path = "/stat")
public class BackStatRestController {
	@Autowired
	private StatSellingAmountRepository statSellingAmountRepository;
	@Autowired
	private StatSellingCountRepository statSellingCountRepository;

	private static Logger logger = LoggerFactory.getLogger(BackStatRestController.class);

	@GetMapping("/selling")
	public ResponseBody<StatSelling> sellings(
			@RequestParam(name = "date", required = false, defaultValue = "") String date) {
		ResponseBody<StatSelling> response = new ResponseBody<>();
		try {
			StatSelling stat = new StatSelling();

			SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");

			Date dt = new Date(sdt.parse(date).getTime());

			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			Integer month = cal.get(Calendar.MONTH) + 1;

			Pageable pageable = PageRequest.of(0, 10);

			stat.setSellAmount(statSellingAmountRepository.sellingAmountByProd(month, pageable).getContent());
			// stat.setSellCount(statSellingCountRepository.findAll());

			stat.setSellAmountByHour(statSellingAmountRepository.sellingAmountByHour(dt));
			stat.setSumSellingAmount(statSellingAmountRepository.sumSellingAmount(dt));
			stat.setSellingCount(statSellingAmountRepository.sellingCount(dt));
			stat.setAvgSellingAmount(statSellingAmountRepository.avgSellingAmount(dt));

			// TODO: Pass the Year as parameter too
			stat.setSellingAmountProgress(statSellingAmountRepository.sellingAmountProgress(month));

			response.setData(Arrays.asList(stat));
		} catch (Exception ex) {
			response.getStatus().setCode(500);
			response.getStatus().setMessage(ex.getMessage());
		}

		return response;
	}

}
