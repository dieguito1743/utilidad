/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pe.util.test;

import com.pe.util.JWTImpl;

/**
 *
 * @author dbermudez
 */
public class JWTImplTest {

    public static void main(String arg[]) {
        JWTImpl jJWT = new JWTImpl();
        //jJWT.setApiKey("1111");//24 bits
        //jJWT.setApiKey("11112222");//48 bits
        //jJWT.setApiKey("111122223333");//72 bits
        //jJWT.setApiKey("1111222233334444");//96 bits
        //jJWT.setApiKey("11112222333344445555");//120 bits
        //jJWT.setApiKey("111122223333444455556666");//144 bits
        //jJWT.setApiKey("1111222233334444555566667777888899990000AAAA");//264 bits
        jJWT.createJWT("id", "issuer", "subject", 10000);
        jJWT.setClaims("name", "Diego");
        String sJWT = jJWT.getJWT();
        System.out.println(sJWT);
        //jJWT.parseJWT(sJWT);
        System.out.println(jJWT.validateJWT(sJWT));
    }
}
