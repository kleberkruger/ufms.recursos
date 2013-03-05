<%-- 
    Document   : login
    Created on : 22/02/2013, 10:42:50
    Author     : Kleber
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UFMS Recursos</title>
        <!-- inserindo o css -->
        <link href="resources/css/estilos.css" rel="stylesheet">
        <link href="resources/css/google-buttons.css" rel="stylesheet">
        <!-- scripts jQuery -->
        <script src="resources/js/jquery.1.9.1.min.js"></script>
        <script src="resources/js/jquery.dropdown.menu.js"></script>
        <script src="resources/js/jquery.tiptip.js"></script>
    </head>
    <body>
        <div class="inner">
            <header>
                <hgroup id="logo">
                    <h1>UFMS Recursos</h1>
                    <h2>Sistema de Gerência de Recursos da Universidade Federal de Mato Grosso do Sul</h2>
                </hgroup>
            </header>
            <section class="content">
                <article>
                    <form action="controller" method="get">
                        <div class="oculto">
                            <h3>Login</h3>
                            <fieldset>
                                <legend>Login</legend>
                                <input type="hidden" name="cmd" value=""/>
                                <p class="first">
                                    <label for="modo">Entrar como:</label>
                                    <select name="modo">
                                        <option value="0">Selecione</option>
                                        <option value="1">Administrador</option>
                                        <option value="2">Professor</option>
                                    </select>
                                </p>
                                <p>
                                    <label for="login">Login:</label>
                                    <input type="text" name="login" id="login" size="30"/>
                                </p>
                                <p>
                                    <label for="senha">Senha:</label>
                                    <input type="password" name="senha" id="senha" size="30"/>
                                </p>
                                <p>${mensagem}</p>
                            </fieldset>
                            <button type="submit" id="salvar" title="Salvar">Salvar</button>
                        </div>
                    </form>
                </article>
            </section>
            <jsp:include page="WEB-INF/jspf/rodape.jspf"/>
        </div>
    </body>
</html>
