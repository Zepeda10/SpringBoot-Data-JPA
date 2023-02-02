package com.example.app.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ch.qos.logback.core.model.Model;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login(Model model, Principal principal, RedirectAttributes flash) {
		
		if(principal != null) { // Significa que ya inició sesión anteriormente
			flash.addAttribute("info","Ya ha iniciado sesión anteriormente");
			return "redirect:/"; // Para evitar que pida volver a iniciar sesión
		}
		
		return "login";
	}
}
