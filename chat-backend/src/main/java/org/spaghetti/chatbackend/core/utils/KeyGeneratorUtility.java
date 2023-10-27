package org.spaghetti.chatbackend.core.utils;

import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAKey;

public class KeyGeneratorUtility {
    public static KeyPair generateRSAKeyPair(){
        KeyPair keyPair;
        try{
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        }catch (Exception exception){
            throw new IllegalStateException();
        }
        return keyPair;
    }
}
