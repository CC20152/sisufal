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
import java.util.HashMap;
/**
 *
 * @author Predator
 */
public class ServidorDAO implements IBaseDAO {
    
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
            if(((Servidor)object).getClasse().getId()==null){
                st.setInt(5, 0);
                st.setNull(6,java.sql.Types.INTEGER);
            }else{
                st.setInt(5, 1);
                st.setInt(6,((Servidor) object).getClasse().getId());
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
        this.conexao = Conexao.getConexao();
        String sql = "UPDATE servidor SET siape = ?, nome = ?, cargo = ?, cpf = ?, docente = ?, id_classe = ? WHERE id_servidor = ?";   
        try{
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, ((Servidor) object).getSiape());
            st.setString(2, ((Servidor) object).getNome());
            st.setString(3, ((Servidor) object).getCargo());
            st.setString(4, ((Servidor) object).getCPF());
            if(((Servidor) object).getClasse().getId()!=null){
                st.setInt(5, 1);
                st.setInt(6, ((Servidor) object).getClasse().getId());
            }
            else{
                 st.setInt(5,0);
                 st.setNull(6,java.sql.Types.INTEGER);
            }
            
            st.setInt(7, ((Servidor) object).getId());
            
            st.execute();
        }catch(Exception ex){
            ex.printStackTrace();
            return "ERROR";
        }
        return "OK";
    }
    
    @Override
    public String delete(Object object){
        this.conexao = Conexao.getConexao();
        String sql = "DELETE FROM servidor WHERE id_servidor = ?";   
        try{
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1, ((Servidor) object).getId());
            st.execute();
        }catch(Exception ex){
            ex.printStackTrace();
            return "ERROR";
        }
        return "OK";
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
    public List<Servidor> listWithParams(Object object){
        ArrayList<Servidor> lista = new ArrayList<Servidor>();
        conexao = Conexao.getConexao();
        Servidor servidor = ((Servidor)object);
        String sql = "SELECT * FROM servidor LEFT JOIN classedocente on servidor.id_classe = classedocente.id_classe_docente ";
        
        if(servidor.getNome()!=null)
            sql+= "WHERE servidor.nome LIKE ?";
        else if(servidor.getCargo()!=null)
            sql+= "WHERE servidor.cargo LIKE ?";
        else if (servidor.getSiape() != null)
            sql+= "WHERE servidor.siape LIKE ?";
        else if (servidor.getClasse().getNome()!=null)
            sql+= "WHERE classedocente.nome LIKE ?";
        
       
        try{
            PreparedStatement st = conexao.prepareStatement(sql);
            ResultSet rs;
            if(servidor.getNome()!=null)
                st.setString(1, "%"+servidor.getNome()+"%");
            else if(servidor.getCargo()!=null)
                st.setString(1, "%"+servidor.getCargo()+"%");
            else if (servidor.getSiape() != null)
                st.setString(1, "%"+servidor.getSiape()+"%");
            else if (servidor.getClasse().getNome()!=null)
                st.setString(1, "%"+servidor.getClasse().getNome()+"%");

            rs = st.executeQuery();
            while ( rs.next() ) {
                Servidor s = new Servidor();
                s.setId(rs.getInt("servidor.id_servidor"));
                s.setNome(rs.getString("servidor.nome"));
                s.setSiape(rs.getString("servidor.siape"));
                s.setCargo(rs.getString("servidor.cargo"));
                s.setCPF(rs.getString("servidor.cpf"));
                int docente = rs.getInt("servidor.docente");
                if(docente==1){
                    s.getClasse().setId(rs.getInt("classedocente.id_classe_docente"));
                    s.getClasse().setNome(rs.getString("classedocente.nome"));
                }
                lista.add(s);
            }
            //conexao.close();
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
        return lista;
    }
    
    public Servidor getServidorById(Servidor servidor){
        Servidor s = new Servidor();
        conexao = Conexao.getConexao();
        String sql = "SELECT * FROM servidor "
                + "LEFT JOIN classedocente on servidor.id_classe = classedocente.id_classe_docente "
                + "WHERE servidor.id_servidor = ?";
        
        try{
            PreparedStatement st = conexao.prepareStatement(sql);
            ResultSet rs;
            
            st.setInt(1, servidor.getId());
            rs = st.executeQuery();
            if ( rs.next() ) {
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
            }
            //conexao.close();
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
        return s;
    }
    
    public ArrayList<Servidor> allByProjeto(Projeto projeto) {
        ArrayList<Servidor> listaServidores = new ArrayList();
        
        this.conexao = Conexao.getConexao();
        String sql = "SELECT s.id_servidor, s.nome as nome_servidor "
                + "FROM servidor s, grupoprojeto gp, projeto p "
                + "WHERE p.id_projeto = ? "
                + "AND gp.id_servidor = s.id_servidor "
                + "AND gp.id_projeto = p.id_projeto";
        
        try{
            PreparedStatement st = this.conexao.prepareStatement(sql);
            st.setInt(1, projeto.getId());
            ResultSet rs;

            rs = st.executeQuery();
            
            while(rs.next()){
                Servidor servidor = new Servidor();
                
                servidor.setId(rs.getInt("id_servidor"));
                servidor.setNome(rs.getString("nome_servidor"));

                listaServidores.add(servidor);
            }
            
            Conexao.desconectar();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return listaServidores;
    }
}