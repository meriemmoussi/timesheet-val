package tn.esprit.spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.services.IEntrepriseService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntrepriseServiceImplTest {
	@Autowired
	IEntrepriseService es;

	@Test
	public void testAjouterEntreprise() {
		Entreprise e = new Entreprise("MyEnterprise", "London");
		es.ajouterEntreprise(e);
		assertEquals(4, e.getId());
	}

	@Test
	public void testAjouterDepartement() {
		Departement d = new Departement("IT");
		es.ajouterDepartement(d);
		assertEquals(2, d.getId());
	}

	@Test
	public void testGetAllDepartementsNamesByEntreprise() {
		// List<String> listDep = es.getAllDepartementsNamesByEntreprise(1);
		List<String> listDep = es.getAllDepartementsNamesByEntreprise(2);
		assertNotNull(listDep);
	}

	@Test
	public void testDeleteEntrepriseById() {
//		es.deleteEntrepriseById(-1);
//		assertEquals(1, 1);
	}

	@Test
	public void testGetEntrepriseById() {
		Entreprise ent = es.getEntrepriseById(1);
		assertEquals("MyEnterprise", ent.getName());
	}

}
