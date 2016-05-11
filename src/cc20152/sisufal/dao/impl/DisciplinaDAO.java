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
 * @author Dayvson
 */
public class DisciplinaDAO implements IBaseDAO {
    
    private Connection conn;
    
    @Override
    public String save(Object object){
        this.conn = Conexao.getConexao();
        String sql = "INSERT INTO disciplinas(nome, carga_horaria, id_curso, turno) VALUES(?, ?, ?, ?)";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, ((Disciplina) object).getNome());
            st.setString(2, ((Disciplina) object).getCargaHoraria());
            st.setInt(3, ((Disciplina) object).getCurso().getId());
            st.setString(4, ((Disciplina) object).getTurno());
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
        String sql = "UPDATE disciplinas SET nome = ?, carga_horaria = ?, id_curso = ?, turno = ? WHERE id_disciplinas = ?";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, ((Disciplina) object).getNome());
            st.setString(2, ((Disciplina) object).getCargaHoraria());
            st.setInt(3, ((Disciplina) object).getCurso().getId());
            st.setString(4, ((Disciplina) object).getTurno());
            st.setInt(5, ((Disciplina) object).getId());
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
        String sql = "DELETE FROM disciplinas WHERE id_disciplinas = ?";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, ((Disciplina) object).getId());
            st.execute();
        }catch(Exception ex){
            ex.printStackTrace();
            return "ERROR";
        }
        return "OK";
    }
    
    @Override
    public List<Disciplina> listAll(){
        ArrayList<Disciplina> listaDisciplina = new ArrayList();
        this.conn = Conexao.getConexao();
        String sql = "SELECT d.id_disciplinas, d.nome, c.nome as NOME_CURSO, d.carga_horaria, d.turno, d.id_curso FROM disciplinas d, cursos c WHERE d.id_curso = c.id_curso";
        
        try{
            PreparedStatement st = this.conn.prepareStatement(sql);
            ResultSet rs;

            rs = st.executeQuery();
            listaDisciplina = mapearResultSet(rs);
            //conexao.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return listaDisciplina;
    }
    
    /*
    * Eu escrevi isso sem ter a certeza se é um bom método
    */
    @Override
    public List<Disciplina> listWithParams(Object object){
        ArrayList<Disciplina> listaDisciplina = new ArrayList();
        this.conn = Conexao.getConexao();
        Disciplina disciplina = (Disciplina) object;
        String sql = "SELECT d.id_disciplinas, d.nome, c.nome as NOME_CURSO, d.carga_horaria, d.turno, d.id_curso FROM cursos c, disciplinas d WHERE d.id_curso = c.id_curso";
        
        try{
            
            
            int i = 1;
            
            if(disciplina.getCurso().getId() != null){
                sql += " AND c.id_curso = ?";
            }
        
            if(disciplina.getTurno() != null){
                sql += " AND d.turno = ?";
            }

            if(disciplina.getNome() != null){
                sql += " AND upper(d.nome) LIKE upper(?)";
            }
            
            PreparedStatement st = this.conn.prepareStatement(sql);
            ResultSet rs;
            
            if(disciplina.getCurso().getId() != null){
                st.setInt(i, disciplina.getCurso().getId());
                i++;
            }
        
            if(disciplina.getTurno() != null){
                st.setString(i, disciplina.getTurno());
                i++;
            }

            if(disciplina.getNome() != null){
                st.setString(i, "%" + disciplina.getNome() + "%");
                i++;
            }
            
            rs = st.executeQuery();
            listaDisciplina = mapearResultSet(rs);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return listaDisciplina;
    }
    
    
    public ArrayList<Disciplina> mapearResultSet(ResultSet rs) throws SQLException{
		
	ArrayList<Disciplina> listaDisciplina = new ArrayList();
        
	while(rs.next()){
            Disciplina disciplina = new Disciplina();
            disciplina.setId(rs.getInt("ID_DISCIPLINAS"));
            disciplina.setNome(rs.getString("NOME"));
            disciplina.setCargaHoraria(rs.getString("CARGA_HORARIA"));
            disciplina.getCurso().setId(rs.getInt("ID_CURSO"));
            disciplina.getCurso().setNome(rs.getString("NOME_CURSO"));
            disciplina.setTurno(rs.getString("TURNO"));
            listaDisciplina.add(disciplina);
	}
        
	return listaDisciplina;
    }
}
