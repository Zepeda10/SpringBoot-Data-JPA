package com.example.app;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.example.app.auth.handler.LoginSuccessHandler;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig {

	@Autowired
	private LoginSuccessHandler successHandler;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private DataSource dataSource;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().and().authorizeHttpRequests()
				.requestMatchers("/", "/css/**", "/js/**", "/images/**", "/listar").permitAll()
				// .requestMatchers("/ver/**").hasAnyRole("USER") // Estas autorizaciones se
				// reemplazarán con anotaciones en el controlador
				// .requestMatchers("/uploads/**").hasAnyRole("USER")
				// .requestMatchers("/form/**").hasAnyRole("ADMIN")
				// .requestMatchers("/eliminar/**").hasAnyRole("ADMIN")
				// .requestMatchers("/factura/**").hasAnyRole("ADMIN")
				.anyRequest().authenticated().and().formLogin().successHandler(successHandler).loginPage("/login")
				.permitAll().and().logout().permitAll().and().exceptionHandling().accessDeniedPage("/error_403");

		return http.build();
	}

	/*
	 * @Bean public UserDetailsService userDetailsService() throws Exception {
	 * 
	 * InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
	 * manager.createUser(
	 * User.withUsername("hilda").password(this.passwordEncoder.encode("54321")).
	 * roles("USER").build()); manager.createUser(
	 * User.withUsername("admin").password(this.passwordEncoder.encode("12345")).
	 * roles("ADMIN", "USER").build());
	 * 
	 * return manager; }
	 */

	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {
		build.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder)
				.usersByUsernameQuery("select username, password, enabled from users where username=?")
				.authoritiesByUsernameQuery(
						"select u.username, a.authority from authorities a inner join users u on (a.user_id=u.id) where u.username=?");

		/*
		 * //UserBuilder users = User.withDefaultPasswordEncoder(); (Deprecated)
		 * 
		 * PasswordEncoder encoder =
		 * PasswordEncoderFactories.createDelegatingPasswordEncoder(); UserBuilder users
		 * = User.builder().passwordEncoder(encoder::encode);
		 * 
		 * build.inMemoryAuthentication()
		 * .withUser(users.username("admin").password("12345").roles("ADMIN", "USER"))
		 * .withUser(users.username("andres").password("12345").roles("USER"));
		 */
	}

}
