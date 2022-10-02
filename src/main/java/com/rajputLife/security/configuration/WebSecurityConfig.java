package com.rajputLife.security.configuration;

import com.rajputLife.security.oauth.CustomOAuth2User;
import com.rajputLife.security.oauth.CustomOAuth2UserService;
import com.rajputLife.security.oauth.UserService;
import com.rajputLife.security.userDetails.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//So in Spring security 3 @PreAuthorize("hasRole('ROLE_XYZ')")
//is the same as @PreAuthorize("hasAuthority('ROLE_XYZ')")
//and in Spring security 4
//
//in Spring Security 4 expects you to have the ROLE_ prefix whereas the hasAuthority('xyz')
// does not expect the prefix and evaluates exactly what is passed in.
//
//@Secured("User") on class will not work for methods covering with annotation.

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private CustomOAuth2UserService oauthUserService;

	@Autowired
	private UserService userService;

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
			.antMatchers(

					"/javax.faces.resource/*/*",
					"/javax.faces.resource/*",
					"/https://lh3.googleusercontent.com/*/*",

//					 "/login",
					"/oauth/**"

					).permitAll()
			.anyRequest().authenticated()

			.and()
			.formLogin().permitAll()
				.loginPage("/security/customLogin.jsf")

				.usernameParameter("app_username")
				.passwordParameter("app_password")

				/*
					"defaultSuccessUrl" will only come into picture only when loginPage(..)
					is typed in browser and pressed enter.

					otherwise, any other url can be entered first and browser will take it
					to loginPage(...) url, but will not take to "defaultSuccessUrl" but will
					take it to the url entered in browser initially.
				 */

				//Both below works!! :)
				//.defaultSuccessUrl("/product/product-list.jsf")

//				.defaultSuccessUrl("/product/product-list.jsf") //for login page use login.xhtml file path.
				.defaultSuccessUrl("/registered/product-list-forRegisteredUser.jsf") //for login page use login.xhtml file path.

				.failureUrl("/security/customLogin.jsf")


			.and()
			.oauth2Login()
				.loginPage("/security/customLogin.jsf")
				.userInfoEndpoint()
					.userService(oauthUserService)
				.and()
				.successHandler(new AuthenticationSuccessHandler() {
					@Override
					public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
						System.out.println("AuthenticationSuccessHandler invoked");
						System.out.println("Authentication name: " + authentication.getName());
						for (GrantedAuthority auth: authentication.getAuthorities()) {
							System.out.println("GrantedAuthority : " + auth.getAuthority());
						}
						CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
						oauthUser.getPicture();
						//We are loading Roles for Google/FB during login here
						userService.processOAuthPostLogin(oauthUser.getEmail());

						response.sendRedirect("/account");
						/* We needed to change "/account" from "/list" since http://localhost:8080 ==> index.html ==> to "/account"
						   ==> thus Spring was told to take to "/account"(from index.html) and "/list"(from OAuth) and thus it was
						   showing Spring dependency issues.

						   WTH "/list" was replaced with "/account" now replaced with "/" now replaced with "/account"
						*/
					}
				})

				.and()
				//TODO: work on logout URL.
				.logout().logoutSuccessUrl("/").permitAll()
				.and()
				.exceptionHandling().accessDeniedPage("/403");
	}
}
