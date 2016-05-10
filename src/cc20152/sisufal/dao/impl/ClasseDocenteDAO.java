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
public class ClasseDocenteDAO implements IBaseDAO {
    
    private Connection conexao;
    
    @Override
    public String save(Object object){
        conexao = Conexao.getConexao();
        String sql = "INSERT INTO `servidor`(`siape`, `nome`, `cargo`, `cpf`, `docente`, `id_classe`) VALUES (?,?,?,?,?,?)";   
        try{
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, ((Servidor) object).getSiape());
            st.setString(2, ((Servidor) object).getNome());
            st.setString(3, ((Servidor) object).getCargo());
            st.setString(4, ((Servidor) object).getCPF());
            if(((Servidor)object).getClasse().getNome().equals("")){
                st.setInt(5, 0);
            }else{
                st.setInt(5, 1);
                st.setInt(5,((Servidor) object).getClasse().getId());
            }
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
    public List<ClasseDocente> listAll(){
        ArrayList<ClasseDocente> lista = new ArrayList<ClasseDocente>();
        conexao = Conexao.getConexao();
        String sql = "SELECT * FROM classedocente";
        
        try{
            PreparedStatement st = conexao.prepareStatement(sql);
            ResultSet rs;

            rs = st.executeQuery();
            while ( rs.next() ) {
                ClasseDocente classe = new ClasseDocente();
                classe.setId(rs.getInt("id_classe_docente"));
                classe.setNome(rs.getString("nome"));
                lista.add(classe);
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