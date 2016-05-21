/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.dao.impl;

import cc20152.sisufal.dao.IBaseDAO;
import cc20152.sisufal.db.Conexao;
import cc20152.sisufal.models.InstituicaoFinanciadora;
import cc20152.sisufal.models.Projeto;
import cc20152.sisufal.models.Servidor;
import java.sql.Connection;
import java.sql.Date;
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
public class ProjetoDAO implements IBaseDAO {
    
    private Connection conexao;

    @Override
    public String save(Object object) {
        int idSalvo = -1;
        
        conexao = Conexao.getConexao();
        String sql = "INSERT INTO projeto(titulo, data_inicio, data_fim, "
                    + "id_coordenador, tipo, id_instituicao_financiadora) "
                    + "VALUES (?,?,?,?,?,?)";   
        try{
            PreparedStatement st = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            st.setString(1, ((Projeto) object).getTitulo());
            st.setDate(2, new Date(((Projeto) object).getDataInicio().getTime()));
            st.setDate(3, new Date(((Projeto) object).getDataFim().getTime()));
            st.setInt(4, ((Projeto) object).getServidorCoordenador().getId());
            st.setString(5, ((Projeto) object).getTipo());
            st.setInt(6, ((Projeto) object).getFinanciadora().getId());
            
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
        String sql = "UPDATE projeto SET titulo = ?, data_inicio = ?, data_fim = ?, "
                + "id_coordenador = ?, tipo = ?, id_instituicao_financiadora = ? "
                + "WHERE id_projeto = ?";   
        try{
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, ((Projeto) object).getTitulo());
            st.setDate(2, new Date(((Projeto) object).getDataInicio().getTime()));
            st.setDate(3, new Date(((Projeto) object).getDataFim().getTime()));
            st.setInt(4, ((Projeto) object).getServidorCoordenador().getId());
            st.setString(5, ((Projeto) object).getTipo());
            st.setInt(6, ((Projeto) object).getFinanciadora().getId());
            st.setInt(7, ((Projeto) object).getId());
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
        String sql = "DELETE FROM projeto WHERE id_projeto = ?";   
        try{
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1, ((Projeto) object).getId());
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
    public List<Projeto> listAll() {
        ArrayList<Projeto> listaProjetos = new ArrayList();
        this.conexao = Conexao.getConexao();
        String sql = "SELECT p.*, s.nome as nome_servidor, i.nome as nome_financiadora "
                + "FROM projeto p, servidor s, instituicaofinanciadora i "
                + "WHERE p.id_coordenador = s.id_servidor "
                + "AND p.id_instituicao_financiadora = i.id_instituicao_financiadora";
        
        try{
            PreparedStatement st = this.conexao.prepareStatement(sql);
            ResultSet rs;

            rs = st.executeQuery();
            listaProjetos = mapearResultSet(rs);
            Conexao.desconectar();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return listaProjetos;
    }

    @Override
    public List<?> listWithParams(Object object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private ArrayList<Projeto> mapearResultSet(ResultSet rs) throws SQLException{
		
	ArrayList<Projeto> listaProjeto = new ArrayList();
        
	while(rs.next()){
            Projeto projeto = new Projeto();
            projeto.setId(rs.getInt("id_projeto"));
            projeto.setTitulo(rs.getString("titulo"));
            projeto.setDataInicio(new Date(rs.getDate("data_inicio").getTime()));
            projeto.setDataFim(new Date(rs.getDate("data_fim").getTime()));
            projeto.setTipo(rs.getString("tipo"));
            
            Servidor servidor = new Servidor();
            servidor.setId(rs.getInt("id_coordenador"));
            servidor.setNome(rs.getString("nome_servidor"));
            projeto.setServidorCoordenador(servidor);
            
            InstituicaoFinanciadora financiadora = new InstituicaoFinanciadora();
            financiadora.setId(rs.getInt("id_instituicao_financiadora"));
            financiadora.setNome(rs.getString("nome_financiadora"));
            projeto.setFinanciadora(financiadora);
            
            listaProjeto.add(projeto);
	}
        
	return listaProjeto;
    }
    
}
