/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.dao.impl;

import cc20152.sisufal.dao.IBaseDAO;
import cc20152.sisufal.db.Conexao;
import cc20152.sisufal.models.BolsaProjeto;
import cc20152.sisufal.models.Discente;
import cc20152.sisufal.models.GrupoProjeto;
import cc20152.sisufal.models.InstituicaoFinanciadora;
import cc20152.sisufal.models.Projeto;
import cc20152.sisufal.models.Servidor;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anderson
 */
public class GrupoProjetoDAO  implements IBaseDAO {
    
    private Connection conexao;

    @Override
    public String save(Object object) {
        conexao = Conexao.getConexao();
        
        String sql = "INSERT INTO grupoprojeto(id_projeto, id_servidor) "
                    + "VALUES (?,?)";   
       
        try{
            PreparedStatement st = conexao.prepareStatement(sql);
            conexao.setAutoCommit(false);
            
            for(Servidor s : ((GrupoProjeto) object).getListaServidores()) {
                st.setInt(1, ((GrupoProjeto) object).getProjeto().getId());
                st.setInt(2, s.getId());

                st.addBatch();
            }
            
            st.executeBatch();
            conexao.commit();
        } catch (Exception e){
            e.printStackTrace();
            return "ERROR";
        } finally {
            Conexao.desconectar();
        }
        
        return "OK";
    }

    @Override
    public String update(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String delete(Object object) {
        this.conexao = Conexao.getConexao();
        String sql = "DELETE FROM grupoprojeto WHERE id_projeto = ? AND id_servidor = ?";   
        
        try{
            PreparedStatement st = conexao.prepareStatement(sql);
            conexao.setAutoCommit(false);
            
            for(Servidor s : ((GrupoProjeto) object).getListaServidores()) {
                st.setInt(1, ((GrupoProjeto) object).getProjeto().getId());
                st.setInt(2, s.getId());
                
                st.addBatch();
            }
            
            st.executeBatch();
            conexao.commit();
        } catch (Exception e){
            e.printStackTrace();
            return "ERROR";
        } finally {
            Conexao.desconectar();
        }
        
        return "OK";
    }

    @Override
    public List<?> listAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<?> listWithParams(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
