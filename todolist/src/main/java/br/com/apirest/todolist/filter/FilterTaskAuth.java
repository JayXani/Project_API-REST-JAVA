package br.com.apirest.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.apirest.todolist.users.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {
    @Autowired
    private IUserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var serveletPath = request.getServletPath();
        if(serveletPath.startsWith("/tasks/")){
            // Decode user and password
            var authorization = request.getHeader("Authorization");
            var authEconded = authorization.substring(5).trim();    // Fist "param" is the length of word, That i want to remove
            byte[] authDecode = Base64.getDecoder().decode(authEconded);
            var authString = new String(authDecode);

            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];

            // Validation of user
            var userExist = this.userRepository.findByUserName(username);
            if(userExist == null){
                response.sendError(401);
            }else{
                // Verify the password of user
                var passwordValidation = BCrypt.verifyer().verify(password.toCharArray(), userExist.getPassword());
                if(passwordValidation.verified){
                    request.setAttribute("user_ID", userExist.getId());
                    filterChain.doFilter(request, response);
                }else{
                    response.sendError(401);
                }
            }
        }else{
            filterChain.doFilter(request, response);
        }
    }
}

