package tn.esprit.spring;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;
import tn.esprit.spring.utils.BaseJUnit49TestCase;

public class EmployeTests extends BaseJUnit49TestCase {
	private static final Logger LOG = LogManager.getLogger(EmployeTests.class);

	@Autowired
	IEmployeService iempServ;
	@Autowired
	IEntrepriseService ientSer;
	@Autowired

	EmployeRepository empRepoistory;
	@Autowired
	ContratRepository contratRepoistory;
	private Employe employe;
	private Departement departement;
	private Contrat contrat;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		this.contrat = new Contrat();
		this.contrat.setDateDebut(new Date(12 / 10 / 2021));
		this.contrat.setTypeContrat("CDI");
		this.contrat.setSalaire(1000);
		this.contrat.setTelephone(24318910);

		this.contrat.setReference(iempServ.ajouterContrat(this.contrat));

		this.employe = new Employe();
		this.employe.setPrenom("Oumayma");
		this.employe.setNom("Ferjani");
		this.employe.setEmail("Oumayma.Ferjani@esprit.tn");
		this.employe.setPassword(getIdHelper().createRandomString(5));
		this.employe.setActif(true);
		this.employe.setRole(Role.INGENIEUR);
		this.employe.setContrat(this.contrat);
		this.departement = new Departement();
		this.departement.setName(getIdHelper().createRandomString(5));
		this.employe.setId(iempServ.addOrUpdateEmploye(this.employe));

	}

	@Test
	public void tests() {
		ajouterContrat();
		addEmploye();
		affecterContratAEmploye();
		deleteEmployeById();
		testGetAllEmployee();
	}

	public void addEmploye() {
		LOG.info("Add Employee");
		LOG.info("***********" + this.contrat);
		LOG.info("****************" + this.employe.getId());
		iempServ.addEmploye(this.employe);

		LOG.info("End Add Employee");
	}

	public void ajouterContrat() {
		LOG.info("Start Method ajouterContrat");
		LOG.info(this.contrat);
		this.contrat.setEmploye(this.employe);

		this.contrat.setReference(iempServ.ajouterContrat(this.contrat));
		Assert.assertTrue(contrat.getReference() > 0);
		LOG.info("End Method ajouterContrat");

	}

	public void affecterContratAEmploye() {

		LOG.info("Start Method affecterContratAEmploye");
		LOG.info(this.contrat);
		LOG.info(this.employe);

		iempServ.affecterContratAEmploye(this.contrat.getReference(), this.employe.getId());
		this.employe = iempServ.getAllEmployes().stream().filter(x -> x.getId() == this.employe.getId()).findFirst()
				.get();
		LOG.info(this.contrat.getReference());
		LOG.info(this.employe.getContrat().getReference());
		Assert.assertEquals(this.employe.getContrat().getReference(), this.contrat.getReference());

		LOG.info("End Method affecterContratAEmploye");
	}

	public void deleteEmployeById() {
		LOG.info("Start Method deleteEmployeById");
		this.employe = iempServ.getAllEmployes().stream().filter(x -> x.getId() == this.employe.getId()).findFirst()
				.get();

		LOG.info(this.employe);
		iempServ.deleteEmployeById(this.employe.getId());
		// Assert.assertNull(iempServ.getEmployePrenomById(this.employe.getId()));
		Assert.assertFalse(empRepoistory.findById(this.employe.getId()).isPresent());
		LOG.info("End deleteEmployeById");

	}

	public void testGetAllEmployee() {
		List<Employe> listEMp = iempServ.getAllEmployes();
		LOG.info("**************************** FirstEmpName  " + listEMp.get(0).getNom());
		Assert.assertNotNull(listEMp);
		if(listEMp.isEmpty()){
			LOG.error("Empty Employee List");
		}
		
	}

}