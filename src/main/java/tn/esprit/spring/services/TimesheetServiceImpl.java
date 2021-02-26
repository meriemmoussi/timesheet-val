package tn.esprit.spring.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		Employe validateur = employeRepository.findById(validateurId).get();
		Mission mission = missionRepository.findById(missionId).get();
		//verifier s'il est un chef de departement (interet des enum)
		if(!validateur.getRole().equals(Role.CHEF_DEPARTEMENT)){
			l.error("********** employé invalide !!!" );

			return;
		}
		boolean chefDeLaMission = false;
		for(Departement dep : validateur.getDepartements()){
			if(dep.getId() == mission.getDepartement().getId()){
				chefDeLaMission = true;
				break;
			}
		}
		if(!chefDeLaMission){
			
			l.error("********** l'employe doit etre chef de departement de la mission "+mission );

			return;
		}

		TimesheetPK timesheetPK = new TimesheetPK(missionId, employeId, dateDebut, dateFin);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
	}

	
	public List<Mission> findAllMissionByEmployeJPQL(int employeId) {
		
		return timesheetRepository.findAllMissionByEmployeJPQL(employeId);
	}

	
	public List<Employe> getAllEmployeByMission(int missionId) {
		return new ArrayList<Employe>();
	}
	public Timesheet getTimessheetById(int tSId) {
		if(tSId<0) {
			l.error("*******Error,Invalide identifier");
		}
		else {
			l.info("*****Succés****"+timesheetRepository.findById(tSId).get());

		}
		return timesheetRepository.findById(tSId).isPresent() ? 
				timesheetRepository.findById(tSId).get() : new Timesheet();
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
		return missionRepository.findById(id).isPresent() ? 
				missionRepository.findById(id).get() : new Mission();
	}
	public void deleteMissionById(int id){
		missionRepository.deleteById(id);
		l.info("********** mission supprimé******" );

	}
}
