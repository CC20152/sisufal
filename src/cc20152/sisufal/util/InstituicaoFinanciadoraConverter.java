package cc20152.sisufal.util;


import cc20152.sisufal.models.InstituicaoFinanciadora;
import javafx.util.StringConverter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author anderson
 */
public class InstituicaoFinanciadoraConverter extends StringConverter<InstituicaoFinanciadora>{

    @Override
    public String toString(InstituicaoFinanciadora object) {
        if(object == null) return "";
        return object.toString();
    }

    @Override
    public InstituicaoFinanciadora fromString(String string) {
        InstituicaoFinanciadora d = new InstituicaoFinanciadora();
        String[] s = string.trim().split("-");
        d.setId(Integer.parseInt(s[0]));
        d.setNome(s[2]);
        
        return d;
    }
    
}
