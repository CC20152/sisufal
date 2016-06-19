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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 *
 * @author Gabriel Fabrício
 */
public class PatrimonioPermanenteDAO implements IBaseDAO {
    
    private Connection conn;
    
    @Override
    public String save(Object object){
        this.conn = Conexao.getConexao();
        String sql = "INSERT INTO patrimoniopermanente(nome_patrimonio, numero_patrimonio, id_movimentacao) VALUES(?, ?, ?)";  
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, ((Patrimonio) object).getNome());
            st.setString(2, ((Patrimonio) object).getNumero());
            st.setInt(3, ((Patrimonio) object).getUltimaMovimentacao().getId());
            st.execute();
        }catch(Exception ex){
            ex.printStackTrace();
            return "ERROR";
        }
        return "OK";
    }
    
    public Integer savePatrimonio(Patrimonio object){
        this.conn = Conexao.getConexao();
        String sql = "INSERT INTO patrimoniopermanente(nome_patrimonio, numero_patrimonio) VALUES(?, ?)";  
        Integer id = -1;
        try{
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, ((Patrimonio) object).getNome());
            st.setString(2, ((Patrimonio) object).getNumero());
            ResultSet rs;
            st.executeUpdate();
            rs = st.getGeneratedKeys();            
            if(rs.next())
                id = rs.getInt(1);
        }catch(Exception ex){
            ex.printStackTrace();
            return -1;
        }
        return id;
    }
    
    public Integer saveMovimentacaoPermanente(Movimentacao movimentacao){
        this.conn = Conexao.getConexao();
        String sql = "INSERT INTO movimentacaopermanente(id_sala, id_patrimonio, data) VALUES(?, ?, ?)";   
        Integer id = -1;
        try{
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, ((Movimentacao) movimentacao).getSala());
            st.setInt(2, ((Movimentacao) movimentacao).getPatrimonio());
            Calendar cal = Calendar.getInstance();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
            st.setTimestamp(3, timestamp);
            ResultSet rs;
            st.executeUpdate();
            rs = st.getGeneratedKeys();            
            if(rs.next())
                id = rs.getInt(1);
        }catch(Exception ex){
            ex.printStackTrace();
            return -1;
        }
        return id;
    }
    
    @Override
    public String update(Object object){
        this.conn = Conexao.getConexao();
        String sql = "UPDATE patrimoniopermanente SET nome_patrimonio = ?, numero_patrimonio = ?, id_movimentacao = ? WHERE id_patrimonio = ?";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, ((Patrimonio) object).getNome());
            st.setString(2, ((Patrimonio) object).getNumero());
            st.setInt(3, ((Patrimonio) object).getUltimaMovimentacao().getId());
            st.setInt(4, ((Patrimonio) object).getId());
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
        String sql = "DELETE FROM patrimoniopermanente WHERE id_patrimonio = ?";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, ((Patrimonio) object).getId());
            st.execute();
        }catch(Exception ex){
            ex.printStackTrace();
            return "ERROR";
        }
        return "OK";
    }
    
    @Override
    public List<Patrimonio> listAll(){
        ArrayList<Patrimonio> listaPatrimonio = new ArrayList();
        this.conn = Conexao.getConexao();
        String sql = "SELECT * FROM patrimoniopermanente "
                + "INNER JOIN movimentacaopermanente c ON c.id_patrimonio = patrimoniopermanente.id_patrimonio "
                + "INNER JOIN sala ON sala.id_sala = c.id_sala "
                + "INNER JOIN bloco ON bloco.id_bloco = sala.id_bloco "
                + "ORDER BY c.id_movimentacao DESC ";
        try{
            PreparedStatement st = this.conn.prepareStatement(sql);
            ResultSet rs;

            rs = st.executeQuery();
            while(rs.next()){
                Patrimonio patrimonio = new Patrimonio();
                patrimonio.setId(rs.getInt("patrimoniopermanente.ID_PATRIMONIO"));
                patrimonio.setNome(rs.getString("patrimoniopermanente.NOME_PATRIMONIO"));
                patrimonio.setNumero(rs.getString("patrimoniopermanente.NUMERO_PATRIMONIO"));
                patrimonio.setSala(rs.getInt("c.ID_SALA"));
                patrimonio.setNomeSala(rs.getString("sala.NOME"));
                patrimonio.setBloco(rs.getInt("bloco.ID_BLOCO"));
                patrimonio.setNomeBloco(rs.getString("bloco.NOME"));
                listaPatrimonio.add(patrimonio);
            }
            Conexao.desconectar();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return listaPatrimonio;
    }
    
    @Override
    public List<Patrimonio> listWithParams(Object object){
        ArrayList<Patrimonio> lista = new ArrayList<Patrimonio>();
        this.conn = Conexao.getConexao();
        
        String tipo = ((HashMap) object).get("tipo").toString().toLowerCase();
        String pesquisa = ((HashMap) object).get("texto").toString();
        String sql;
        //System.out.println(tipo);
        if(tipo.equals("nome") ||tipo.equals("numero") ){
            sql = "SELECT * FROM patrimoniopermanente "
                + "INNER JOIN movimentacaopermanente c ON c.id_patrimonio = patrimoniopermanente.id_patrimonio "
                + "INNER JOIN sala ON sala.id_sala = c.id_sala "
                + "INNER JOIN bloco ON bloco.id_bloco = sala.id_bloco "
                + "WHERE patrimoniopermanente."+tipo+"_patrimonio LIKE '%"+pesquisa+"%'";
        }else if(tipo.equals("bloco")){
             sql = "SELECT * FROM patrimoniopermanente "
                + "INNER JOIN movimentacaopermanente c ON c.id_patrimonio = patrimoniopermanente.id_patrimonio "
                + "INNER JOIN sala ON sala.id_sala = c.id_sala "
                + "INNER JOIN bloco ON bloco.id_bloco = sala.id_bloco "
                + "WHERE bloco.nome LIKE '%"+pesquisa+"%'";
        }else{
             sql = "SELECT * FROM patrimoniopermanente "
                + "INNER JOIN movimentacaopermanente c ON c.id_patrimonio = patrimoniopermanente.id_patrimonio "
                + "INNER JOIN sala ON sala.id_sala = c.id_sala "
                + "INNER JOIN bloco ON bloco.id_bloco = sala.id_bloco "
                + "WHERE sala.nome LIKE '%"+pesquisa+"%'";
        }
       
        try{
            PreparedStatement st = this.conn.prepareStatement(sql);
            ResultSet rs;

            rs = st.executeQuery();
            while(rs.next()){
                Patrimonio patrimonio = new Patrimonio();
                patrimonio.setId(rs.getInt("patrimoniopermanente.ID_PATRIMONIO"));
                patrimonio.setNome(rs.getString("patrimoniopermanente.NOME_PATRIMONIO"));
                patrimonio.setNumero(rs.getString("patrimoniopermanente.NUMERO_PATRIMONIO"));
                patrimonio.setSala(rs.getInt("c.ID_SALA"));
                patrimonio.setNomeSala(rs.getString("sala.NOME"));
                patrimonio.setBloco(rs.getInt("bloco.ID_BLOCO"));
                patrimonio.setNomeBloco(rs.getString("bloco.NOME"));
                lista.add(patrimonio);
            }
            Conexao.desconectar();
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
        return lista;
    }
    
    
    public ArrayList<Patrimonio> mapearResultSet(ResultSet rs) throws SQLException{
        
    ArrayList<Patrimonio> listaPatrimonio = new ArrayList();
        
    while(rs.next()){
            Patrimonio patrimonio = new Patrimonio();
            patrimonio.setId(rs.getInt("ID_PATRIMONIO"));
            patrimonio.setNome(rs.getString("NOME_PATRIMONIO"));
            patrimonio.setNumero(rs.getString("NUMERO_PATRIMONIO"));
            patrimonio.setSala(rs.getInt("ID_MOVIMENTACAO"));
            listaPatrimonio.add(patrimonio);
    }
        
    return listaPatrimonio;
    }
}
