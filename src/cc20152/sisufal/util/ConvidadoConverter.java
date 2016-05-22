/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.util;

import cc20152.sisufal.models.Convidado;
import javafx.util.StringConverter;

/**
 *
 * @author AtaideAl
 */
public class ConvidadoConverter extends StringConverter<Convidado>{

    @Override
    public String toString(Convidado object) {
        if(object == null) return "";
        return object.toString();
    }

    @Override
    public Convidado fromString(String string) {
        Convidado d = new Convidado();
        String[] s = string.trim().split("-");
        d.setId(Integer.parseInt(s[0]));
        d.setNome(s[2]);
        
        return d;
    }
    
}
