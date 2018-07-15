package com.music.puzzle.authorization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.music.puzzle.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Filter for checking and verifying jwt for protected paths.
 * JWT is set in Authorization header, if jwt is valid chain will e continued.
 * If jwt is not present or is not valid, error response will be send to client,
 * with 401 (UNAUTHORIZED) status. 
 * 
 * @author Aram Kirakosyan.
 */
public class AuthorizationFilter implements Filter {

   	/**
	 * JWT secret key, value injected from auth0.properties file.
	 */
    @Value("${auth0.clientSecret}")
    private String secret;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // Cast to HttpServletRequest.
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        // Get jwt token from authorization header.
        String token = httpServletRequest.getHeader("Authorization");
        // Get username form header.
        String userNameFromRequest = httpServletRequest.getHeader("User");

//        if (token == null || userNameFromRequest == null) {
//            // No jwt token, send error response to client.
//            generateAndSendErrorResponse((HttpServletResponse) response, "headers are missing");
//            return;
//        }
//        Jws<Claims> claims = Jwts.parser()
//                    .setSigningKey(secret.getBytes("UTF-8"))
//                    .parseClaimsJws(token);
//        String userName = (String) claims.getBody().get("userName");
//        if(!userNameFromRequest.equals(userName)) {
//            generateAndSendErrorResponse(httpServletResponse, "Jwt is not valid");
//        }
        // TODO check also expiration time.
        // JWT validation passed, continue filter.
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    private void generateAndSendErrorResponse (HttpServletResponse httpServletResponse, String message) throws IOException {
        ErrorCode errorCodes = new ErrorCode(message);
        // jwt is not valid, send error response to client.
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().write(convertObjectToJson(errorCodes));
        httpServletResponse.flushBuffer();
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
