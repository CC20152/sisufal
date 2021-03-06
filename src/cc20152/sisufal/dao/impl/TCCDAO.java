/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.dao.impl;

import cc20152.sisufal.dao.IBaseDAO;
import cc20152.sisufal.db.Conexao;
import cc20152.sisufal.models.BancaTCC;
import cc20152.sisufal.models.Concurso;
import cc20152.sisufal.models.Convidado;
import cc20152.sisufal.models.Discente;
import cc20152.sisufal.models.Orientador;
import cc20152.sisufal.models.Servidor;
import cc20152.sisufal.models.TCC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author AtaideAl
 */
public class TCCDAO implements IBaseDAO{

    private Connection conn;
    
    @Override
    public String save(Object object) {
        TCC tcc = ((TCC)object);
        int idTCC = saveTCC(tcc);
        if(idTCC!=-1){
            //System.out.println("Cadastrou TCC:"+idTCC);
            int idBanca = saveBancaTCC(idTCC);
            if (idBanca!=-1){
                //System.out.println("Cadastrou banca:"+idBanca);
                String result = saveIdBancaTCC(idTCC,idBanca);
                if(result.equals("OK")){
                    //System.out.println("Salvou banca TCC");
                    //salvar discentes
                    for (Discente d : tcc.getBanca().getListaDiscentes()) {
                        saveParticipanteDiscente(idBanca,d.getId());
                    }
                    //salvar professores
                    for (Servidor s : tcc.getBanca().getListaServidores()) {
                        saveParticipanteProf(idBanca,s.getId());
                    }
                    //salvar convidados
                    for (Convidado c : tcc.getBanca().getListaConvidados()) {
                        if(c.getId()==null)
                            c.setId(saveConvidado(c)); 
                        saveParticipanteConvidado(idBanca,c.getId());
                    }
                    //System.out.println("Salvou participantes");
                    return "OK";
                }
            }
        }
        
        
        return "ERROR";
    }

    @Override
    public String update(Object object) {
        TCC tcc = ((TCC)object);
        deletarBanca(tcc.getBanca());
        int idBanca = saveBancaTCC(tcc.getId());
            if (idBanca!=-1){
                tcc.getBanca().setId(idBanca);
                //System.out.println("Cadastrou banca:"+idBanca);
                String result = updateTcc(tcc);
                if(result.equals("OK")){
                    //System.out.println("Salvou banca TCC");
                    //salvar discentes
                    for (Discente d : tcc.getBanca().getListaDiscentes()) {
                        saveParticipanteDiscente(idBanca,d.getId());
                    }
                    //salvar professores
                    for (Servidor s : tcc.getBanca().getListaServidores()) {
                        saveParticipanteProf(idBanca,s.getId());
                    }
                    //salvar convidados
                    for (Convidado c : tcc.getBanca().getListaConvidados()) {
                        if(c.getId()==null)
                            c.setId(saveConvidado(c)); 
                        saveParticipanteConvidado(idBanca,c.getId());
                    }
                    //System.out.println("Salvou participantes");
                    return "OK";
                }
            }
        
        return "ERROR";
    }

