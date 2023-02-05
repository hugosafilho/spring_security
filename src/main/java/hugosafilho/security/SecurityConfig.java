package hugosafilho.security;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	CustomTokenAuthenticationProvider customTokenAuthenticationProvider;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.httpBasic().disable().csrf().disable().formLogin().disable().logout().disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authenticationProvider(customTokenAuthenticationProvider)
				.addFilterBefore(getFilter(), AnonymousAuthenticationFilter.class).authorizeRequests()
				.requestMatchers(getRequestMatchers()).authenticated();

		return http.build();
	}

	private RequestMatcher getRequestMatchers() {
		return new OrRequestMatcher(new AntPathRequestMatcher("/**"));
	}

	private Filter getFilter() throws Exception {
		return new CustomAuthenticationProcessingFilter();
	}
}
