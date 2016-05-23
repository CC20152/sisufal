/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.dao.impl;

import cc20152.sisufal.dao.IBaseDAO;
import cc20152.sisufal.db.Conexao;
import cc20152.sisufal.models.Discente;
import cc20152.sisufal.models.Disciplina;
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
public class DiscenteDAO implements IBaseDAO {
    
    private Connection conexao;
    
    @Override
    public String save(Object object){
        conexao = Conexao.getConexao();
        String sql = "INSERT INTO `discentes`(`nome`, `matricula`, `curso`, `id_periodo_ingresso`, `cpf`) VALUES (?,?,?,?,?)";   
        try{
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, ((Discente) object).getNome());
            st.setString(2, ((Discente) object).getMatricula());
            st.setInt(3, ((Discente) object).getCurso().getId());
            st.setInt(4, ((Discente) object).getPeriodoIngresso().getId());
            st.setString(5, ((Discente) object).getCpf());
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
        String sql = "UPDATE discentes SET nome = ?, matricula = ?, curso = ?, id_periodo_ingresso = ?, cpf = ? WHERE id_discente = ?";   
        try{
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, ((Discente) object).getNome());
            st.setString(2, ((Discente) object).getMatricula());
            st.setInt(3, ((Discente) object).getCurso().getId());
            st.setInt(4, ((Discente) object).getPeriodoIngresso().getId());
            st.setString(5, ((Discente) object).getCpf());
            st.setInt(6, ((Discente) object).getId());
            
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
        String sql = "DELETE FROM discentes WHERE id_discente = ?";   
        try{
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1, ((Discente) object).getId());
            st.execute();
        }catch(Exception ex){
            ex.printStackTrace();
            return "ERROR";
        }
        return "OK";
    }
    
    @Override
    public List<Discente> listAll(){
        ArrayList<Discente> lista = new ArrayList<Discente>();
        conexao = Conexao.getConexao();
        String sql = "SELECT * FROM `discentes` "
                + "INNER JOIN periodo ON periodo.id_periodo = discentes.id_periodo_ingresso "
                + "INNER JOIN cursos ON cursos.id_curso = discentes.curso";
        
        try{
            PreparedStatement st = conexao.prepareStatement(sql);
            ResultSet rs;

            rs = st.executeQuery();
            while ( rs.next() ) {
                Discente discente = new Discente();
                discente.setId(rs.getInt("discentes.id_discente"));
                discente.setNome(rs.getString("discentes.nome"));
                discente.setCpf(rs.getString("discentes.cpf"));
                discente.setMatricula(rs.getString("discentes.matricula"));
                discente.getCurso().setId(rs.getInt("cursos.id_curso"));
                discente.getCurso().setNome(rs.getString("cursos.nome"));
                discente.getCurso().setCodigo(rs.getString("cursos.codigo"));
                discente.getPeriodoIngresso().setId(rs.getInt("periodo.id_periodo"));
                discente.getPeriodoIngresso().setNome(rs.getString("periodo.periodo"));
                
                lista.add(discente);
            }
            //conexao.close();
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
        return lista;
    }
    
    @Override
    public List<Discente> listWithParams(Object object){
         ArrayList<Discente> lista = new ArrayList<>();
        conexao = Conexao.getConexao();
        Discente d = ((Discente)object);
        String sql = "SELECT * FROM `discentes` "
                + "INNER JOIN periodo ON periodo.id_periodo = discentes.id_periodo_ingresso "
                + "INNER JOIN cursos ON cursos.id_curso = discentes.curso ";
        
        if(d.getNome()!=null)
            sql+= "WHERE discentes.nome LIKE ?";
        else if(d.getMatricula()!=null)
            sql+= "WHERE discentes.matricula LIKE ?";
        else if (d.getCurso().getNome() != null)
            sql+= "WHERE cursos.nome LIKE ?";
        else if (d.getPeriodoIngresso().getNome()!=null)
            sql+= "WHERE periodo.periodo LIKE ?";
        
       
        try{
            PreparedStatement st = conexao.prepareStatement(sql);
            ResultSet rs;
            if(d.getNome()!=null)
                st.setString(1, "%"+d.getNome()+"%");
            else if(d.getMatricula()!=null)
                st.setString(1, "%"+d.getMatricula()+"%");
            else if (d.getCurso().getNome() != null)
                st.setString(1, "%"+d.getCurso().getNome()+"%");
            else if (d.getPeriodoIngresso().getNome()!=null)
                st.setString(1, "%"+d.getPeriodoIngresso().getNome()+"%");

            rs = st.executeQuery();
            while ( rs.next() ) {
                Discente discente = new Discente();
                discente.setId(rs.getInt("discentes.id_discente"));
                discente.setNome(rs.getString("discentes.nome"));
                discente.setCpf(rs.getString("discentes.cpf"));
                discente.setMatricula(rs.getString("discentes.matricula"));
                discente.getCurso().setId(rs.getInt("cursos.id_curso"));
                discente.getCurso().setNome(rs.getString("cursos.nome"));
                discente.getCurso().setCodigo(rs.getString("cursos.codigo"));
                discente.getPeriodoIngresso().setId(rs.getInt("periodo.id_periodo"));
                discente.getPeriodoIngresso().setNome(rs.getString("periodo.periodo"));
                lista.add(discente);
            }
            //conexao.close();
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
        return lista;
    }
}