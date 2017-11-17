package com.springSecProj.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springSecProj.models.Role;
import com.springSecProj.models.User;
import com.springSecProj.repositories.UserRepository;
import com.springSecProj.services.UserService;
import com.springSecProj.validators.UserValidator;

@Controller
public class Users {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserValidator userValidator;
	
	/*
    @GetMapping("/registration")
    public String registerForm(@Valid @ModelAttribute("user") User user) {
        return "registrationPage";
    }
    */
	
    @GetMapping("/login")
    public String login(@ModelAttribute("user") User user, @RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model) {
        if(error != null) {
            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
        }
        if(logout != null) {
            model.addAttribute("logoutMessage", "Logout Successfull!");
        }
        return "loginPage";
    }
    
    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession httpSession) {
    	//System.out.println("user: " + user);
    	
    	userValidator.validate(user, result);
    	
    	if(result.hasErrors()) {
    		//System.out.println("err: " + result);
    		return "loginPage";
    	}
    	
    	if(userService.getCountOfAdminRoleUser() == 0) {
    		userService.saveUserWithAdminRole(user);
    	}else {
    		userService.saveWithUserRole(user);
    	}
    	return "redirect:/login";
    	
    }
    
    @GetMapping(value = {"/", "/home"})
    public String home(Principal principal, Model model) {        
        String email = principal.getName();        
       // model.addAttribute("currentUser", userService.findByEmail(email));
        //return "homePage";
        
        User user = userService.findByEmail(email);
        List<Role> userRoles = user.getRoles();
        boolean isAdmin = false;
        String role = "";
        
        for(int i = 0; i < userRoles.size(); i++) {
        	role = userRoles.get(i).getName();        	
        	if(role.equals("ROLE_ADMIN")) {
        		isAdmin = true;
        		break;
        	}
        }
        
        model.addAttribute("currentUser", userService.findByEmail(email));
        
        if(isAdmin) {        	
        	return "redirect:/admin";
        }else {
        	return "homePage";
        }
    }
    
    @GetMapping("/admin")
    public String adminPage(Principal principal, Model model) {
        String email = principal.getName();        
        model.addAttribute("currentUser", userService.findByEmail(email));
        model.addAttribute("allUsers", userService.getAllUsers());
        return "adminPage";
    }
    
    @GetMapping("/delete/{user_id}")
    public String deleteUser(@PathVariable("user_id") Long user_id) {
    	User user = userService.findUserById(user_id);
    	userService.deleteUser(user);
    	return "redirect:/admin";
    }
    
    @GetMapping("/admin/new/{user_id}")
    public String giveAdminPrivilege(@PathVariable("user_id") Long user_id) {
    	userService.giveAdminPrivilege(user_id);
    	return "redirect:/admin";
    	
    }
}
