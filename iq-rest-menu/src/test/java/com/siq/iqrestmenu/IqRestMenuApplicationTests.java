package com.siq.iqrestmenu;

import com.siq.iqrestmenu.service.RemoteDataImporterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IqRestMenuApplicationTests {

	@Autowired
    RemoteDataImporterService testRC;

	@Autowired
	ApplicationContext applicationContext;

	@Test
	public void contextLoads() {
		assertThat(testRC).isNotNull();
	}

}
