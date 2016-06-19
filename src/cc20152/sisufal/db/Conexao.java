/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.NamingException;

public class Conexao
{

    private static Connection conexao = null;
    
    public static void conectar()
        throws ClassNotFoundException, SQLException, NamingException
    {
        Class.forName("com.mysql.jdbc.Driver");  
        
        conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/sisufal?allowMultiQueries=true", "root", "1234"); 
    }

    public static Connection getConexao()
    {
        if(conexao == null)
        {
            try
            {
                conectar();
            }
            catch(ClassNotFoundException ex)
            {
                ex.printStackTrace();
                System.out.println("Erro ao carregar o driver de conex\343o!");
            }
            catch(SQLException ex)
            {
                System.out.println((new StringBuilder("Erro ao conectar com o banco de dados! Motivo: ")).append(ex.getMessage()).toString());
            }
            catch(NamingException ex)
            {
                System.out.println((new StringBuilder("Erro ao achar a fonte de dados JNDI! Motivo: ")).append(ex.getMessage()).toString());
            }
        }
        return conexao;
    }

    public static void desconectar()
    {
        try
        {
            conexao.close();
            conexao = null;
        }
        catch(SQLException e)
        {
            System.out.println("Erro ao desconectar aplica\347\343o!");
        }
    }

}