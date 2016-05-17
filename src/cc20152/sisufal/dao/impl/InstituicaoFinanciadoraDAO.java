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
        conexao = Conexao.getConexao();
        String sql = "INSERT INTO instituicaofinanciadora(nome, codigo) "
                    + "VALUES (?,?)";   
        try{
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, ((InstituicaoFinanciadora) object).getNome());
            st.setString(2, ((InstituicaoFinanciadora) object).getCodigo());
            
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
    public List<?> listWithParams(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
