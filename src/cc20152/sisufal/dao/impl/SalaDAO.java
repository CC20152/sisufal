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
public class SalaDAO implements IBaseDAO {
    
    private Connection conn;
    
    @Override
    public String save(Object object){
        this.conn = Conexao.getConexao();
        String sql = "INSERT INTO sala(nome, codigo, id_bloco, nome_bloco) VALUES(?, ?, ?, ?)";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, ((Sala) object).getNome());
            st.setString(2, ((Sala) object).getCodigo());
            st.setInt(3, ((Sala) object).getBloco().getId());
            st.setString(4, ((Sala) object).getBloco().getNome());
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
        String sql = "UPDATE sala SET nome = ?, codigo = ?, id_bloco = ?, nome_bloco = ? WHERE id_sala = ?";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, ((Sala) object).getNome());
            st.setString(2, ((Sala) object).getCodigo());
            st.setInt(3, ((Sala) object).getBloco().getId());
            st.setString(4, ((Sala) object).getBloco().getNome());
            st.setInt(5, ((Sala) object).getId());
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
        String sql = "DELETE FROM sala WHERE id_sala = ?";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, ((Sala) object).getId());
            st.execute();
        }catch(Exception ex){
            ex.printStackTrace();
            return "ERROR";
        }
        return "OK";
    }
    
    @Override
    public List<Sala> listAll(){
        ArrayList<Sala> listaSala = new ArrayList();
        this.conn = Conexao.getConexao();
        String sql = "SELECT * FROM sala "
                    + "INNER JOIN bloco ON bloco.id_bloco = sala.id_bloco ";

        try{
            PreparedStatement st = this.conn.prepareStatement(sql);
            ResultSet rs;

            rs = st.executeQuery();
            listaSala = mapearResultSet(rs);
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return listaSala;
    }
    
    @Override
    public List<Sala> listWithParams(Object object){
        ArrayList<Sala> lista = new ArrayList<Sala>();
        this.conn = Conexao.getConexao();
        
        String tipo = ((HashMap) object).get("tipo").toString().toLowerCase();
        String pesquisa = ((HashMap) object).get("texto").toString();
        String sql;
        //System.out.println(tipo);
        if(tipo.equals("codigo") ||tipo.equals("nome") ){
            sql = "SELECT * FROM sala "
                    + "INNER JOIN bloco ON bloco.id_bloco = sala.id_bloco "
                    + "WHERE sala."+tipo+" LIKE '%"+pesquisa+"%'";
        }else{
             sql = "SELECT * FROM sala "
                     + "INNER JOIN bloco ON bloco.id_bloco = sala.id_bloco "
                     + "WHERE bloco.nome LIKE '%"+pesquisa+"%'";
        }
       
        try{
            PreparedStatement st = this.conn.prepareStatement(sql);
            ResultSet rs;

            rs = st.executeQuery();
            while(rs.next()){
            Sala sala = new Sala();
            sala.setId(rs.getInt("ID_SALA"));
            sala.setNome(rs.getString("NOME"));
            sala.setCodigo(rs.getString("CODIGO"));
            sala.getBloco().setId(rs.getInt("ID_BLOCO"));
            sala.getBloco().setNome(rs.getString("BLOCO.NOME"));
            lista.add(sala);
            }
            Conexao.desconectar();
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
        return lista;
    }
    
    public ArrayList<Sala> listSalas(Bloco bloco){
        ArrayList<Sala> listaSala = new ArrayList();
        this.conn = Conexao.getConexao();
        String sql = "SELECT * FROM sala "
                + "INNER JOIN bloco c ON c.id_bloco = sala.id_bloco"
                + " WHERE sala.id_bloco = ?";
        
        try{
            PreparedStatement st = this.conn.prepareStatement(sql);
            ResultSet rs;
            st.setInt(1, bloco.getId());
            rs = st.executeQuery();
            listaSala = mapearResultSet(rs);
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return listaSala;
    }
    
    
    public ArrayList<Sala> mapearResultSet(ResultSet rs) throws SQLException{
        
    ArrayList<Sala> listaSala = new ArrayList();
        
    while(rs.next()){
            Sala sala = new Sala();
            sala.setId(rs.getInt("ID_SALA"));
            sala.setNome(rs.getString("NOME"));
            sala.setCodigo(rs.getString("CODIGO"));
            sala.getBloco().setId(rs.getInt("ID_BLOCO"));
            sala.getBloco().setNome(rs.getString("BLOCO.NOME"));
            listaSala.add(sala);
    }

    return listaSala;
    }
}
