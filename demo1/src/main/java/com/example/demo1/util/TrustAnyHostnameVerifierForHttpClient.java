package com.example.demo1.util;

import org.apache.http.conn.ssl.X509HostnameVerifier;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.security.cert.X509Certificate;

/**
 * Created by liudianbing on 2016/12/16.
 */
public class TrustAnyHostnameVerifierForHttpClient implements X509HostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }

    @Override
    public void verify(String host, SSLSocket ssl) throws IOException {

    }

    @Override
    public void verify(String host, X509Certificate cert) throws SSLException {

    }

    @Override
    public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {

    }
}