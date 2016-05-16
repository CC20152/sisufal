package cc20152.sisufal.dao.impl;

import cc20152.sisufal.dao.IBaseDAO;
import cc20152.sisufal.db.Conexao;
import cc20152.sisufal.models.Monitoria;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dayvson
 */
public class MonitoriaDAO implements IBaseDAO {
    private Connection conn;
    
    @Override
    public String save(Object object){
        conn = Conexao.getConexao();
        String sql = "INSERT INTO monitoria(id_disciplina, id_orientador, situacao_certificado, data_inicio, data_fim, id_discente, id_periodo) VALUES(?, ?, ?, ?, ?, ?, ?)";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, ((Monitoria) object).getDisciplina().getId());
            st.setInt(2, ((Monitoria) object).getOrientador().getId());
            st.setString(3, ((Monitoria) object).getSitCertificado());
            st.setDate(4, new java.sql.Date(((Monitoria) object).getDataInicio().getTime()));
            st.setDate(5, new java.sql.Date(((Monitoria) object).getDataFim().getTime()));
            st.setInt(6, ((Monitoria) object).getDiscente().getId());
            st.setInt(7, ((Monitoria) object).getPeriodo().getId());
            
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
        String sql = "UPDATE monitoria SET id_disciplina = ?, id_orientador = ?, situacao_certificado = ?, data_fim = ?, data_inicio = ?, id_discente = ?, id_periodo = ? WHERE id_monitoria = ?";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, ((Monitoria) object).getDisciplina().getId());
            st.setInt(2, ((Monitoria) object).getOrientador().getId());
            st.setString(3, ((Monitoria) object).getSitCertificado());
            st.setDate(4, new java.sql.Date(((Monitoria) object).getDataInicio().getTime()));
            st.setDate(5, new java.sql.Date(((Monitoria) object).getDataFim().getTime()));
            st.setInt(6, ((Monitoria) object).getDiscente().getId());
            st.setInt(7, ((Monitoria) object).getPeriodo().getId());
            st.setInt(8, ((Monitoria) object).getId());
            
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
        String sql = "DELETE FROM monitoria WHERE id_monitoria = ?";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, ((Monitoria) object).getId());
            st.execute();
        }catch(Exception ex){
            ex.printStackTrace();
            return "ERROR";
        }
        return "OK";
    }
    
    @Override
    public List<Monitoria> listAll(){
        ArrayList<Monitoria> listaMonitoria = new ArrayList();
        this.conn = Conexao.getConexao();
        String sql = "SELECT d.id_disciplinas, d.nome as NOME_DISCIPLINA, m.*, dis.id_discente, dis.nome AS NOME_DISCENTE, s.id_servidor AS ID_ORIENTADOR, s.siape, s.nome AS NOME_ORIENTADOR, p.id_periodo, p.periodo AS NOME_PERIODO FROM monitoria m, disciplinas d, discentes dis, servidor s, periodo p  WHERE m.id_periodo = p.id_periodo AND d.id_disciplinas = m.id_disciplina AND m.id_discente = dis.id_discente AND s.id_servidor = m.id_orientador";
        
        try{
            PreparedStatement st = this.conn.prepareStatement(sql);
            ResultSet rs;

            rs = st.executeQuery();
            listaMonitoria = mapearResultSet(rs);
            //conexao.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return listaMonitoria;
    }
    
     public ArrayList<Monitoria> mapearResultSet(ResultSet rs) throws SQLException{
		
	ArrayList<Monitoria> listaMonitoria = new ArrayList();
        
	while(rs.next()){
            Monitoria monitoria = new Monitoria();
            monitoria.setId(rs.getInt("ID_MONITORIA"));
            monitoria.setSitCertificado(rs.getString("SITUACAO_CERTIFICADO"));
            monitoria.getDiscente().setId(rs.getInt("ID_DISCENTE"));
            monitoria.getDiscente().setNome(rs.getString("NOME_DISCENTE"));
            monitoria.getOrientador().setId(rs.getInt("ID_ORIENTADOR"));
            monitoria.getOrientador().setNome(rs.getString("NOME_ORIENTADOR"));
            monitoria.getDisciplina().setId(rs.getInt("ID_DISCIPLINA"));
            monitoria.getDisciplina().setNome(rs.getString("NOME_DISCIPLINA"));
            monitoria.getPeriodo().setId(rs.getInt("ID_PERIODO"));
            monitoria.getPeriodo().setNome(rs.getString("NOME_PERIODO"));
            monitoria.setSitCertificado(rs.getString("SITUACAO_CERTIFICADO"));
            monitoria.setDataInicio(rs.getDate("DATA_INICIO"));
            monitoria.setDataFim(rs.getDate("DATA_FIM"));
            listaMonitoria.add(monitoria);
	}
        
	return listaMonitoria;
    }

    @Override
    public List<Monitoria> listWithParams(Object object) {
        ArrayList<Monitoria> listaMonitoria = new ArrayList<>();
        
        this.conn = Conexao.getConexao();
        Monitoria monitoria = (Monitoria) object;
        String sql = "SELECT d.id_disciplinas, d.nome as NOME_DISCIPLINA, m.*, dis.id_discente, dis.nome AS NOME_DISCENTE, s.id_servidor AS ID_ORIENTADOR, s.siape, s.nome AS NOME_ORIENTADOR, p.id_periodo, p.periodo AS NOME_PERIODO FROM monitoria m, disciplinas d, discentes dis, servidor s, periodo p  WHERE m.id_periodo = p.id_periodo AND d.id_disciplinas = m.id_disciplina AND m.id_discente = dis.id_discente AND s.id_servidor = m.id_orientador";
        
        try{
            
            
            int i = 1;
            
            if(monitoria.getDiscente().getNome() != null){
                sql += " AND UPPER(dis.nome) LIKE UPPER(?))";
            }
        
            if(monitoria.getDataInicio() != null){
                sql += " AND m.data_inicio = ?";
            }

            if(monitoria.getDataFim() != null){
                sql += " AND m.data_fim = ?";
            }
            
            if(monitoria.getDisciplina().getId() != null){
                sql += " AND d.id_disciplinas = ?";
            }
            
            PreparedStatement st = this.conn.prepareStatement(sql);
            ResultSet rs;
            
            if(monitoria.getDiscente().getNome() != null){
                st.setString(i, "%" + monitoria.getDiscente().getNome() + "%");
                i++;
            }
        
            if(monitoria.getDataInicio() != null){
                st.setDate(i, (Date) monitoria.getDataInicio());
                i++;
            }
            
            if(monitoria.getDataFim() != null){
                st.setDate(i, (Date) monitoria.getDataFim());
                i++;
            }
            
            if(monitoria.getDisciplina().getId() != null){
                st.setInt(i, monitoria.getDisciplina().getId());
                i++;
            }
            
            rs = st.executeQuery();
            listaMonitoria = mapearResultSet(rs);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return listaMonitoria;
    }
}
