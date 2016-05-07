/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.dao.impl;

import cc20152.sisufal.dao.IBaseDAO;
import cc20152.sisufal.db.Conexao;
import cc20152.sisufal.models.Disciplina;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import cc20152.sisufal.models.*;
import java.sql.ResultSet;
/**
 *
 * @author Predator
 */
public class ServidorDAO implements IBaseDAO {
    
    private Connection conexao;
    
    @Override
    public String save(Object object){
        conexao = Conexao.getConexao();
        String sql = "INSERT INTO disciplina(nome) VALUES(?)";   
        try{
            PreparedStatement st = conexao.prepareStatement(sql);
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
    public List<Servidor> listAll(){
        ArrayList<Servidor> lista = new ArrayList<Servidor>();
        conexao = Conexao.getConexao();
        String sql = "SELECT * FROM servidor "
                + "LEFT JOIN classedocente on servidor.id_classe = classedocente.id_classe_docente";
        
        try{
            PreparedStatement st = conexao.prepareStatement(sql);
            ResultSet rs;

            rs = st.executeQuery();
            while ( rs.next() ) {
                Servidor servidor = new Servidor();
                servidor.setId(rs.getInt("servidor.id_servidor"));
                servidor.setNome(rs.getString("servidor.nome"));
                servidor.setSiape(rs.getString("servidor.siape"));
                servidor.setCargo(rs.getString("servidor.cargo"));
                servidor.setCPF(rs.getString("servidor.cpf"));
                int docente = rs.getInt("servidor.docente");
                if(docente==1){
                    servidor.getClasse().setId(rs.getInt("classedocente.id_classe_docente"));
                    servidor.getClasse().setNome(rs.getString("classedocente.nome"));
                }
                lista.add(servidor);
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