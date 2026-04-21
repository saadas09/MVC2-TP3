package service;

import dao.ProduitDao;
import dao.ProduitDaoImpl;
import model.Produit;
import java.util.List;

public class ProduitService {
    
    private ProduitDao produitDao;
    
    public ProduitService() {
        this.produitDao = new ProduitDaoImpl();
    }
    
    public void addProduct(String nom, double prix) {
        if (nom == null || nom.trim().isEmpty()) {
            System.out.println(" fill the name");
            return;
        }
        if (prix < 0) {
            System.out.println("no nega");
            return;
        }
        
        Produit produit = new Produit(nom, prix);
        produitDao.addProduit(produit);
    }
    
    public Produit getProductById(Long id) {
        if (id == null || id <= 0) {
            System.out.println(" Invalid ID");
            return null;
        }
        return produitDao.getProduitById(id);
    }
    
    public List<Produit> getAllProduits() {
        return produitDao.getAllProduits();
    }
    
    public void updateProduct(Long id, String nom, double prix) {
        Produit produit = produitDao.getProduitById(id);
        if (produit == null) {
            System.out.println(" Error: Product not found with ID: " + id);
            return;
        }
        
        if (nom != null && !nom.trim().isEmpty()) {
            produit.setNom(nom);
        }
        if (prix >= 0) {
            produit.setPrix(prix);
        }
        
        produitDao.updateProduit(produit);
    }
    
    public void deleteProduct(Long id) {
        Produit produit = produitDao.getProduitById(id);
        if (produit == null) {
            System.out.println(" Erreur " + id);
            return;
        }
        produitDao.deleteProduit(id);
    }
    
    public void displayAllProducts() {
        List<Produit> products = produitDao.getAllProduits();
        if (products == null || products.isEmpty()) {
            System.out.println("empty");
            return;
        }
        
        System.out.println("\n List of Products:");
        System.out.println("----------------------------------------");
        for (Produit p : products) {
            System.out.println("ID: " + p.getId() + " | Name: " + p.getNom() + " | Price: $" + p.getPrix());
        }
        System.out.println("----------------------------------------");
        System.out.println("Total: " + products.size() + " products\n");
    }
}