package tn.esprit.spring.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import tn.esprit.spring.entities.Departement;

import tn.esprit.spring.services.IDepartementService;


@RestController
public class RestControlDepartment {

	@Autowired
	IDepartementService idepservice; 
	
	
	@PostMapping("/ajouterDep")
	@ResponseBody
	public Departement ajouterEmploye(@RequestBody Departement d)
	{
		idepservice.addOrUpdateDep(d);
		return d;
	}

	
	@GetMapping(value = "/getAllDepts")
    @ResponseBody
	public List<Departement> getAllDepartments() {
		
		return idepservice.getAllDepartements();
	}

	
	
}
