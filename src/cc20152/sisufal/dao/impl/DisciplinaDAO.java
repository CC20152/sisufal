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
import java.util.ArrayList;

/**
 *
 * @author Dayvson
 */
public class DisciplinaDAO implements IBaseDAO {
    
    private Connection conn;
    
    @Override
    public String save(Object object){
        conn = Conexao.getConexao();
        String sql = "INSERT INTO disciplina(nome) VALUES(?)";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, ((Disciplina) object).getNome());
            st.execute();
        }catch(Exception ex){
            ex.printStackTrace();
            return "ERROR";
        }
        return "OK";
    }
    
    @Override
    public String update(Object object){
        return "ok";
    }
    
    @Override
    public String delete(Object object){
        return "ok";
    }
    
    @Override
    public List<Disciplina> listAll(){
        return new ArrayList<>();
    }
    
    @Override
    public List<Disciplina> listWithParams(Object object){
        return new ArrayList<>();
    }
}
