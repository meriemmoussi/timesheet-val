package tn.esprit.spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Departement;

import tn.esprit.spring.services.IDepartementService;

import tn.esprit.spring.utils.BaseJUnit49TestCase;
@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartementTest extends BaseJUnit49TestCase{
	private static final Logger LOG = LogManager.getLogger(DepartementTest.class);


	@Autowired
	IDepartementService idepartementservice;
	
	
	@Test
	public void testAjouterDepartement() {
		Departement d = new Departement("IT");
		idepartementservice.addOrUpdateDep(d);
		assertEquals(1, d.getId());
	}

	@Test
	public void testGetAllDepartements() {
		// List<String> listDep = es.getAllDepartementsNamesByEntreprise(1);
		List<Departement> listDep = idepartementservice.getAllDepartements();
		assertNotNull(listDep);
	}

	

	@Test
	public void tesGetDepartementById() {
		Departement ent = idepartementservice.getDepartementById(2);
		assertEquals("IT", ent.getName());
	}


}
