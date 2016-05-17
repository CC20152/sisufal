/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.util;

import cc20152.sisufal.models.Discente;
import javafx.util.StringConverter;

/**
 *
 * @author Dayvson
 */
public class DiscenteConverter extends StringConverter<Discente>{

    @Override
    public String toString(Discente object) {
        if(object == null) return "";
        return object.toString();
    }

    @Override
    public Discente fromString(String string) {
        Discente d = new Discente();
        String[] s = string.trim().split("-");
        d.setId(Integer.parseInt(s[0]));
        d.setNome(s[1]);
        
        return d;
    }
    
}
