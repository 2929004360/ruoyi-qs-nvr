package com.ruoyi.gb28181.auth;

import gov.nist.javax.sip.message.SIPRequest;
import lombok.extern.slf4j.Slf4j;

import javax.sip.address.URI;
import javax.sip.header.AuthorizationHeader;
import javax.sip.header.HeaderFactory;
import javax.sip.header.WWWAuthenticateHeader;
import javax.sip.message.Request;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Slf4j
public class DigestClientAuthenticationHelper {

    private final MessageDigest messageDigest;
    private static final String DEFAULT_ALGORITHM = "MD5";
    private static final String DEFAULT_SCHEME = "Digest";
    private static final char[] toHex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final Random random = new Random();
    private long nonceCount = 1;

    public DigestClientAuthenticationHelper() throws NoSuchAlgorithmException {
        messageDigest = MessageDigest.getInstance(DEFAULT_ALGORITHM);
    }

    public static String toHexString(byte[] b) {
        int pos = 0;
        char[] c = new char[b.length * 2];
        for (byte value : b) {
            c[pos++] = toHex[(value >> 4) & 0x0F];
            c[pos++] = toHex[value & 0x0f];
        }
        return new String(c);
    }

    private String generateCNonce() {
        byte[] bytes = new byte[8];
        random.nextBytes(bytes);
        return toHexString(bytes);
    }

    private String getNextNonceCount() {
        return String.format("%08x", nonceCount++);
    }

    public AuthorizationHeader createAuthorizationHeader(HeaderFactory headerFactory,
                                                           Request request,
                                                           WWWAuthenticateHeader wwwAuthenticate,
                                                           String username,
                                                           String password) throws Exception {

        String realm = wwwAuthenticate.getRealm();
        String nonce = wwwAuthenticate.getNonce();
        String algorithm = wwwAuthenticate.getAlgorithm();
        String qop = wwwAuthenticate.getQop();
        String uri = request.getRequestURI().toString();

        String cnonce = generateCNonce();
        String nc = getNextNonceCount();

        String A1 = username + ":" + realm + ":" + password;
        String A2 = request.getMethod().toUpperCase() + ":" + uri;

        String HA1 = toHexString(messageDigest.digest(A1.getBytes()));
        String HA2 = toHexString(messageDigest.digest(A2.getBytes()));

        String response;
        if (qop != null && qop.equalsIgnoreCase("auth")) {
            String KD = HA1 + ":" + nonce + ":" + nc + ":" + cnonce + ":" + qop + ":" + HA2;
            response = toHexString(messageDigest.digest(KD.getBytes()));
        } else {
            String KD = HA1 + ":" + nonce + ":" + HA2;
            response = toHexString(messageDigest.digest(KD.getBytes()));
        }

        AuthorizationHeader authHeader = headerFactory.createAuthorizationHeader(DEFAULT_SCHEME);
        authHeader.setUsername(username);
        authHeader.setRealm(realm);
        authHeader.setNonce(nonce);
        authHeader.setURI(request.getRequestURI());
        authHeader.setResponse(response);

        if (algorithm != null) {
            authHeader.setAlgorithm(algorithm);
        }
        if (qop != null) {
            authHeader.setQop(qop);
            authHeader.setCNonce(cnonce);
            authHeader.setNonceCount(Integer.parseInt(nc, 16));
        }

        log.debug("Authorization Header created for user: {}", username);
        return authHeader;
    }

    public void resetNonceCount() {
        this.nonceCount = 1;
    }
}
