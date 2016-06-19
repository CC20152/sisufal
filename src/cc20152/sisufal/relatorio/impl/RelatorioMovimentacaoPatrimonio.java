/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.relatorio.impl;

import cc20152.sisufal.dao.impl.PatrimonioDAO;
import cc20152.sisufal.models.Patrimonio;
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
public class RelatorioMovimentacaoPatrimonio extends Relatorio {
    
    public RelatorioMovimentacaoPatrimonio(){
        super.relatorio = "reportMovimentacao.jasper";
    }
    /*
    public void gerarDeclaracao(Monitoria monitoria) throws JRException, IOException{
        
        monitoria.getDiscente().setNome(monitoria.getDiscente().getNome().toUpperCase());
        monitoria.getOrientador().setNome(monitoria.getOrientador().getNome().toUpperCase());
        monitoria.getDisciplina().setNome((monitoria.getDisciplina().getNome() + ".").toUpperCase());
        
        List<Monitoria> listaMonitoria = new ArrayList<>();
        listaMonitoria.add(monitoria);
        ds = new JRBeanCollectionDataSource(listaMonitoria, false);
        parametros = new HashMap();
        super.relatorio = "reportDeclaracaoMonitoria.jasper";
        
        imprimirRelatorio();
    }
    */
    public void gerarRelatorio(Patrimonio patrimonio, Map parametros) throws JRException, IOException{
        
        super.parametros = parametros;
        
        List<Patrimonio> listaPatrimonio = new PatrimonioDAO().listWithParams(patrimonio);
        
        ds = new JRBeanCollectionDataSource(listaPatrimonio, false);
        
        imprimirRelatorio();
    }
    
}
