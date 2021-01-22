package com.contactmanager.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.contactmanager.entity.Message;
import com.contactmanager.entity.User;
import com.contactmanager.repository.UserRepository;

@Controller
public class UserController {
	
	@Autowired
	private UserRepository userRepository;

	@RequestMapping({ "/", "/home" })
	public String home(Model model) {
		model.addAttribute("title", "Home");
		return "home";
	}

	@RequestMapping("/about")
	public String aboutUs(Model model) {
		model.addAttribute("title", "About Us");
		return "about";
	}

	@RequestMapping(value="/reg", method=RequestMethod.GET)
	public String register(Model model) {
		model.addAttribute("title", "Register");
		model.addAttribute("user", new User());
		return "register";
	}
	
	@RequestMapping(value="/reg", method=RequestMethod.POST)
	public String registerPost(@Valid @ModelAttribute("user") User user,BindingResult result,@RequestParam(name = "aggrement", defaultValue = "false") Boolean aggrement, HttpSession session,Model model) {
		
		try {
			if (!aggrement) {
				throw new Exception("Please accept Terms and Conditions");
			}
			
			if(result.hasErrors()) {
				model.addAttribute("user", user);
				return "register";
			}
			
			user.setRoll("ROLE_USER");
			user.setEnabled(true);
			user.setImg("default.png");
			userRepository.save(user);
			model.addAttribute("user", new User());
			session.setAttribute("message", new Message("!!! Registration is Successful !!! ", "alert-success"));
			return "register";

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("Something went wrong !!! " + e.getMessage(), "alert-danger"));
			return "register";
		}
	}

	@RequestMapping(value="/signin",method=RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("title", "Login");
		return "login";
	}
	
	@RequestMapping(value="/user/dashboard",method=RequestMethod.GET)
	public String loginSuccess(Model model) {
		model.addAttribute("title", "dashboard");
		return "user/user_dashboard";
	}
}
