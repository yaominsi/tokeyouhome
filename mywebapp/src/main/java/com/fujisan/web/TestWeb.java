package com.fujisan.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fujisan.api.service.UserService;

@Controller()
public class TestWeb {
	@Autowired
	private UserService UserService;
	@RequestMapping("/test")
	public String get() {
		System.out.println("......");
		return "index.jsp";
	}
}
