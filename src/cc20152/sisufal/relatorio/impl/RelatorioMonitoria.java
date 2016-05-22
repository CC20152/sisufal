/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.relatorio.impl;

import cc20152.sisufal.dao.impl.MonitoriaDAO;
import cc20152.sisufal.models.Monitoria;
import cc20152.sisufal.relatorio.Relatorio;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Dayvson
 */
public class RelatorioMonitoria extends Relatorio {
    
    public RelatorioMonitoria(){
        relatorio = "reportMonitoria.jasper";
    }
    
    public void gerarRelatorio(Monitoria monitoria, Map parametros) throws JRException, IOException{
        
        super.parametros = parametros;
        
        List<Monitoria> listaMonitoria = new MonitoriaDAO().listWithParams(monitoria);
        
        ds = new JRBeanCollectionDataSource(listaMonitoria, false);
        
        imprimirRelatorio();
    }
    
}
