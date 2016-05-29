/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.dao.impl;

import cc20152.sisufal.dao.IBaseDAO;
import cc20152.sisufal.db.Conexao;
import java.util.List;
import cc20152.sisufal.models.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Dayvson
 */
public class CursoDAO implements IBaseDAO {
    
    private Connection conn;
    
    @Override
    public String save(Object object){
        this.conn = Conexao.getConexao();
        String sql = "INSERT INTO cursos(nome, codigo) VALUES(?, ?)";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, ((Curso) object).getNome());
            st.setString(2, ((Curso) object).getCodigo());
            st.execute();
        }catch(Exception ex){
            return "ERROR";
        }
        return "OK";
    }
    
    @Override
    public String update(Object object){
        this.conn = Conexao.getConexao();
        String sql = "UPDATE cursos SET nome = ?, codigo = ? WHERE id_curso = ?";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, ((Curso) object).getNome());
            st.setString(2, ((Curso) object).getCodigo());
            st.setInt(3, ((Curso) object).getId());
            st.execute();
        }catch(Exception ex){
            return "ERROR";
        }
        return "OK";
    }
    
    @Override
    public String delete(Object object){
        this.conn = Conexao.getConexao();
        String sql = "DELETE FROM cursos WHERE id_curso = ?";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, ((Curso) object).getId());
            st.execute();
        }catch(Exception ex){
            return "ERROR";
        }
        return "OK";
    }
    
    @Override
    public List<Curso> listAll(){
        ArrayList<Curso> listaCurso = new ArrayList();
        this.conn = Conexao.getConexao();
        String sql = "SELECT * FROM cursos";
        
        try{
            PreparedStatement st = this.conn.prepareStatement(sql);
            ResultSet rs;

            rs = st.executeQuery();
            listaCurso = mapearResultSet(rs);
            //conexao.close();
        }catch(Exception ex){
        }
        return listaCurso;
    }
    
    @Override
    public List<Curso> listWithParams(Object object){
        ArrayList<Curso> listaCurso = new ArrayList();
        
        this.conn = Conexao.getConexao();
        Curso curso = (Curso) object;
        String sql = "SELECT * FROM cursos c WHERE 1 = 1";
        
        try {
            
            int i = 1;
            
            if(curso.getNome() != null){
                sql += " AND UPPER(c.nome) LIKE UPPER(?)";
            }
        
            if(curso.getCodigo() != null){
                sql += " AND UPPER(c.codigo) LIKE UPPER(?)";
            }

            PreparedStatement st = this.conn.prepareStatement(sql);
            ResultSet rs;
            
            if(curso.getNome() != null){
                st.setString(i, "%" + curso.getNome() + "%");
                i++;
            }
        
            if(curso.getCodigo() != null){
                st.setString(i, "%" + curso.getCodigo() + "%");
                i++;
            }

            rs = st.executeQuery();
            listaCurso = mapearResultSet(rs);
        }catch(Exception ex){}
        
        return listaCurso;
    }
    
    public ArrayList<Curso> mapearResultSet(ResultSet rs) throws SQLException{
		
	ArrayList<Curso> listaCurso = new ArrayList();
        
	while(rs.next()){
            Curso curso = new Curso();
            curso.setId(rs.getInt("ID_CURSO"));
            curso.setNome(rs.getString("NOME"));
            curso.setCodigo(rs.getString("CODIGO"));
            listaCurso.add(curso);
	}
        
	return listaCurso;
    }
}
