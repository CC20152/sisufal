/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.dao.impl;

import cc20152.sisufal.dao.IBaseDAO;
import cc20152.sisufal.db.Conexao;
import cc20152.sisufal.models.BancaConcurso;
import cc20152.sisufal.models.Concurso;
import cc20152.sisufal.models.Servidor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dayvson
 */
public class BancaConcursoDAO implements IBaseDAO {
    private Connection conn;
    
    @Override
    public String save(Object object){
        conn = Conexao.getConexao();
        
        String sql = "INSERT INTO bancaconcurso(id_concurso, id_servidor) VALUES(?, ?)";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            
            for(Servidor servidor : ((BancaConcurso) object).getListaServidores()){
                st.setInt(1, ((BancaConcurso) object).getConcurso().getId());
                st.setInt(2, servidor.getId());
                st.addBatch();
            }
           
            st.executeBatch();
            conn.commit();
        }catch(Exception ex){
            ex.printStackTrace();
            return "ERROR";
        }
        return "OK";
    }
    
    @Override
    public String update(Object object){
        return "OK";
    }
    
    @Override
    public String delete(Object object){
        this.conn = Conexao.getConexao();
        String sql = "DELETE FROM bancaconcurso WHERE id_banca_concurso = ?";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, ((BancaConcurso) object).getId());
            st.execute();
        }catch(Exception ex){
            ex.printStackTrace();
            return "ERROR";
        }
        return "OK";
    }
    
    @Override
    public List<BancaConcurso> listAll(){
        ArrayList<BancaConcurso> listaBancaConcurso = new ArrayList();
        this.conn = Conexao.getConexao();
        String sql = "SELECT * FROM bancaconcurso b, servidor s WHERE b.id_servidor = s.id_servidor";
        
        try{
            PreparedStatement st = this.conn.prepareStatement(sql);
            ResultSet rs;

            rs = st.executeQuery();
            listaBancaConcurso = mapearResultSet(rs);
            //conexao.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return listaBancaConcurso;
    }
    
     private ArrayList<BancaConcurso> mapearResultSet(ResultSet rs) throws SQLException{
		
	ArrayList<BancaConcurso> listaBancaConcurso = new ArrayList();
        
        BancaConcurso bancaConcurso = new BancaConcurso();
	
        while(rs.next()){
            bancaConcurso.setId(rs.getInt("ID_BANCA_CONCURSO"));
            
            Servidor servidor = new Servidor();
            servidor.setId(rs.getInt("ID_SERVIDOR"));
            servidor.setSiape(rs.getString("SIAPE"));
            servidor.setNome(rs.getString("NOME"));
            servidor.setCargo(rs.getString("CARGO"));
            
            bancaConcurso.getListaServidores().add(servidor);
	}
        
        listaBancaConcurso.add(bancaConcurso);
	return listaBancaConcurso;
    }

    @Override
    public List<BancaConcurso> listWithParams(Object object) {
        ArrayList<BancaConcurso> listaBancaConcurso = new ArrayList();
        this.conn = Conexao.getConexao();
        String sql = "SELECT * FROM bancaconcurso b, servidor s WHERE b.id_servidor = s.id_servidor AND b.id_concurso = ?";
        
        try{
            PreparedStatement st = this.conn.prepareStatement(sql);
            ResultSet rs;
            st.setInt(1, ((Concurso) object).getId());
            rs = st.executeQuery();
            listaBancaConcurso = mapearResultSet(rs);
            //conexao.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return listaBancaConcurso;
    }
}
