package com.pratice_project;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
	@Disabled("Skipping contextLoads on CI/CD to avoid MySQL connection")
class PraticeProjectApplicationTests {

	@Test
	void contextLoads() {
	}

}
