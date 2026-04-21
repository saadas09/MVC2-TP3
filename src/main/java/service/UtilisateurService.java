package service;

import dao.UtilisateurDao;
import dao.UtilisateurDaoImpl;
import model.Utilisateur;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class UtilisateurService {

    private final UtilisateurDao utilisateurDao;

    public UtilisateurService() {
        this.utilisateurDao = new UtilisateurDaoImpl();
    }

    public Utilisateur login(String email, String password) {
        if (email == null || email.trim().isEmpty() || password == null) {
            return null;
        }

        Utilisateur utilisateur = utilisateurDao.findByEmail(email.trim());
        if (utilisateur == null) {
            return null;
        }

        if (!verifyPassword(password, utilisateur.getPassword())) {
            return null;
        }

        return utilisateur;
    }

    public boolean register(String nom, String email, String password) {
        if (nom == null || nom.trim().isEmpty()) {
            return false;
        }
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        if (password == null || password.isEmpty()) {
            return false;
        }

        String normalizedEmail = email.trim();
        if (utilisateurDao.existsByEmail(normalizedEmail)) {
            return false;
        }

        String hashedPassword = hashPassword(password);
        Utilisateur utilisateur = new Utilisateur(nom.trim(), normalizedEmail, hashedPassword);
        utilisateurDao.save(utilisateur);
        return utilisateurDao.findByEmail(normalizedEmail) != null;
    }

    // ============================
    // Password hashing (PBKDF2)
    // Format: iterations:saltBase64:hashBase64
    // ============================

    private static final int PBKDF2_ITERATIONS = 65536;
    private static final int SALT_BYTES = 16;
    private static final int KEY_LENGTH_BITS = 256;

    private static String hashPassword(String password) {
        byte[] salt = new byte[SALT_BYTES];
        new SecureRandom().nextBytes(salt);

        byte[] hash = pbkdf2(password.toCharArray(), salt, PBKDF2_ITERATIONS, KEY_LENGTH_BITS);

        return PBKDF2_ITERATIONS + ":" +
                Base64.getEncoder().encodeToString(salt) + ":" +
                Base64.getEncoder().encodeToString(hash);
    }

    private static boolean verifyPassword(String rawPassword, String stored) {
        if (rawPassword == null || stored == null) {
            return false;
        }

        try {
            String[] parts = stored.split(":");
            if (parts.length != 3) {
                // fallback: si tu avais déjà des mots de passe en clair, ça permet une transition
                return stored.equals(rawPassword);
            }

            int iterations = Integer.parseInt(parts[0]);
            byte[] salt = Base64.getDecoder().decode(parts[1]);
            byte[] expectedHash = Base64.getDecoder().decode(parts[2]);

            byte[] actualHash = pbkdf2(rawPassword.toCharArray(), salt, iterations, expectedHash.length * 8);

            return constantTimeEquals(expectedHash, actualHash);
        } catch (Exception e) {
            return false;
        }
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int keyLengthBits) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLengthBits);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            throw new RuntimeException("Error while hashing a password", e);
        }
    }

    private static boolean constantTimeEquals(byte[] a, byte[] b) {
        if (a == null || b == null || a.length != b.length) {
            return false;
        }
        int result = 0;
        for (int i = 0; i < a.length; i++) {
            result |= a[i] ^ b[i];
        }
        return result == 0;
    }
}
