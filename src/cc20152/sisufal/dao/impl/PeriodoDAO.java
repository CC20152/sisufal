/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.dao.impl;

import cc20152.sisufal.dao.IBaseDAO;
import cc20152.sisufal.db.Conexao;
import cc20152.sisufal.models.Disciplina;
import cc20152.sisufal.models.Periodo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import cc20152.sisufal.models.Usuario;
import java.sql.ResultSet;
/**
 *
 * @author Predator
 */
public class PeriodoDAO implements IBaseDAO {
    
    private Connection conexao;
    
    @Override
    public String save(Object object){
        conexao = Conexao.getConexao();
        String sql = "INSERT INTO disciplina(nome) VALUES(?)";   
        try{
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, ((Usuario) object).getLogin());
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
    public List<Periodo> listAll(){
        ArrayList<Periodo> lista = new ArrayList<>();
        conexao = Conexao.getConexao();
        String sql = "SELECT * FROM periodo";
        
        try{
            PreparedStatement st = conexao.prepareStatement(sql);
            ResultSet rs;

            rs = st.executeQuery();
            while ( rs.next() ) {
                Periodo periodo = new Periodo();
                periodo.setId(rs.getInt("id_periodo"));
                periodo.setNome(rs.getString("periodo"));
                lista.add(periodo);
            }
            //conexao.close();
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
        return lista;
    }
    
    @Override
    public List<Disciplina> listWithParams(Object object){
        return new ArrayList<>();
    }
}