package com.example.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.Role;
import com.example.model.User;
import com.example.service.RoleService;
import com.example.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;

	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		List<Role> roles = roleService.getRoles();
		modelAndView.addObject("user", user);
		modelAndView.addObject("roles", roles);
		modelAndView.setViewName("registration");
		return modelAndView;
	}
	
	@RequestMapping(value="/registrationRole", method = RequestMethod.GET)
	public ModelAndView registrationRole(){
		ModelAndView modelAndView = new ModelAndView();
		Role role = new Role();
		modelAndView.addObject("role", role);
		modelAndView.setViewName("addRole");
		return modelAndView;
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			userService.saveUser(user);
			List<Role> roles = roleService.getRoles();
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.addObject("roles", roles);
			modelAndView.setViewName("registration");
			
		}
		return modelAndView;
	}
	

	@RequestMapping(value = "/registrationRole", method = RequestMethod.POST)
	public ModelAndView createNewRole(Role role) {
		ModelAndView modelAndView = new ModelAndView();
	
			roleService.addRole(role);
			modelAndView.addObject("successMessage", "Role has been registered successfully");
			modelAndView.addObject("role", new Role());
			modelAndView.setViewName("addRole");
			
		
		return modelAndView;
	}
	
	/*
	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
	public ModelAndView homeAdmin(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}*/
	
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@RequestMapping(value="/user/home", method = RequestMethod.GET)
	public ModelAndView homeUser(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + "(" + user.getEmail() + ")");
		modelAndView.addObject("role", ""+ user.getRoles().toString());
		modelAndView.setViewName("user/home");
		return modelAndView;
	}
	

}
