package mg.ankoay.hotelmanage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HotelmanageApplication {
	private static Logger logger = LoggerFactory.getLogger(HotelmanageApplication.class);

	public static void main(String[] args) {
		logger.info("Hotel management is up and running");
		SpringApplication.run(HotelmanageApplication.class, args);
	}

}
