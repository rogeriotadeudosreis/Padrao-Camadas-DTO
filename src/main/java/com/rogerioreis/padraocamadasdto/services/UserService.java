package com.rogerioreis.padraocamadasdto.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rogerioreis.padraocamadasdto.dto.UserDTO;
import com.rogerioreis.padraocamadasdto.dto.UserInsertDTO;
import com.rogerioreis.padraocamadasdto.entities.User;
import com.rogerioreis.padraocamadasdto.repositories.UserRepository;
import com.rogerioreis.padraocamadasdto.services.exceptions.ServiceException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public List<UserDTO> findAll() {

		List<User> list = userRepository.findAll();

		return list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());

	}

	public UserDTO insert(UserInsertDTO dto) {

		User user = userRepository.findByEmail(dto.getEmail());

		if (user != null) {
			throw new ServiceException("Um usuário com esse email já existe");
		}

		User obj = new User();
		obj.setName(dto.getName());
		obj.setEmail(dto.getEmail());
		obj.setPassword(passwordEncoder.encode(dto.getPassword()));

		obj = userRepository.save(obj);

		return new UserDTO(obj);

	}

}
