<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<c:import url="/template/header.jsp"/>
<link href="css/signin_style.css" rel="stylesheet" type="text/css"/>

<main>
    <form method="post" action="FrontController">
        <label for="usuario">Nome de Usuário: </label>
        <input type="text" required="required" id="usuario" name="usuario">

        <label for="senha">Senha: </label>
        <input type="password" required="required" id="senha" name="senha">
        
        <input type="hidden" name="ctrl" value="Login"/>
        
        <input type="submit" value="LOGAR">
    </form>
</main>

<c:import url="/template/footer.jsp"/>
