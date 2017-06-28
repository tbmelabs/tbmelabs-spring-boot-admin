package ch.tbmelabs.tv.webapp.security.login;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationStatus {
	public static final String AUTHENTICATION_SUCCEED = "SUCCEED";
	public static final String AUTHENTICATION_FAILED = "FAILED";

	private AuthenticationStatus() {
		// Hidden constructor
	}

	public static Map<String, String> getAllStatuse() {
		Map<String, String> statuse = new HashMap<>();

		Arrays.asList(AuthenticationStatus.class.getDeclaredFields()).stream().forEach(field -> {
			try {
				statuse.put(field.getName(), field.get(AuthenticationStatus.class).toString());
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		return statuse;
	}
}