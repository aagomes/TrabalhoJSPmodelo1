<%-- 
    Document   : listar
    Created on : 28/09/2016, 12:35:18
    Author     : alexandre
--%>

<%@page import="br.edu.ifsul.modelo.Especialidade"%>
<%@page import="br.edu.ifsul.dao.EspecialidadeDAO"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<jsp:useBean id="especialidadeDAO" scope="session"
             type="EspecialidadeDAO"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Listagem de especialidades</title>
    </head>
    <body>
        <a href="../index.html">Inicio</a>
        <h2>Especialidades</h2>
        <h2><%=especialidadeDAO.getMensagem()%></h2>
        <a href="ServletEspecialidade?acao=incluir">Incluir</a>
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Alterar</th>
                    <th>Excluir</th>
                </tr>
            </thead>
            <tbody>
                <%
                  for(Especialidade e :especialidadeDAO.getLista() )  {
                %>  
                <tr>
                    <td><%=e.getId()%></td>
                    <td><%=e.getNome()%></td>
                    <td><a href="ServletEspecialidade?acao=alterar&id=<%=e.getId()%>">Alterar</a></td>
                    <td><a href="ServletEspecialidade?acao=excluir&id=<%=e.getId()%>">Excluir</a></td>
                </tr>
                <%
                    }
                %>    
                    
            </tbody>
        </table>
    </body>
</html>
