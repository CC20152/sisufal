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
    
    public Integer saveMovimentacao(Movimentacao movimentacao){
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
    /*
    * Eu escrevi isso sem ter a certeza se é um bom método
    */
    @Override
    public List<Patrimonio> listWithParams(Object object){
        ArrayList<Patrimonio> listaPatrimonio = new ArrayList();
        this.conn = Conexao.getConexao();
        Patrimonio patrimonio = (Patrimonio) object;
        String sql = "SELECT d.id_patrimonios, d.nome_patrimonio, c.nome as NOME_SALA, d.numero_patrimonio, d.id_sala FROM salas c, patrimoniopermanente d WHERE d.id_sala = c.id_sala";
        
        try{
            
            
            int i = 1;
            /*
            if(patrimonio.getBloco() != null){
                sql += " AND c.id_bloco = ?";
            }
            
            if(patrimonio.getSala() != null){
                sql += " AND c.id_sala = ?";
            }
            */
            if(patrimonio.getNome() != null){
                sql += " AND upper(d.nome_patrimonio) LIKE upper(?)";
            }

            if(patrimonio.getNumero() != null){
                sql += " AND upper(d.numero_patrimonio) LIKE upper(?)";
            }
            
            PreparedStatement st = this.conn.prepareStatement(sql);
            ResultSet rs;
            
            if(patrimonio.getBloco() != null){
                st.setInt(i, patrimonio.getBloco());
                i++;
            }
            /*
            if(patrimonio.getSala() != null){
                st.setInt(i, patrimonio.getSala());
                i++;
            }
            */
            if(patrimonio.getNome() != null){
                st.setString(i, "%" + patrimonio.getNome() + "%");
                i++;
            }
            
            rs = st.executeQuery();
            listaPatrimonio = mapearResultSet(rs);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return listaPatrimonio;
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
