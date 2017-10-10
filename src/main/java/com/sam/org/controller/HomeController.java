package com.sam.org.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping("/csr*")
	public String getCsrHomeUrl(){
		return "Pages/csr";
	}
	@RequestMapping("/keySpecs")
	public String getKeySpecPage(){
		return "Pages/keySizeSpecification";
	}
}
