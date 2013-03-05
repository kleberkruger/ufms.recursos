<%-- 
    Document   : index
    Created on : 21/02/2013, 14:14:44
    Author     : Kleber
--%>

<%@page import="javax.swing.JOptionPane"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="br.ufms.model.bean.Disciplina"%>
<%@page import="br.ufms.model.dao.DisciplinaDAO"%>
<%@page import="java.sql.SQLException"%>
<%@page import="br.ufms.utils.db.Pool"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Connection"%>
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
                    <%
                        Pool pool = Pool.getInstance();
                        List<Connection> connections = new ArrayList<Connection>();
                        Connection conn = null;
                        try {
                            for (int i = 0; i < 10; i++) {
                                connections.add(pool.getConnection());
                                //connections.get(i).close();
                            }
                            String sql = "SELECT * FROM disciplinas ORDER BY nome";
                            List<Disciplina> lista = new ArrayList<Disciplina>();
                            conn = pool.getConnection();

                            try {
                                PreparedStatement ps = conn.prepareStatement(sql);
                                ResultSet rs = ps.executeQuery();

                                while (rs.next()) {
                                    Disciplina curso = new Disciplina();
                                    curso.setCodigo(rs.getInt("codDisc"));
                                    curso.setNome(rs.getString("nome"));
                                    lista.add(curso);
                                    response.getWriter().println(curso.getNome());
                                }
                            } finally {
                                conn.close();
                            }
                        } catch (SQLException ex) {
                            response.getWriter().println(conn);
                            response.getWriter().println(ex.getMessage());
                        }
                    %>
                </article>
            </section>
            <jsp:include page="WEB-INF/jspf/rodape.jspf"/>
        </div>
    </body>
</html>
