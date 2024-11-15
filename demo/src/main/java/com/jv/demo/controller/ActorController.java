package com.jv.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jv.demo.model.Actor;
import com.jv.demo.service.ActorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/actors")
public class ActorController {
	
	@Autowired
	private ActorService actorService;
	
	@GetMapping
	ResponseEntity<List<Actor>> getAllActors() {
		return new ResponseEntity<List<Actor>>(actorService.getActors(), HttpStatus.OK);
	}
	
	@PostMapping
	ResponseEntity<List<Actor>> saveAllActors(@Valid @RequestBody List<Actor> actors) {
		return new ResponseEntity<List<Actor>>(actorService.saveActor(actors), HttpStatus.OK);
	}
}
