package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Role;
import com.example.repository.RoleRepository;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
    private RoleRepository roleRepository;
	
	@Override
	public void addRole(Role role) {
		
		roleRepository.save(role);
	}

	@Override
	public Role getRole(int id) {
		
		return roleRepository.findOne(id);
	}

	@Override
	public List<Role> getRoles() {

		return roleRepository.findAll();
	}

}
