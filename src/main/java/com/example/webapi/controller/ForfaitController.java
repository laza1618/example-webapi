package com.example.webapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webapi.repository.ForfaitRepository;
import com.example.webapi.exceptions.RessourceNotFoundException;
import com.example.webapi.model.Forfait;

@RestController
@RequestMapping("/api")
public class ForfaitController {
	
	@Autowired
	ForfaitRepository repo;
	
	//@RequestMapping(value="v2/forfaits", method = RequestMethod.GET)
	@GetMapping("/forfaits")
	public List<Forfait> getAllForfait() {
		return repo.findAll();
	}
	
	@GetMapping("/forfaits/{id}")
	public Forfait getForfaitById(@PathVariable(value="id")Long forfaitId) {
		return repo.findById(forfaitId).orElseThrow(
				() -> new RessourceNotFoundException("Forfait", "id", forfaitId)
		);
	}
	
	@PostMapping("/forfaits")
	public Forfait createForfait(@Valid @RequestBody Forfait forfait) {
		return repo.save(forfait);
	}
	
	@DeleteMapping("/forfaits/{id}")
	public ResponseEntity<Forfait> deleteForfaitById(@PathVariable(value="id")Long forfaitId) {		
		Forfait forfait = repo.findById(forfaitId).orElseThrow(
				() -> new RessourceNotFoundException("Forfait", "id", forfaitId));
		repo.delete(forfait);
		return ResponseEntity.ok(forfait);
	}
	

}
