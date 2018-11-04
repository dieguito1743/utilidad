/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pe.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dbermudez
 */
public class FileFunction {

    private static Properties props = null;

    public static String readFile(String path, String fileName) throws BussinessException {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        String retorno = "";

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File(path + "/" + fileName);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null) {
                retorno = retorno + linea;
            }
        } catch (Exception e) {
            return null;
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
            }
        }
        return retorno;
    }

    public static boolean writeFile(String path, String fileName, String contenido, String separadorLinea) throws BussinessException {
        boolean retorno = false;
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(path + "/" + fileName);
            pw = new PrintWriter(fichero);
            String[] p = contenido.split(separadorLinea);
            for (int i = 0; i < p.length; i++) {
                pw.println(p[i]);
            }
        } catch (Exception e) {
            return retorno;
        } finally {
            try {
                // Nuevamente aprovechamos el finally para 
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
            }
        }
        return retorno;
    }

    private static void readProperty(String nombrePROP) {
        //String nombrePROP = UtilDefines.WEB_INF + UtilDefines.PROPERTIES;
        if (props == null) {
            try {
                props = new Properties();
                props.load(new FileReader(nombrePROP));
            } catch (IOException ex) {
                System.out.println(" no se pudo leer archivo de configuracion" + nombrePROP);
                Logger.getLogger(FileFunction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static Properties getProps(String nombrePROP) {
        readProperty(nombrePROP);
        return props;
    }

    public static String getPropertie(String key, String nombrePROP) {
        String sRet = null;
        try {
            sRet = getProps(nombrePROP).getProperty(key);
        } catch (Exception ex) {
            System.err.println("NO SE PUDO OBTENER LA PROPIEDAD");
            sRet = null;
        }
        return sRet;
    }
}
