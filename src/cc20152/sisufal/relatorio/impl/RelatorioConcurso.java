/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.relatorio.impl;

import cc20152.sisufal.dao.impl.ServidorDAO;
import cc20152.sisufal.models.Servidor;
import cc20152.sisufal.relatorio.Relatorio;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Dayvson
 */
public class RelatorioConcurso extends Relatorio {
    
    public RelatorioConcurso(){
        super.relatorio = "reportConcurso.jasper";
    }
    
    public void gerarDeclaracao(Servidor servidor, Map parametros) throws JRException, IOException{
        
        super.parametros = parametros;
        
        servidor.setNome(servidor.getNome().toUpperCase());
        servidor.setCPF(servidor.getCPF().toUpperCase());
        servidor.setSiape((servidor.getSiape()).toUpperCase());
        
        List<Servidor> listaServidor = new ArrayList<>();
        listaServidor.add(servidor);
        ds = new JRBeanCollectionDataSource(listaServidor, false);
        parametros = new HashMap();
        super.relatorio = "reportDeclaracaoParticipanteConcurso.jasper";
        
        imprimirRelatorio();
    }
    
    public void gerarRelatorio(Servidor servidor, Map parametros) throws JRException, IOException{
        
        super.parametros = parametros;
        
        List<Servidor> listaServidor = new ServidorDAO().listWithParams(servidor);
        
        ds = new JRBeanCollectionDataSource(listaServidor, false);
        
        imprimirRelatorio();
    }
    
}
