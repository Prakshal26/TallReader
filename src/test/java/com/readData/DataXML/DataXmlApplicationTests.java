package com.readData.DataXML;

import com.readData.DataXML.models.Ledger;
import com.readData.DataXML.repositories.LedgerRepository;
import com.readData.DataXML.services.LedgerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class DataXmlApplicationTests {

	@Autowired
	private LedgerService ledgerService;

	@MockBean
	private LedgerRepository ledgerRepository;

	@Test
	public void  findByName() {
		Ledger l1 = new Ledger();
		l1.setGUID("123");
		l1.setName("Prakshal");


		/*It will not call actual ledger Repository, we are just mocking it and saying that whenever in
		the code execution we get ledgerRepository we will mock it and it should return l1.

		 */
		when(ledgerRepository.findByName("123")).thenReturn(l1);
		//assertEquals(expected,actual);
		assertEquals(l1.getName(),ledgerService.findByName("123").getName());
	}

	@Test
	void contextLoads() {
	}

}
