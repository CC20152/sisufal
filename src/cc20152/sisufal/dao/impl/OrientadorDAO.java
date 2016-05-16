/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.dao.impl;

import cc20152.sisufal.dao.IBaseDAO;
import cc20152.sisufal.models.Orientador;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dayvson
 */
public class OrientadorDAO implements IBaseDAO{

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
        return listaOrientador;
    }

    @Override
    public List<?> listWithParams(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
