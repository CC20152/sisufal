/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.util;

import cc20152.sisufal.models.Servidor;
import javafx.util.StringConverter;

/**
 *
 * @author Dayvson
 */
public class ServidorConverter extends StringConverter<Servidor>{

    @Override
    public String toString(Servidor object) {
        if(object == null) return "";
        return object.toString();
    }

    @Override
    public Servidor fromString(String string) {
        Servidor d = new Servidor();
        String[] s = string.trim().split("-");
        d.setId(Integer.parseInt(s[0]));
        d.setNome(s[2]);
        
        return d;
    }
    
}
