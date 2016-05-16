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
public class PatrimonioDAO implements IBaseDAO {
    
    private Connection conn;
    
    @Override
    public String save(Object object){
        this.conn = Conexao.getConexao();
        String sql = "INSERT INTO patrimonios(nome, numero, id_bloco, id_sala) VALUES(?, ?, ?, ?)";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, ((Patrimonio) object).getNome());
            st.setString(2, ((Patrimonio) object).getNumero());
            st.setInt(3, ((Patrimonio) object).getBloco().getId());
            st.setInt(4, ((Patrimonio) object).getSala().getId());
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
        String sql = "UPDATE patrimonios SET nome = ?, numero = ?, id_bloco = ?, id_sala = ? WHERE id_patrimonio = ?";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, ((Patrimonio) object).getNome());
            st.setString(2, ((Patrimonio) object).getNumero());
            st.setInt(3, ((Patrimonio) object).getBloco().getId());
            st.setInt(4, ((Patrimonio) object).getSala().getId());
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
        String sql = "DELETE FROM patrimonio WHERE id_patrimonio = ?";   
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
        String sql = "SELECT d.id_patrimonio, d.nome, c.nome as NOME_BLOCO, d.numero, d.id_sala FROM patrimonio d, salas c WHERE d.id_sala = c.id_sala, d.id_bloco FROM patrimonio d, blocos c WHERE d.id_bloco = c.id_bloco";
        
        try{
            PreparedStatement st = this.conn.prepareStatement(sql);
            ResultSet rs;

            rs = st.executeQuery();
            listaPatrimonio = mapearResultSet(rs);
            //conexao.close();
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
        String sql = "SELECT d.id_patrimonios, d.nome, c.nome as NOME_BLOCO, d.numero, d.id_sala FROM salas c, d.id_bloco FROM blocos c, patrimonios d WHERE d.id_bloco = c.id_bloco";
        
        try{
            
            
            int i = 1;
            
            if(patrimonio.getBloco().getId() != null){
                sql += " AND c.id_bloco = ?";
            }
        
            if(patrimonio.getSala().getId() != null){
                sql += " AND c.id_sala = ?";
            }

            if(patrimonio.getNome() != null){
                sql += " AND upper(d.nome) LIKE upper(?)";
            }

            if(patrimonio.getNumero() != null){
                sql += " AND upper(d.numero) LIKE upper(?)";
            }
            
            PreparedStatement st = this.conn.prepareStatement(sql);
            ResultSet rs;
            
            if(patrimonio.getBloco().getId() != null){
                st.setInt(i, patrimonio.getBloco().getId());
                i++;
            }
        
            if(patrimonio.getSala().getId() != null){
                st.setInt(i, patrimonio.getSala().getId());
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
            patrimonio.setNumero(rs.getString("CARGA_HORARIA"));
            patrimonio.getBloco().setId(rs.getInt("ID_BLOCO"));
            patrimonio.getBloco().setNome(rs.getString("NOME_BLOCO"));
            patrimonio.getSala().setId(rs.getInt("ID_SALA"));
            patrimonio.getSala().setNome(rs.getString("NOME_SALA"));
            listaPatrimonio.add(patrimonio);
    }
        
    return listaPatrimonio;
    }
}
