package br.com.alineccorrea.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.alineccorrea.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter{

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        
        var servletPath = request.getServletPath();

        //Executar apenas na rota de tasks
        if(servletPath.equals("/tasks/")) {

            //Pegar usuário e senha
            var authorization = request.getHeader("Authorization");

            //Removendo string "Basic" do parametro de authorization
            var authEncoded = authorization.substring("Basic".length()).trim();

            //Decodificar base64
            byte[] authDecoded = Base64.getDecoder().decode(authEncoded);

            //Transformar em String
            String authString = new String(authDecoded);

            //Extrair usuário e senha
            String[] credentials = authString.split(":");
            var username = credentials[0];
            var password = credentials[1];

            //Validar usuário
            var user = this.userRepository.findByUsername(username);

            if(user == null) {
                response.sendError(401);
            } else {
                
                //Validar Senha
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

                if(passwordVerify.verified) {
                    //setando o id do usuário na request
                    /*Também garante que mesmo o usuário enviando um id diferente na request, 
                    o filter vai setar o id correto do usuário autenticado*/
                    request.setAttribute("idUser", user.getId());
                    //segue execução
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401);
                }

            }
        } else {
            filterChain.doFilter(request, response);
        }
        
    }
    
}
