package com.itmo.backend.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordManager {
    public static String hashPassword(String password){
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }
    public static boolean checkPasswordHash(String password, String hash){
        return BCrypt.verifyer().verify(password.toCharArray(), hash).verified;
    }
}
