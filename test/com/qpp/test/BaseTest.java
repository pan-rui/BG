package com.qpp.test;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:WebContent/WEB-INF/spring/spring-servlet.xml", "file:WebContent/WEB-INF/spring/applicationContext-*.xml" })
public class BaseTest extends AbstractJUnit4SpringContextTests {

	private static final Logger LOGGER = Logger.getLogger(BaseTest.class);
	
	@Before
	public void init(){
		LOGGER.info("================test start===================");
	}
	
	@After
	public void end(){
		LOGGER.info("================test end===================");
	}
}
