package com.sammidev.kotlin.springboot.okta

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
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
				?.antMatcher("/**")
				?.authorizeRequests()
				?.antMatchers("/","/login**")?.permitAll()
				?.anyRequest()?.authenticated()
				?.and()
				?.oauth2Login()
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

	// template
	@RequestMapping("/securedPage")
	fun securePage(
			model: Model,
			@RegisteredOAuth2AuthorizedClient authorizedClient: OAuth2AuthorizedClient,
			@AuthenticationPrincipal oAuth2User: OAuth2User): String {
		model.addAttribute("userName", oAuth2User.name)
		model.addAttribute("clientName", authorizedClient.clientRegistration.clientName)
		model.addAttribute("userAttributes", oAuth2User.attributes)
		return "securedPage"
	}

	@RequestMapping("/hola")
	@ResponseBody
	fun welcome(@RequestParam("name") name: String): String =  "hello, $name"

	// template
	@RequestMapping("/")
	fun index(): String = "index"
}

