/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.dao.impl;

import cc20152.sisufal.dao.IBaseDAO;
import cc20152.sisufal.db.Conexao;
import cc20152.sisufal.models.Concurso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dayvson
 */
public class ConcursoDAO implements IBaseDAO{

    private Connection conn;
    
    @Override
    public String save(Object object) {
        this.conn = Conexao.getConexao();
        String sql = "INSERT INTO concurso(numero_edital, area_estudo, id_supervisor, data_inicio, data_fim, modalidade) VALUES(?, ?, ?, ?, ?, ?)";   
        ResultSet rs;
        int codigo = -1;
        try{
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, ((Concurso) object).getNumeroEdital());
            st.setString(2, ((Concurso) object).getAreaEstudo());
            st.setInt(3, ((Concurso) object).getSupervisor().getId());
            st.setDate(4, new java.sql.Date(((Concurso) object).getDataInicio().getTime()));
            st.setDate(5, new java.sql.Date(((Concurso) object).getDataFim().getTime()));
            st.setString(6, ((Concurso) object).getModalidade());
            st.executeUpdate();
            rs = st.getGeneratedKeys();
            if(rs.next())
                codigo = rs.getInt(1);
        }catch(Exception ex){
            ex.printStackTrace();
            return "ERROR";
        }
        return codigo + " - OK";
    }

    @Override
    public String update(Object object) {
        this.conn = Conexao.getConexao();
        String sql = "UPDATE concurso SET numero_edital = ?, area_estudo = ?, id_supervisor = ?, data_inicio = ?, data_fim = ?, modalidade = ? WHERE id_concurso = ?";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, ((Concurso) object).getNumeroEdital());
            st.setString(2, ((Concurso) object).getAreaEstudo());
            st.setInt(3, ((Concurso) object).getSupervisor().getId());
            st.setDate(4, new java.sql.Date(((Concurso) object).getDataInicio().getTime()));
            st.setDate(5, new java.sql.Date(((Concurso) object).getDataFim().getTime()));
            st.setString(6, ((Concurso) object).getModalidade());
            st.setInt(7, ((Concurso) object).getId());
            st.execute();
        }catch(Exception ex){
            return "ERROR";
        }
        return "OK";
    }

    @Override
    public String delete(Object object) {
    this.conn = Conexao.getConexao();
        String sql = "DELETE FROM concurso WHERE id_concurso = ?";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, ((Concurso) object).getId());
            st.execute();
        }catch(Exception ex){
            ex.printStackTrace();
            return "ERROR";
        }
        return "OK";}

    @Override
    public List<Concurso> listAll() {
        ArrayList<Concurso> listaConcurso = new ArrayList<>();
        this.conn = Conexao.getConexao();
        String sql = "SELECT c.*, s.id_servidor, s.nome, s.siape FROM concurso c, servidor s WHERE c.id_supervisor = s.id_servidor";
        
        try{
            PreparedStatement st = this.conn.prepareStatement(sql);
            ResultSet rs;

            rs = st.executeQuery();
            listaConcurso = mapearResultSet(rs);
            //conexao.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return listaConcurso;
    }

    @Override
    public List<Concurso> listWithParams(Object object) {
        ArrayList<Concurso> listaConcurso = new ArrayList();
        this.conn = Conexao.getConexao();
        Concurso concurso = (Concurso) object;
        String sql = "SELECT c.*, s.id_servidor, s.nome, s.siape FROM concurso c, servidor s WHERE c.id_supervisor = s.id_servidor";
        
        try{
            int i = 1;
            
            if(concurso.getNumeroEdital() != null){
                sql += " AND UPPER(c.numero_edital) LIKE UPPER(?)";
            }
        
            if(concurso.getDataInicio() != null){
                sql += " AND c.data_inicio = ?";
            }

            if(concurso.getDataFim() != null){
                sql += " AND c.data_fim = ?";
            }
            
            PreparedStatement st = this.conn.prepareStatement(sql);
            ResultSet rs;
            
            if(concurso.getNumeroEdital() != null){
                st.setString(i, concurso.getNumeroEdital());
                i++;
            }
        
            if(concurso.getDataInicio() != null){
                st.setDate(i, new java.sql.Date(concurso.getDataInicio().getTime()));
                i++;
            }

            if(concurso.getDataFim() != null){
                st.setDate(i, new java.sql.Date(concurso.getDataFim().getTime()));
                i++;
            }
            
            rs = st.executeQuery();
            listaConcurso = mapearResultSet(rs);
        }catch(Exception ex){}
        return listaConcurso;
    }
    
     public ArrayList<Concurso> mapearResultSet(ResultSet rs) throws SQLException{
		
	ArrayList<Concurso> listaConcurso = new ArrayList();
        
	while(rs.next()){
            Concurso concurso = new Concurso();
            concurso.setId(rs.getInt("ID_CONCURSO"));
            concurso.setNumeroEdital(rs.getString("NUMERO_EDITAL"));
            concurso.setAreaEstudo(rs.getString("AREA_ESTUDO"));
            concurso.getSupervisor().setId(rs.getInt("ID_SUPERVISOR"));
            concurso.getSupervisor().setNome(rs.getString("NOME"));
            concurso.setDataInicio(rs.getDate("DATA_INICIO"));
            concurso.setDataFim(rs.getDate("DATA_FIM"));
            concurso.setModalidade(rs.getString("MODALIDADE"));
            listaConcurso.add(concurso);
	}
        
	return listaConcurso;
    }
    
}
