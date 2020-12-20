package com.sammidev.kotlin.springboot.okta

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class KotlinSpringbootOktaApplication

fun main(args: Array<String>) {
	runApplication<KotlinSpringbootOktaApplication>(*args)
}

@Configuration
class SecurityConfig() : WebSecurityConfigurerAdapter() {
	override fun configure(http: HttpSecurity?) {
		http
				?.authorizeRequests()
				?.anyRequest()?.authenticated()
				?.and()
				?.formLogin()
				?.and()
				?.httpBasic()
	}

	override fun configure(auth: AuthenticationManagerBuilder?) {
		auth?.inMemoryAuthentication()
				?.withUser("sammidev")
				?.password("{noop}password")
				?.roles("USER")
	}
}

@RestController
class Greeting {

	@GetMapping("/")
	@ResponseBody
	fun name(@RequestParam("name") name: String): String {
		return "hello, ${name}"
	}

}