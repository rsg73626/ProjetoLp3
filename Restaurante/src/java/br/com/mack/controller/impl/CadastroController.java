/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mack.controller.impl;

import br.com.mack.controller.AbstractController;
import br.com.mack.persistence.UserDAO;
import br.com.mack.persistence.entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Bruno
 */
public class CadastroController extends AbstractController {

    UserDAO userDAO = lookupUserDAOBean();

    @Override
    public void execute() {
        this.returnPage = "user_area/home.jsp";
        
        String senha = request.getParameter("senha");
        String confirmacaoSenha = request.getParameter("conf_senha");
        User user = new User();
        user.setFullName(request.getParameter("nome_completo"));
        user.setBirthday(request.getParameter("dt_nasc"));
        user.setEmail(request.getParameter("email"));
        user.setUserName(request.getParameter("usuario"));
        user.setPassword(senha);
        
        boolean erro = false;
        List<String> erros = new ArrayList();
        
        if(user.getFullName() == null){
            erro = true;
            erros.add("Preencha o campo NOME COMPLETO");
        }
        if(user.getEmail() == null){
            erro = true;
            erros.add("Preencha o campo E-MAIL");
        }
        if(user.getUserName() == null){
            erro = true;
            erros.add("Preencha o campo NOME DE USUÁRIO");
        }
        if(user.getPassword() == null){
            erro = true;
            erros.add("Preencha o campo SENHA");
        }
        if(!senha.equals(confirmacaoSenha)){
            erro = true;
            erros.add("Os campos SENHA e CONFIRMAÇÃO DE SENHA devem possuir valores iguais");
        }
        
        if(erro){
            this.returnPage = "erro.jsp";
            this.request.getSession().setAttribute("errorMessages", erros);
            return;
        }
        
        try {
            userDAO.create(user);
            request.getSession().setAttribute("usuario", user);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            erro = true;
            erros.add("Já existe um usuário cadastrado com este NOME DE USUÁRIO");
            this.request.getSession().setAttribute("errorMessages", erros);
            this.returnPage = "erro.jsp";
        }
    }

    private UserDAO lookupUserDAOBean() {
        try {
            Context c = new InitialContext();
            return (UserDAO) c.lookup("java:global/Restaurante/UserDAO!br.com.mack.persistence.UserDAO");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
