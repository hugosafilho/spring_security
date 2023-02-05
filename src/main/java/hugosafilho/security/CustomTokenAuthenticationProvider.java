package hugosafilho.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class CustomTokenAuthenticationProvider implements AuthenticationProvider {

	private static final String APIKEY = "APIKEY";

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		return isValidToken((String) authentication.getPrincipal());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return PreAuthenticatedAuthenticationToken.class.equals(authentication);
	}

	private Authentication isValidToken(String token) {
		boolean isValid = false;

		if (System.getenv(APIKEY) != null && System.getenv(APIKEY).contentEquals(token)) {
			isValid = true;
		}

		if (isValid) {
			return new PreAuthenticatedAuthenticationToken("AuthenticatedUser", "ROLE_ADMIN");
		} else {
			throw new AccessDeniedException("Invalid authentication token");
		}
	}
}
