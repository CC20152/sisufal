/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.dao;

import java.util.List;

/**
 *
 * @author Dayvson
 */
public interface IBaseDAO {
    public String save(Object object);
    public String update(Object object);
    public String delete(Object object);
    public List<?> listAll();
    public List<?> listWithParams(Object object);
}
