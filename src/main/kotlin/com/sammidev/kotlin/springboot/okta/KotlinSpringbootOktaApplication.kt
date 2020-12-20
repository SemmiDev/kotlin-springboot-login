package com.sammidev.kotlin.springboot.okta

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@SpringBootApplication
class KotlinSpringbootOktaApplication

fun main(args: Array<String>) {
	runApplication<KotlinSpringbootOktaApplication>(*args)
}

@Configuration
@EnableWebSecurity
class SecurityConfig() : WebSecurityConfigurerAdapter() {
	override fun configure(http: HttpSecurity?) {
		http
				?.authorizeRequests()
				?.antMatchers("/public/**")
				?.permitAll()
				?.anyRequest()?.authenticated()
				?.and()
				?.formLogin()
				?.loginPage("/login.html")
				?.failureUrl("/login-error.html")
				?.permitAll()
	}

	override fun configure(auth: AuthenticationManagerBuilder?) {
		auth?.inMemoryAuthentication()
				?.withUser("sammidev")
				?.password("{noop}password")
				?.roles("USER")
	}
}

@Controller
class Student {

	@RequestMapping("/")
	@ResponseBody
	fun welcome(@RequestParam("name") name: String): String {
		return "hello, $name"
	}

	// login form
	@RequestMapping("/login.html")
	fun login(): String {
		return "login.html"
	}

	// login form with error
	@RequestMapping("/login-error.html")
	fun loginError(model: Model): String {
		model.addAttribute("loginError", true)
		return "login.html"
	}
}




