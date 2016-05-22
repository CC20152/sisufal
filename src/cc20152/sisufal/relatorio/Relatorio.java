/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.relatorio;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Dayvson
 */
public abstract class Relatorio {
    
    protected JRBeanCollectionDataSource ds;
    protected String path;
    protected String relatorio;
    protected Map parametros;
    
    @SuppressWarnings("unchecked")
    protected void imprimirRelatorio() throws JRException, IOException {
        String path = getClass().getResource("").toString();
        path = path.replace("impl", "jrxml");
        parametros.put("CAMINHO", path);
        
        InputStream jasperStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("cc20152/sisufal/relatorio/jrxml/reportMonitoria.jasper");
        
        JasperPrint impressao = JasperFillManager.fillReport(jasperStream, parametros, ds);
        JasperViewer viewer = new JasperViewer(impressao, false);
        
        viewer.setVisible(true);
    }
}
