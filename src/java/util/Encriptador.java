package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utilidad para encriptar contraseñas con SHA-256
 * Mismo algoritmo que usa MySQL: SHA2('texto', 256)
 * AulaVirtual — GA7-220501096-AA2-EV02
 */
public class Encriptador {

    /**
     * Convierte un texto plano a SHA-256 en hexadecimal.
     * Equivale a SHA2(texto, 256) de MySQL.
     * @param texto contraseña en texto plano
     * @return hash SHA-256 en minúsculas
     */
    public static String sha256(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(texto.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            throw new RuntimeException("Error al encriptar: " + e.getMessage());
        }
    }
}
