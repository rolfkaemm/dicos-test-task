/**
 * (c) DICOS GmbH, 2020
 *
 * $Id$
 */

package de.dicos.springboot.repairservice.restful.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import lombok.NoArgsConstructor;

/**
 *
 * @author bhochberger
 */
@Configuration
@EnableWebSecurity
@NoArgsConstructor
public class WebSecurityConfig
	extends
	WebSecurityConfigurerAdapter
{
	// /////////////////////////////////////////////////////////
	// Class Members
	// /////////////////////////////////////////////////////////


	// /////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////


	// /////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////
	/**
	 * {@inheritDoc}
	 *
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
	 */
	@Override
	protected void configure(HttpSecurity httpSecurity)
		throws Exception
	{
        // not relevant so disable it
		httpSecurity.csrf().disable();
		httpSecurity.httpBasic().disable();
		httpSecurity.formLogin().disable();
		httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		// Requests ohne Authentisierung
//		httpSecurity.authorizeRequests()
//			.antMatchers("/rest/ok").permitAll()
//			.antMatchers("/**").authenticated().and().httpBasic();
		
		httpSecurity.authorizeRequests().anyRequest().permitAll();
	}

	// /////////////////////////////////////////////////////////
	// Inner Classes
	// /////////////////////////////////////////////////////////


}
