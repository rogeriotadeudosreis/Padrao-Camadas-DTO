package com.rogerioreis.padraocamadasdto.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rogerioreis.padraocamadasdto.dto.UserDTO;
import com.rogerioreis.padraocamadasdto.dto.UserInsertDTO;
import com.rogerioreis.padraocamadasdto.services.UserService;
import com.rogerioreis.padraocamadasdto.services.exceptions.ServiceException;

@Controller
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {

		List<UserDTO> list = userService.findAll();
		return ResponseEntity.ok(list);
	}

	@PostMapping
	public ResponseEntity<UserDTO> insert(@RequestBody UserInsertDTO dto) {
		try {
			UserDTO userDto = userService.insert(dto);
			
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(userDto.getId()).toUri();
			
			return ResponseEntity.created(uri).body(userDto);
			
		} catch (ServiceException e) {
			
			return ResponseEntity.unprocessableEntity().build();
			
		}



	}

}
