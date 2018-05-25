package com.thesis.smile.di.modules;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okio.Buffer;

public class OkHttpClientTrustCertificate {
    public static OkHttpClient getOkHttpClient(OkHttpClient.Builder builder) {

        X509TrustManager trustManager;
        SSLSocketFactory sslSocketFactory;

        try {
            trustManager = trustManagerForCertificates(trustedCertificatesInputStream());
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[] { trustManager }, null);
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        OkHttpClient okHttpClient = builder
                .sslSocketFactory(sslSocketFactory, trustManager)
                .hostnameVerifier((hostname, session) -> true)
                .build();
        return okHttpClient;
    }

    private static InputStream trustedCertificatesInputStream() {
        String SmileServerCertificate = "" +
                "-----BEGIN CERTIFICATE-----\n" +
                "MIIDcTCCAlmgAwIBAgIEArvClDANBgkqhkiG9w0BAQsFADBpMQswCQYDVQQGEwJQ\n" +
                "VDEQMA4GA1UECBMHTWFkZWlyYTEQMA4GA1UEBxMHRnVuY2hhbDEOMAwGA1UEChMF\n" +
                "TS1JVEkxDjAMBgNVBAsTBU0tSVRJMRYwFAYDVQQDEw1UZWxtYSBDb3JyZWlhMB4X\n" +
                "DTE4MDUyMDE0MzYwOFoXDTIwMDQxOTE0MzYwOFowaTELMAkGA1UEBhMCUFQxEDAO\n" +
                "BgNVBAgTB01hZGVpcmExEDAOBgNVBAcTB0Z1bmNoYWwxDjAMBgNVBAoTBU0tSVRJ\n" +
                "MQ4wDAYDVQQLEwVNLUlUSTEWMBQGA1UEAxMNVGVsbWEgQ29ycmVpYTCCASIwDQYJ\n" +
                "KoZIhvcNAQEBBQADggEPADCCAQoCggEBAIlLNGZ0iTaHqv9anHZRE+eBYgS87bl5\n" +
                "tqrS6RNsxoFzeS6i/ZFwM8OcIetI5r9o7JKvzsMwu3N1KVbca0RCvaSGma7T1GjG\n" +
                "TdmAU08N8UcAj6URK/9CeN2DVQZeMlMtwe3X1gVfYyL3KUqCKIMJiR7KiYks8SyX\n" +
                "5s5G+MPgSpvqVnjzrwbpAme4k40LLnRfdfkH8RjoQ2cfbu7uQVg9H4PWNYwxPf+t\n" +
                "O8DNbXl46jY4tA5Gg0hclGYfNokLDU0J83eqqA6saUH2Hk1yXK4gYWMuJwxdJV2F\n" +
                "UlG7K+oa9QkTJfklR5WwtRscdSvT5Ao+OuCJLPL2gjAePt3Q/9RiNd8CAwEAAaMh\n" +
                "MB8wHQYDVR0OBBYEFCLOK+QGIinqSS2mjySNshAD9czsMA0GCSqGSIb3DQEBCwUA\n" +
                "A4IBAQAPdAzuIzmIsujnhjaOl2uJ7fdjp00xMX8zPaKXX2Nq2DIlaNghp0Oawr5D\n" +
                "wixma9MoFswkL/0N6tp3RRB4n9QIP5uDYOO3hrQN/CIu8KT/DsLHWSOChoiVBGlw\n" +
                "bnUy+l4Qhjox/JyEBHBbhACRig+ZSJrdplECSsqNJ7ji1Rp8KRa0SH5kJHdUblNk\n" +
                "WDx6Xzk52u/n9jNNordjQjKAcLWtFg/1DaUufUowVWLqBbEJL6qUdvyrPff52hlT\n" +
                "jqTdbmuHn9x8W7NeNU/EYzu8N6VS3R4+lJv6IEbx+s6Pa+sE3IxAvLx7EUfPVwkA\n" +
                "JzpWcFdXqoncCCeANXWRHh8gvPcR\n" +
                "-----END CERTIFICATE-----";

        return new Buffer()
                .writeUtf8(SmileServerCertificate)
                .inputStream();
    }

    private static X509TrustManager trustManagerForCertificates(InputStream in)
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        // Put the certificates a key store.
        char[] password = "78572smile".toCharArray(); // Any password will work.
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }

        // Use it to build an X509 trust manager.
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    private static KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
