package com.example.demo1.util;

import org.apache.http.conn.ssl.TrustStrategy;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by LDB on 16-5-6.
 */
public class TrustAnySignedStrategy implements TrustStrategy {

    public boolean isTrusted(
            final X509Certificate[] chain, final String authType) throws CertificateException {
        return true;
    }

}