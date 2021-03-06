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
import java.util.HashMap;

/**
 *
 * @author Gabriel Fabrício
 */
public class BlocoDAO implements IBaseDAO {
    
    private Connection conn;
    
    @Override
    public String save(Object object){
        this.conn = Conexao.getConexao();
        String sql = "INSERT INTO bloco(nome, codigo) VALUES(?, ?)";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, ((Bloco) object).getNome());
            st.setString(2, ((Bloco) object).getCodigo());
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
        String sql = "UPDATE bloco SET nome = ?, codigo = ? WHERE id_bloco = ?";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, ((Bloco) object).getNome());
            st.setString(2, ((Bloco) object).getCodigo());
            st.setInt(3, ((Bloco) object).getId());
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
        String sql = "DELETE FROM bloco WHERE id_bloco = ?";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, ((Bloco) object).getId());
            st.execute();
        }catch(Exception ex){
            ex.printStackTrace();
            return "ERROR";
        }
        return "OK";
    }
    
    @Override
    public List<Bloco> listAll(){
        ArrayList<Bloco> listaBloco = new ArrayList();
        this.conn = Conexao.getConexao();
        String sql = "SELECT * FROM bloco";
        
        try{
            PreparedStatement st = this.conn.prepareStatement(sql);
            ResultSet rs;

            rs = st.executeQuery();
            listaBloco = mapearResultSet(rs);
            //conexao.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return listaBloco;
    }
    
    /*
    * Eu escrevi isso sem ter a certeza se é um bom método
    
    @Override
    public List<Bloco> listWithParams(Object object){
        ArrayList<Bloco> listaBloco = new ArrayList();
        this.conn = Conexao.getConexao();
        Bloco bloco = (Bloco) object;
        String sql = "SELECT * FROM bloco";
        
        try{
            
            
            int i = 1;

            if(bloco.getNome() != null){
                sql += " AND upper(d.nome) LIKE upper(?)";
            }

            if(bloco.getCodigo() != null){
                sql += " AND upper(d.codigo) LIKE upper(?)";
            }
            
            PreparedStatement st = this.conn.prepareStatement(sql);
            ResultSet rs;

            if(bloco.getNome() != null){
                st.setString(i, "%" + bloco.getNome() + "%");
                i++;
            }
            
            rs = st.executeQuery();
            listaBloco = mapearResultSet(rs);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return listaBloco;
    }
    */
    @Override
    public List<Bloco> listWithParams(Object object){
        ArrayList<Bloco> lista = new ArrayList<>();
        this.conn = Conexao.getConexao();
        
        String tipo = ((HashMap) object).get("tipo").toString().toLowerCase();
        String pesquisa = ((HashMap) object).get("texto").toString();
        String sql;
        //System.out.println(tipo);
        sql = "SELECT * FROM bloco "
            + "WHERE bloco."+tipo+" LIKE '%"+pesquisa+"%'";
       
        try{
            PreparedStatement st = this.conn.prepareStatement(sql);
            ResultSet rs;

            rs = st.executeQuery();
            while(rs.next()){
            Bloco bloco = new Bloco();
            bloco.setId(rs.getInt("ID_BLOCO"));
            bloco.setNome(rs.getString("NOME"));
            bloco.setCodigo(rs.getString("CODIGO"));
            lista.add(bloco);
            }
            Conexao.desconectar();
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
        return lista;
    }
    
    
    public ArrayList<Bloco> mapearResultSet(ResultSet rs) throws SQLException{
		
	ArrayList<Bloco> listaBloco = new ArrayList();
        
	while(rs.next()){
            Bloco bloco = new Bloco();
            bloco.setId(rs.getInt("ID_BLOCO"));
            bloco.setNome(rs.getString("NOME"));
            bloco.setCodigo(rs.getString("CODIGO"));
            listaBloco.add(bloco);
	}

	return listaBloco;
    }
}
