/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Dayvson
 */
public class DataUtil {
    private static SimpleDateFormat formatador = new SimpleDateFormat();
    
    public static String getDataApresentacao(Date data) {
	String dataRetorno;
	formatador.applyPattern("dd/MM/yyyy");
	dataRetorno = formatador.format(data);
	return dataRetorno;
    }
}
