package hugosafilho.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

public class CustomAuthenticationProcessingFilter extends OncePerRequestFilter {

	private static final String APIKEY = "APIKEY";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		Optional<String> apiKey = Collections.list(request.getHeaderNames()).stream()
				.filter(header -> header.toUpperCase().contentEquals(APIKEY)).findFirst();

		if (apiKey.isEmpty()) {
			throw new AccessDeniedException("APIKEY not found");
		}

		PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(
				request.getHeader(apiKey.get()), null);

		SecurityContextHolder.getContext().setAuthentication(token);

		filterChain.doFilter(request, response);
	}
}
