package model;

public class Utilisateur {
    private Long id;
    private String nom;
    private String email;
    private String password;

    // Constructeur par défaut obligatoire pour Hibernate
    public Utilisateur() {
        super();
    }

    public Utilisateur(String nom, String email, String password) {
        this.nom = nom;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
