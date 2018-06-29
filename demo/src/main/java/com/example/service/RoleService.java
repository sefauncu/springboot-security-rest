package com.example.service;

import java.util.List;

import com.example.model.Role;

public interface RoleService {

	public void addRole(Role role);
	
	public Role getRole(int id);
	
	public List<Role> getRoles();
}
