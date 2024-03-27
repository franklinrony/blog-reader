/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.gob.bandesal.blog.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import org.wildfly.security.WildFlyElytronProvider;
import org.wildfly.security.password.Password;
import org.wildfly.security.password.PasswordFactory;
import org.wildfly.security.password.interfaces.BCryptPassword;
import org.wildfly.security.password.spec.EncryptablePasswordSpec;
import org.wildfly.security.password.spec.IteratedSaltedPasswordAlgorithmSpec;
import org.wildfly.security.password.util.ModularCrypt;

/**
 *
 * @author cash america
 */
public class Elytron {

    /**
     *
     * @param password Password en texto plano como cadena
     * @return hashed password con bcrypt y salt
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public static String hashGenerator(String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        final Provider ELYTRON_PROVIDER = new WildFlyElytronProvider();
        PasswordFactory passwordFactory = PasswordFactory.getInstance(BCryptPassword.ALGORITHM_BCRYPT, ELYTRON_PROVIDER);

        int iterationCount = 10;

        byte[] salt = new byte[BCryptPassword.BCRYPT_SALT_SIZE];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);

        IteratedSaltedPasswordAlgorithmSpec iteratedAlgorithmSpec = new IteratedSaltedPasswordAlgorithmSpec(iterationCount, salt);
        EncryptablePasswordSpec encryptableSpec = new EncryptablePasswordSpec(password.toCharArray(), iteratedAlgorithmSpec);

        BCryptPassword original = (BCryptPassword) passwordFactory.generatePassword(encryptableSpec);
        String modularCryptString = ModularCrypt.encodeAsString(original);

        return modularCryptString;
    }

    /**
     *
     * @param password password a validar en texto plano
     * @param hashedPassword hash del password
     * @return true si coinciden el hash y el password ingresado
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     */
    public static boolean validatePassword(String password, String hashedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        final Provider ELYTRON_PROVIDER = new WildFlyElytronProvider();
        PasswordFactory passwordFactory = PasswordFactory.getInstance(BCryptPassword.ALGORITHM_BCRYPT, ELYTRON_PROVIDER);
        Password hashed = passwordFactory.translate(ModularCrypt.decode(hashedPassword));
        return passwordFactory.verify(hashed, password.toCharArray());
    }

}
