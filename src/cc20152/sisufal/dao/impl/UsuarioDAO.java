/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.dao.impl;

import cc20152.sisufal.dao.IBaseDAO;
import cc20152.sisufal.db.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import cc20152.sisufal.models.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author Dayvson
 */
public class UsuarioDAO implements IBaseDAO {
    
    private Connection conn;
    
    @Override
    public String save(Object object){
        conn = Conexao.getConexao();
        String sql = "INSERT INTO usuario(id_servidor, login, password, admin) VALUES(?, ?, ?, ?)";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, ((Usuario) object).getServidor().getId());
            st.setString(2, ((Usuario) object).getLogin());
            st.setString(3, ((Usuario) object).getPassword());
            st.setBoolean(4, ((Usuario) object).isIsAdmin());
            st.execute();
        }catch(Exception ex){
            ex.printStackTrace();
            return "ERROR";
        }
        return "OK";
    }
    
    @Override
    public String update(Object object){
        this.conn = Conexao.getConexao();
        String sql = "UPDATE usuario SET id_servidor = ?, login = ?, password = ?, admin = ? WHERE id_usuario = ?";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, ((Usuario) object).getServidor().getId());
            st.setString(2, ((Usuario) object).getLogin());
            st.setString(3, ((Usuario) object).getPassword());
            st.setBoolean(4, ((Usuario) object).isIsAdmin());
            st.setInt(5, ((Usuario)object).getId());
            
            st.execute();
        }catch(Exception ex){
            ex.printStackTrace();
            return "ERROR";
        }
        return "OK";
    }
    
    @Override
    public String delete(Object object){
        this.conn = Conexao.getConexao();
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, ((Usuario) object).getId());
            st.execute();
        }catch(Exception ex){
            ex.printStackTrace();
            return "ERROR";
        }
        return "OK";
    }
    
    @Override
    public List<Usuario> listAll(){
        ArrayList<Usuario> listaUsuario = new ArrayList();
        this.conn = Conexao.getConexao();
        String sql = "SELECT * FROM usuario u LEFT JOIN servidor s ON s.id_servidor = u.id_servidor";
        
        try{
            PreparedStatement st = this.conn.prepareStatement(sql);
            ResultSet rs;

            rs = st.executeQuery();
            listaUsuario = mapearResultSet(rs, false);
            //conexao.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return listaUsuario;
    }
    
    public Usuario logar(Usuario usuario){
        
        ArrayList<Usuario> listaUsuario = new ArrayList();
        this.conn = Conexao.getConexao();
        String sql = "SELECT * FROM usuario u LEFT JOIN servidor s ON s.id_servidor = u.id_servidor WHERE login = ?";
        
        try{
            PreparedStatement st = this.conn.prepareStatement(sql);
            ResultSet rs;
            st.setString(1, usuario.getLogin());
            
            rs = st.executeQuery();
            listaUsuario = mapearResultSet(rs, true);
            //conexao.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return listaUsuario.size() > 0 ? listaUsuario.get(0) : null;
    }
    
    public ArrayList<Usuario> mapearResultSet(ResultSet rs, boolean hash) throws SQLException{
		
	ArrayList<Usuario> listaUsuario = new ArrayList();
        
	while(rs.next()){
            Usuario usuario = new Usuario();
            usuario.setId(rs.getInt("ID_USUARIO"));
            usuario.setLogin(rs.getString("LOGIN"));
            usuario.setIsAdmin(rs.getBoolean("ADMIN"));
            usuario.setDataCriacao(rs.getDate("DATA_CRIACAO"));
            if(hash)
                usuario.setHash(rs.getString("PASSWORD"));
            listaUsuario.add(usuario);
	}
        
	return listaUsuario;
    }
    
    @Override
    public List<Usuario> listWithParams(Object object){
        return new ArrayList<>();
    }
}