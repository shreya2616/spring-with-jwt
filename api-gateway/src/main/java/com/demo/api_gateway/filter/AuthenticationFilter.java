package com.demo.api_gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    private final RestTemplate restTemplate;

    @Autowired
    public AuthenticationFilter(RestTemplate restTemplate) {
        super(Config.class);
        this.restTemplate = restTemplate;
    }

    public static class Config {
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Missing Authorization Header");
                }
                String authHeaderToken = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeaderToken != null && authHeaderToken.startsWith("Bearer ")) {
                    authHeaderToken = authHeaderToken.substring(7);
                }
                try {
                    // Make the token validation call using RestTemplate
                    String url = "http://localhost:8090/api/auth/validate/token?token=" + authHeaderToken;
                    Map<String, Object> response = restTemplate.getForObject(url, HashMap.class);

                    // Validate the response
                    if (response == null) {
                        throw new RuntimeException("Response from auth service is null");
                    }

                    Boolean isValid = (Boolean) response.get("isValid");
                    List<String> roles = (List<String>) response.get("roles");

                    if (Boolean.FALSE.equals(isValid)) {
                        throw new RuntimeException("Invalid Token");
                    }

                    // Role-based authorization: Check roles based on the request path
                    String path = exchange.getRequest().getPath().toString();
                    if (path.startsWith("/admin") && !roles.contains("ROLE_ADMIN")) {
                        throw new RuntimeException("Forbidden: Admin role required");
                    } else if (path.startsWith("/user") && !roles.contains("ROLE_USER")) {
                        throw new RuntimeException("Forbidden: User role required");
                    }
                } catch (RestClientException e) {
                    System.out.println(e.getMessage());
                    throw new RuntimeException("Invalid Access!! : " + e.getMessage());
                }
            }
            return chain.filter(exchange);
        });
    }
}
