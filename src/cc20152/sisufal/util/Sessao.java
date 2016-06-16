/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.util;

import cc20152.sisufal.models.Usuario;

/**
 *
 * @author Dayvson
 */
public class Sessao {
    
    private static Sessao sessao = null; 
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public static Sessao getInstance(){
        if(sessao == null)
           sessao = new Sessao();
        return sessao;
    }        
}
