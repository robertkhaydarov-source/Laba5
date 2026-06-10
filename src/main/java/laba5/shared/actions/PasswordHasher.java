package laba5.shared.actions;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {
    public static String hash(String password) {
        if (password == null) return "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);

            // Добиваем нулями слева, если хэш получился короче
            while (hashtext.length() < 64) {
                hashtext = "0" + hashtext;
            }

            // ИСПРАВЛЕНО: Принудительно переводим весь хэш в нижний регистр,
            // чтобы исключить разницу регистров на клиенте/сервере/БД
            return hashtext.toLowerCase();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ошибка алгоритма хэширования", e);
        }
    }
}