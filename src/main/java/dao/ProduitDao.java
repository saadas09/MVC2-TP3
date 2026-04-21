package dao;

import model.Produit;
import java.util.*;

public interface ProduitDao {

    public void addProduit(Produit p);
    
    public Produit getProduitById(Long id); 
    
    public List<Produit> getAllProduits();  
        public void updateProduit(Produit p);
    
    public void deleteProduit(Long id);  
    
  
}