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
 * @author Gabriel Fabrício
 */
public class PatrimonioPermanenteDAO implements IBaseDAO {
    
    private Connection conn;
    
    @Override
    public String save(Object object){
        this.conn = Conexao.getConexao();
        String sql = "INSERT INTO patrimoniopermanente(nome_patrimonio, numero_patrimonio, id_bloco, id_sala) VALUES(?, ?, ?, ?)";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, ((Patrimonio) object).getNome());
            st.setString(2, ((Patrimonio) object).getNumero());
            st.setInt(3, ((Patrimonio) object).getBloco());
            st.setInt(4, ((Patrimonio) object).getSala());
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
        String sql = "UPDATE patrimoniopermanente SET nome_patrimonio = ?, numero_patrimonio = ?, id_bloco = ?, id_sala = ? WHERE id_patrimonio = ?";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, ((Patrimonio) object).getNome());
            st.setString(2, ((Patrimonio) object).getNumero());
            st.setInt(3, ((Patrimonio) object).getBloco());
            st.setInt(4, ((Patrimonio) object).getSala());
            st.setInt(5, ((Patrimonio) object).getId());
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
        String sql = "SELECT * FROM patrimoniopermanente";
        try{
            PreparedStatement st = this.conn.prepareStatement(sql);
            ResultSet rs;

            rs = st.executeQuery();
            listaPatrimonio = mapearResultSet(rs);
            Conexao.desconectar();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return listaPatrimonio;
    }
    /*
    @Override
    public List<InstituicaoFinanciadora> listAll() {
        ArrayList<InstituicaoFinanciadora> listaInstituicaoFinanciadora = new ArrayList();
        this.conexao = Conexao.getConexao();
        String sql = "SELECT * FROM instituicaofinanciadora";
        
        try{
            PreparedStatement st = this.conexao.prepareStatement(sql);
            ResultSet rs;

            rs = st.executeQuery();
            listaInstituicaoFinanciadora = mapearResultSet(rs);
            
            Conexao.desconectar();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return listaInstituicaoFinanciadora;
    }
    */
    /*
    * Eu escrevi isso sem ter a certeza se é um bom método
    */
    @Override
    public List<Patrimonio> listWithParams(Object object){
        ArrayList<Patrimonio> listaPatrimonio = new ArrayList();
        this.conn = Conexao.getConexao();
        Patrimonio patrimonio = (Patrimonio) object;
        String sql = "SELECT d.id_patrimonios, d.nome_patrimonio, c.nome_patrimonio as NOME_BLOCO, d.numero_patrimonio, d.id_sala FROM salas c, d.id_bloco FROM blocos c, patrimoniopermanente d WHERE d.id_bloco = c.id_bloco";
        
        try{
            
            
            int i = 1;
            
            if(patrimonio.getBloco() != null){
                sql += " AND c.id_bloco = ?";
            }
        
            if(patrimonio.getSala() != null){
                sql += " AND c.id_sala = ?";
            }

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
        
            if(patrimonio.getSala() != null){
                st.setInt(i, patrimonio.getSala());
                i++;
            }

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
            patrimonio.setNome(rs.getString("NOME"));
            patrimonio.setNumero(rs.getString("NUMERO"));
            patrimonio.setBloco(rs.getInt("ID_BLOCO"));
            patrimonio.setSala(rs.getInt("ID_SALA"));
            listaPatrimonio.add(patrimonio);
    }
        
    return listaPatrimonio;
    }
}
