package dao;

import model.Utilisateur;

public interface UtilisateurDao {

    void save(Utilisateur utilisateur);

    Utilisateur findByEmail(String email);

    boolean existsByEmail(String email);
}
