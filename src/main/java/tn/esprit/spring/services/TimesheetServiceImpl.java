package tn.esprit.spring.services;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.EmployeTests;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
public class TimesheetServiceImpl implements ITimesheetService {
	private static final Logger LOG = LogManager.getLogger(EmployeTests.class);
	
	
	private static final Logger l = LogManager.getLogger(TimesheetServiceImpl.class);


	@Autowired
	MissionRepository missionRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;
	@Autowired
	EmployeRepository employeRepository;
	
	public int ajouterMission(Mission mission) {
		missionRepository.save(mission);
		l.info("********** mission ajoutée  " + mission);
		return mission.getId();
	}
    
	public void affecterMissionADepartement(int missionId, int depId) {
		Mission mission = missionRepository.findById(missionId).get();
		Departement dep = deptRepoistory.findById(depId).get();
		mission.setDepartement(dep);
		missionRepository.save(mission);
		l.info("********** mission "+mission+" affectée à "+dep );
		
	}

	public void ajouterTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin) {
		TimesheetPK timesheetPK = new TimesheetPK();
		timesheetPK.setDateDebut(dateDebut);
		timesheetPK.setDateFin(dateFin);
		timesheetPK.setIdEmploye(employeId);
		timesheetPK.setIdMission(missionId);
		
		Timesheet timesheet = new Timesheet();
		timesheet.setTimesheetPK(timesheetPK);
		timesheet.setValide(false); //par defaut non valide
		timesheetRepository.save(timesheet);
		l.info("********** timesheet ajouté "+timesheet );

		
	}

	
	public void validerTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin, int validateurId) {
		System.out.println("In valider Timesheet");
		Employe validateur = employeRepository.findById(validateurId).get();
		Mission mission = missionRepository.findById(missionId).get();
		//verifier s'il est un chef de departement (interet des enum)
		if(!validateur.getRole().equals(Role.CHEF_DEPARTEMENT)){
			System.out.println("l'employe doit etre chef de departement pour valider une feuille de temps !");
			l.error("********** employé invalide !!!" );

			return;
		}
		//verifier s'il est le chef de departement de la mission en question
		boolean chefDeLaMission = false;
		for(Departement dep : validateur.getDepartements()){
			if(dep.getId() == mission.getDepartement().getId()){
				chefDeLaMission = true;
				break;
			}
		}
		if(!chefDeLaMission){
			LOG.info("l'employe doit etre chef de departement de la mission en question");
			l.error("********** l'employe doit etre chef de departement de la mission "+mission );

			return;
		}
		
	}

	
	public List<Mission> findAllMissionByEmployeJPQL(int employeId) {
return Collections.emptyList();
}

	
	public List<Employe> getAllEmployeByMission(int missionId) {
		return Collections.emptyList();
	}
	public Timesheet getTimessheetById(int tSId) {
		if(tSId<0) {
			l.error("*******Error,Invalide identifier");
		}
		else {
			l.info("*****Succés****"+timesheetRepository.findById(tSId).get());

		}
		return timesheetRepository.findById(tSId).get();
	}
	public void deleteTimeSheet(int id){
		timesheetRepository.deleteById(id);
		l.info("********** timesheet supprimé******" );

	}
	
	
	public Mission getMissionById(int id) {
		if(id<0) {
			l.error("*******Error,Invalide identifier");
		}
		else {
			l.info("*****Succés****");

		}
		return missionRepository.findById(id).get();
	}
	public void deleteMissionById(int id){
		missionRepository.deleteById(id);
		l.info("********** mission supprimé******" );

	}
}
