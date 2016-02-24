package com.alleywayconsulting.piggybanker.server;

import com.alleywayconsulting.piggybanker.server.service.BarcodeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PiggyBankerApplication.class)
@WebAppConfiguration
public class PiggyBankerApplicationTests {

	@Autowired
	BarcodeService barcodeService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testBarcodeGeneration() throws Exception {

		barcodeService.createSVGBarcode("123");

	}

}
