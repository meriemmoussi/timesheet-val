package tn.esprit.spring.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Departement;

import tn.esprit.spring.repository.DepartementRepository;

@Service
public class DepartementServiceImpl implements IDepartementService {
	
	@Autowired
	DepartementRepository deptRepository;
	private static final Logger l = LogManager.getLogger(DepartementServiceImpl.class);
	public List<Departement> getAllDepartements() {
		List<Departement> depNames= (List<Departement>) deptRepository.findAll();
		l.info("*******La liste des départements est : ");
		
		if (depNames.isEmpty()) {
			l.warn("vide********");
		} else {
			l.info(depNames + "*********");
		}
		return depNames;
	}

	@Override
	public Departement addOrUpdateDep(Departement d) {
		Departement dept = deptRepository.save(d);
		l.info("**********Le departement  est ajouté/mis a jour avec succés " + dept);
		return dept;
	}

	

	@Override
	public Departement getDepartementById(int depId) {
		if(depId<0) {
			l.warn("*******Error,Invalide identifier");
		}
		else {
			if(deptRepository.findById(depId).isPresent()){
				Departement d =deptRepository.findById(depId).get();
			l.info("*****Le nom du departement est " + d.getName()+ "********");
			}
		}
		if(deptRepository.findById(depId).isPresent()){
			Departement d =deptRepository.findById(depId).get();
		return d;
		}
		return deptRepository.findById(depId).get();
	}
	
	

}
