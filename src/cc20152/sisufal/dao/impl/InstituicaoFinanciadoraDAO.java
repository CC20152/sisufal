/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.dao.impl;

import cc20152.sisufal.dao.IBaseDAO;
import cc20152.sisufal.db.Conexao;
import cc20152.sisufal.models.InstituicaoFinanciadora;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anderson
 */
public class InstituicaoFinanciadoraDAO implements IBaseDAO{

    private Connection conexao;
    
    @Override
    public String save(Object object) {
        int idSalvo = -1;
        
        conexao = Conexao.getConexao();
        String sql = "INSERT INTO instituicaofinanciadora(nome, codigo) "
                    + "VALUES (?,?)";   
        try{
            PreparedStatement st = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, ((InstituicaoFinanciadora) object).getNome());
            st.setString(2, ((InstituicaoFinanciadora) object).getCodigo());
            
            st.execute();
            
            ResultSet rs = st.getGeneratedKeys();
            
            if(rs.next()) {
                idSalvo = rs.getInt(1);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            Conexao.desconectar();
        }
        return String.valueOf(idSalvo);
    }

    @Override
    public String update(Object object) {
        this.conexao = Conexao.getConexao();
        String sql = "UPDATE instituicaofinanciadora SET nome = ?, codigo = ?"
                + "WHERE id_instituicao_financiadora = ?";   
        try{
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, ((InstituicaoFinanciadora) object).getNome());
            st.setString(2, ((InstituicaoFinanciadora) object).getCodigo());
            st.setInt(3, ((InstituicaoFinanciadora) object).getId());
            
            st.execute();
        }catch(Exception ex){
            ex.printStackTrace();
            return "ERROR";
        }finally{
            Conexao.desconectar();
        }
        return "OK";
    }

    @Override
    public String delete(Object object) {
        this.conexao = Conexao.getConexao();
        String sql = "DELETE FROM instituicaofinanciadora WHERE id_instituicao_financiadora = ?";   
        try{
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1, ((InstituicaoFinanciadora) object).getId());
            
            st.execute();
        }catch(Exception ex){
            ex.printStackTrace();
            return "ERROR";
        }finally{
            Conexao.desconectar();
        }
        return "OK";
    }

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

    @Override
    public List<InstituicaoFinanciadora> listWithParams(Object object){
        ArrayList<InstituicaoFinanciadora> listaInstituicoesFinanciadoras = new ArrayList();
        this.conexao = Conexao.getConexao();
        InstituicaoFinanciadora instituicaoFinanciadora = (InstituicaoFinanciadora) object;
        String sql = "SELECT * FROM instituicaofinanciadora WHERE";
        
        try{          
            int i = 1;
            
            if(instituicaoFinanciadora.getCodigo()!= null){
                sql += " codigo LIKE ? ";
            }

            if(instituicaoFinanciadora.getNome() != null){
                if(instituicaoFinanciadora.getCodigo()!= null) sql += "";
                sql += " upper(nome) LIKE upper(?)";
            }
            
            PreparedStatement st = this.conexao.prepareStatement(sql);
            ResultSet rs;
            
            if(instituicaoFinanciadora.getCodigo()!= null){
                st.setString(i, instituicaoFinanciadora.getCodigo());
                i++;
            }

            if(instituicaoFinanciadora.getNome() != null){
                st.setString(i, "%" + instituicaoFinanciadora.getNome() + "%");
                i++;
            }
            
            System.out.println(sql);
            
            rs = st.executeQuery();
            listaInstituicoesFinanciadoras = mapearResultSet(rs);
            
            Conexao.desconectar();
        } catch(Exception ex){
            ex.printStackTrace();
        }
        
        return listaInstituicoesFinanciadoras;
    }
    
    private ArrayList<InstituicaoFinanciadora> mapearResultSet(ResultSet rs) throws SQLException{
		
	ArrayList<InstituicaoFinanciadora> listaInstituicaoFinanciadora = new ArrayList();
        
	while(rs.next()){
            InstituicaoFinanciadora instituicaoFinanciadora = new InstituicaoFinanciadora();
            instituicaoFinanciadora.setId(rs.getInt("id_instituicao_financiadora"));
            instituicaoFinanciadora.setCodigo(rs.getString("codigo"));
            instituicaoFinanciadora.setNome(rs.getString("nome"));
            
            listaInstituicaoFinanciadora.add(instituicaoFinanciadora);
	}
        
	return listaInstituicaoFinanciadora;
    }
}