    @Override
    public String delete(Object object) {
    this.conn = Conexao.getConexao();
        String sql = "DELETE FROM tcc WHERE id_TCC = ?";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, ((TCC) object).getId());
            st.execute();
        }catch(Exception ex){
            ex.printStackTrace();
            return "ERROR";
        }
        return "OK";
    }

    @Override
    public List<TCC> listAll() {
        ArrayList<TCC> listaTCC = new ArrayList<>();
        this.conn = Conexao.getConexao();
        String sql = "SELECT * FROM tcc " +
                     "INNER JOIN cursos ON tcc.id_curso = cursos.id_curso " +
                     "INNER JOIN discentes on discentes.id_discente = tcc.id_discente " +
                     "INNER JOIN servidor on servidor.id_servidor =  tcc.id_orientador";
        
        try{
            PreparedStatement st = this.conn.prepareStatement(sql);
            ResultSet rs;

            rs = st.executeQuery();
            listaTCC = mapearResultSet(rs);
            //conexao.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return listaTCC;
    }

    @Override
    public List<TCC> listWithParams(Object object) {
        ArrayList<TCC> lista = new ArrayList<TCC>();
        conn = Conexao.getConexao();
        TCC tcc = ((TCC)object);
        String sql = "SELECT * FROM tcc " +
                     "INNER JOIN cursos ON tcc.id_curso = cursos.id_curso " +
                     "INNER JOIN discentes on discentes.id_discente = tcc.id_discente " +
                     "INNER JOIN servidor on servidor.id_servidor =  tcc.id_orientador ";
        
        if(tcc.getTitulo()!=null)
            sql+= "WHERE tcc.titulo_TCC LIKE ? ";       
       
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs;
            if(tcc.getTitulo()!=null)
                st.setString(1, "%"+tcc.getTitulo()+"%");

            rs = st.executeQuery();
            lista = mapearResultSet(rs);
            //conexao.close();
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
        return lista;
    }
    
     public ArrayList<TCC> mapearResultSet(ResultSet rs) throws SQLException{
		
	ArrayList<TCC> listaTCC = new ArrayList();
        
	while(rs.next()){
            TCC tcc = new TCC();
            tcc.setId(rs.getInt("tcc.id_TCC"));
            tcc.setTitulo(rs.getString("tcc.titulo_TCC"));
            tcc.getCurso().setId(rs.getInt("cursos.id_curso"));
            tcc.getCurso().setNome(rs.getString("cursos.nome"));
            
            tcc.getDiscente().setId(rs.getInt("discentes.id_discente"));
            tcc.getDiscente().setNome(rs.getString("discentes.nome"));
            
            tcc.getOrientador().setId(rs.getInt("servidor.id_servidor"));
            tcc.getOrientador().setNome(rs.getString("servidor.nome"));
            
            tcc.setDataInicio(rs.getDate("tcc.data_inicio"));
            tcc.setDataFim(rs.getDate("tcc.data_fim"));
            
            tcc.getBanca().setId(rs.getInt("tcc.id_banca_TCC"));
                       
            listaTCC.add(tcc);
	}
        
	return listaTCC;
    }
     
     public int saveTCC(TCC tcc){
        this.conn = Conexao.getConexao();
        String sql = "INSERT INTO tcc(titulo_TCC, data_inicio, data_fim, id_curso, id_discente, id_orientador) VALUES(?, ?, ?, ?, ?, ?)";   
        ResultSet rs;
        int codigo = -1;
        try{
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, tcc.getTitulo());
            st.setDate(2, new java.sql.Date(tcc.getDataInicio().getTime()));
            st.setDate(3, new java.sql.Date(tcc.getDataFim().getTime()));
            st.setInt(4, tcc.getCurso().getId());
            st.setInt(5, tcc.getDiscente().getId());
            st.setInt(6, tcc.getOrientador().getId());
            
            st.executeUpdate();
            rs = st.getGeneratedKeys();
            if(rs.next())
                codigo = rs.getInt(1);
        }catch(Exception ex){
            ex.printStackTrace();
            return -1;
        }        
	return codigo;
    } 
     
    private String updateTcc(TCC tcc){
       this.conn = Conexao.getConexao();
        String sql = "UPDATE tcc SET id_curso = ?, id_discente = ?, id_orientador = ?, titulo_tcc = ?, data_inicio = ?, data_fim = ?, id_banca_tcc = ? WHERE id_tcc = ?";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, tcc.getCurso().getId());
            st.setInt(2, tcc.getDiscente().getId());   
            st.setInt(3, tcc.getOrientador().getId());  
            st.setString(4, tcc.getTitulo());
            st.setDate(5, new java.sql.Date(tcc.getDataInicio().getTime()));
            st.setDate(6, new java.sql.Date(tcc.getDataFim().getTime()));
            st.setInt(7, tcc.getBanca().getId());
            st.setInt(8, tcc.getId());
            
            st.execute();
        }catch(Exception ex){
            ex.printStackTrace();
            return "ERROR";
        }
        return "OK";
    }

    private int saveBancaTCC(int id) {
        this.conn = Conexao.getConexao();
        String sql = "INSERT INTO bancatcc(id_TCC) VALUES(?)";   
        ResultSet rs;
        int codigo = -1;
        try{
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1,id);
            
            st.executeUpdate();
            rs = st.getGeneratedKeys();
            if(rs.next())
                codigo = rs.getInt(1);
        }catch(Exception ex){
            ex.printStackTrace();
            return -1;
        }        
	return codigo;
    }

    private String saveIdBancaTCC(int idTCC, int idBanca) {
        this.conn = Conexao.getConexao();
        String sql = "UPDATE tcc SET id_banca_TCC = ? WHERE id_tcc = ?";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, idBanca);
            st.setInt(2, idTCC);         
            st.execute();
        }catch(Exception ex){
            ex.printStackTrace();
            return "ERROR";
        }
        return "OK";
    }

    private void saveParticipanteDiscente(int idBanca, Integer id) {
        this.conn = Conexao.getConexao();
        String sql = "INSERT INTO participantebancatcc(id_banca_TCC,id_discente) VALUES(?,?)";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1,idBanca);
            st.setInt(2,id);
            st.execute();
            }catch(Exception ex){
            ex.printStackTrace();
        }        
    }
    private void saveParticipanteProf(int idBanca, Integer id) {
        this.conn = Conexao.getConexao();
        String sql = "INSERT INTO participantebancatcc(id_banca_TCC,id_servidor) VALUES(?,?)";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1,idBanca);
            st.setInt(2,id);
            st.execute();
            }catch(Exception ex){
            ex.printStackTrace();
        }        
    }
    private void saveParticipanteConvidado(int idBanca, Integer id) {
        this.conn = Conexao.getConexao();
        String sql = "INSERT INTO participantebancatcc(id_banca_TCC,id_convidado) VALUES(?,?)";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1,idBanca);
            st.setInt(2,id);
            st.execute();
            }catch(Exception ex){
            ex.printStackTrace();
        }        
    }

    private Integer saveConvidado(Convidado c) {
        this.conn = Conexao.getConexao();
        String sql = "INSERT INTO convidado(nome) VALUES(?)";   
        ResultSet rs;
        int codigo = -1;
        try{
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1,c.getNome());
            
            st.executeUpdate();
            rs = st.getGeneratedKeys();
            if(rs.next())
                codigo = rs.getInt(1);
        }catch(Exception ex){
            ex.printStackTrace();
            return -1;
        }        
	return codigo;
    }
    
    public ArrayList<Orientador> listaProfessorBanca(TCC tcc){
    ArrayList<Orientador> listaProfessorBanca = new ArrayList<>();
        this.conn = Conexao.getConexao();
        String sql = "SELECT * FROM participantebancatcc p " +
                     "INNER JOIN servidor ON servidor.id_servidor =  p.id_servidor " +
                     "WHERE p.id_banca_TCC = ?";
        
        try{
            PreparedStatement st = this.conn.prepareStatement(sql);
            //System.out.println(tcc.getBanca().getId().toString());
            st.setInt(1, tcc.getBanca().getId());
            
            ResultSet rs;

            rs = st.executeQuery();
            while ( rs.next() ) {
            Orientador orientador = new Orientador();
            orientador.setId(rs.getInt("servidor.id_servidor"));
            orientador.setNome(rs.getString("servidor.nome"));
            orientador.setSiape(rs.getString("servidor.siape"));
            orientador.setCargo(rs.getString("servidor.cargo"));
            orientador.setCPF(rs.getString("servidor.cpf"));
            listaProfessorBanca.add(orientador);
            }
            
            //conexao.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return listaProfessorBanca;
    }
    
    public ArrayList<Discente> listaDiscenteBanca(TCC tcc){
    ArrayList<Discente> listaDiscenteBanca = new ArrayList<>();
        this.conn = Conexao.getConexao();
        String sql = "SELECT * FROM participantebancatcc p " +
                     "INNER JOIN discentes ON discentes.id_discente = p.id_discente " +
                     "WHERE p.id_banca_TCC = ?";
        
        try{
            PreparedStatement st = this.conn.prepareStatement(sql);
            //System.out.println(tcc.getBanca().getId().toString());
            st.setInt(1, tcc.getBanca().getId());
            
            ResultSet rs;

            rs = st.executeQuery();
            while ( rs.next() ) {
                Discente discente = new Discente();
                discente.setId(rs.getInt("discentes.id_discente"));
                discente.setNome(rs.getString("discentes.nome"));
                discente.setCpf(rs.getString("discentes.cpf"));
                discente.setMatricula(rs.getString("discentes.matricula"));
                listaDiscenteBanca.add(discente);
            }
            
            //conexao.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return listaDiscenteBanca;
    }
    
    public ArrayList<Convidado> listaConvidadoBanca(TCC tcc){
    ArrayList<Convidado> listaConvidadoBanca = new ArrayList<>();
        this.conn = Conexao.getConexao();
        String sql = "SELECT * FROM participantebancatcc p " +
                     "INNER JOIN convidado ON convidado.id_convidado =  p.id_convidado " +
                     "WHERE p.id_banca_TCC = ?";
        
        try{
            PreparedStatement st = this.conn.prepareStatement(sql);
            //System.out.println(tcc.getBanca().getId().toString());
            st.setInt(1, tcc.getBanca().getId());
            
            ResultSet rs;

            rs = st.executeQuery();
            while ( rs.next() ) {
                Convidado convidado = new Convidado();
                convidado.setId(rs.getInt("convidado.id_convidado"));
                convidado.setNome(rs.getString("convidado.nome"));
                listaConvidadoBanca.add(convidado);
            }
            
            //conexao.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return listaConvidadoBanca;
    }
    
    public void deletarBanca(BancaTCC banca) {
    this.conn = Conexao.getConexao();
        String sql = "DELETE FROM bancatcc WHERE id_banca_TCC = ?";   
        try{
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, banca.getId());
            st.execute();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
