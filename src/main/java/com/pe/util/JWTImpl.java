/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pe.util;

import com.pe.interfaces.IJWT;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import io.jsonwebtoken.*;
import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

/**
 *
 * @author dbermudez
 */
@Component(UtilDefines.INSTANCE_JWT)
public class JWTImpl implements IJWT {

    private String apiKey = null;
    private JwtBuilder builder = null;
    private Claims claims = null;

    public JWTImpl() {
        apiKey = UtilDefines.APIKEY;
    }

    @Override
    public String getApiKey() {
        return apiKey;
    }

    @Override
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String getJWT() {
        String sRet = "";
        try {
            sRet = builder.compact();
        } catch (NullPointerException ex) {
            sRet = "";
        }
        return sRet;
    }

    @Override
    public void setJWT(Object builder) {
        this.builder = (JwtBuilder) builder;
    }

    @Override
    public void createJWT(String id, String issuer, String subject, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = null;
        long nowMillis = 0, expMillis = 0;
        Date now = null, exp = null;
        byte[] apiKeySecretBytes = null;
        Key signingKey = null;
        try {
            signatureAlgorithm = SignatureAlgorithm.HS256;
            nowMillis = System.currentTimeMillis();
            now = new Date(nowMillis);
            apiKeySecretBytes = DatatypeConverter.parseBase64Binary(getApiKey());
            signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
            setJWT(Jwts.builder().setId(id).setIssuedAt(now).setSubject(subject).setIssuer(issuer).signWith(signatureAlgorithm, signingKey));
            if (ttlMillis >= 0) {
                expMillis = nowMillis + ttlMillis;
                exp = new Date(expMillis);
                builder.setExpiration(exp);
            }
        } catch (Exception ex) {
            System.out.println("imposible crear el token");
        }
    }

    @Override
    public void setClaims(String atri, Object value) {
        try {
            builder.claim(atri, value);
        } catch (NullPointerException ex) {
            System.out.println("Imposible asignar el atributo");
        }
    }

    @Override
    public Object getClaims(Object atri) {
        Object oRet;
        try {
            if (atri.toString().toString().equalsIgnoreCase("id")) {
                oRet = claims.getId();
            } else if (atri.toString().toString().equalsIgnoreCase("subject")) {
                oRet = claims.getSubject();
            } else if (atri.toString().toString().equalsIgnoreCase("issuer")) {
                oRet = claims.getIssuer();
            } else if (atri.toString().toString().equalsIgnoreCase("expiration")) {
                oRet = claims.getExpiration();
            } else {
                oRet = claims.get(atri);
            }
        } catch (NullPointerException ex) {
            oRet = "";
        }
        return oRet;
    }

    @Override
    public boolean validateJWT(String jwt) {
        boolean bRet = true;
        try {
            claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(getApiKey())).parseClaimsJws(jwt).getBody();
        } catch (ExpiredJwtException ex) {
            System.out.println("El token expiro");
            bRet = false;
        } catch (IllegalArgumentException ex) {
            System.out.println("Argumento Ilegal");
            bRet = false;
        }
        return bRet;
    }
}
