<%-- 
    Document   : index
    Created on : 21/02/2013, 14:14:44
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
            <jsp:include page="WEB-INF/jspf/cabecalho.jspf"/>
            <jsp:include page="WEB-INF/jspf/menu_cadastros.jspf"/>
            <section class="content">
                <article>
                </article>
            </section>
            <jsp:include page="WEB-INF/jspf/rodape.jspf"/>
        </div>
    </body>
</html>
