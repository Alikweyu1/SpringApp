package apharm.co.ke.fskcb.UserManagement.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Autowired
    ObjectMapper mObjectMapper;
    @Override
    public void commence
            (HttpServletRequest request, HttpServletResponse response,
             AuthenticationException authException) throws IOException{
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,authException.getMessage());
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AccessDeniedException accessDeniedException) throws IOException {
        log.error("AccessDenied error: {}", accessDeniedException.getMessage());
        httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
        //
        ObjectNode node = mObjectMapper.createObjectNode();
        node.put("status",0);
        node.put("message","Authentication Failed");
        node.putPOJO("data",mObjectMapper.createObjectNode());
        //mObjectMapper.writeValue(httpServletResponse.getOutputStream(), node);
        httpServletResponse.getWriter().write(mObjectMapper.writeValueAsString(node));
    }
}
