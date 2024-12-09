package br.com.alineccorrea.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
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

        System.out.println("Authorization:");
        System.out.println("username: " + username);
        System.out.println("password: " + password);

        //Validar usuário

        //Validar Senha

        //Segue execução
        
        filterChain.doFilter(request, response);
    }
    
}
