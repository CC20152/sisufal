/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.util;

import cc20152.sisufal.models.Orientador;
import javafx.util.StringConverter;

/**
 *
 * @author Dayvson
 */
public class OrientadorConverter extends StringConverter<Orientador>{

    @Override
    public String toString(Orientador object) {
        if(object == null) return "";
        return object.toString();
    }

    @Override
    public Orientador fromString(String string) {
        Orientador d = new Orientador();
        String[] s = string.trim().split("-");
        d.setId(Integer.parseInt(s[0]));
        d.setNome(s[2]);
        
        return d;
    }
    
}
