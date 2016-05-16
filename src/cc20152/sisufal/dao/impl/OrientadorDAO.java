/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.dao.impl;

import cc20152.sisufal.dao.IBaseDAO;
import cc20152.sisufal.db.Conexao;
import cc20152.sisufal.models.Orientador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dayvson
 */
public class OrientadorDAO implements IBaseDAO{

    private Connection conn;
    
    @Override
    public String save(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String update(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String delete(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Orientador> listAll() {
        ArrayList<Orientador> listaOrientador = new ArrayList<>(); //To change body of generated methods, choose Tools | Templates.
        
        this.conn = Conexao.getConexao();
        String sql = "SELECT * FROM servidor WHERE docente = 1";
        
        try{
            PreparedStatement st = this.conn.prepareStatement(sql);
            ResultSet rs;

            rs = st.executeQuery();
            listaOrientador = mapearResultSet(rs);
            //conexao.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return listaOrientador;
    }

     public ArrayList<Orientador> mapearResultSet(ResultSet rs) throws SQLException{
		
	ArrayList<Orientador> listaOrientador = new ArrayList();
        
	while(rs.next()){
            Orientador orientador = new Orientador();
            orientador.setId(rs.getInt("ID_SERVIDOR"));
            orientador.setSiape(rs.getString("SIAPE"));
            orientador.setNome(rs.getString("NOME"));
            orientador.setCargo(rs.getString("CARGO"));
            orientador.setCPF(rs.getString("CPF"));
            orientador.getClasse().setId(rs.getInt("ID_CLASSE"));
            
            listaOrientador.add(orientador);
	}
        
	return listaOrientador;
    }
    
    @Override
    public List<?> listWithParams(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
