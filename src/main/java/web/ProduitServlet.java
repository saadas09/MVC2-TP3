package web;

import model.Produit;
import service.ProduitService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/produit")
public class ProduitServlet extends HttpServlet {
    
    private ProduitService produitService;
    
    @Override
    public void init() {
        produitService = new ProduitService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utilisateur") == null) {
            response.sendRedirect(request.getContextPath() + "/auth?action=login");
            return;
        }
        
        String action = request.getParameter("action");
        
        if (action == null || action.equals("list")) {
            // ✅ CORRECTION ICI : getAllProduits() au lieu de getAllProducts()
            List<Produit> produits = produitService.getAllProduits();  // ← Changé ici
            request.setAttribute("produits", produits);
            request.getRequestDispatcher("/jsp/gestionProduits.jsp").forward(request, response);
            
        } else if (action.equals("edit")) {
            Long id = Long.parseLong(request.getParameter("id"));
            Produit produit = produitService.getProductById(id);
            // ✅ CORRECTION ICI AUSSI
            List<Produit> produits = produitService.getAllProduits();  // ← Changé ici
            
            request.setAttribute("produitAModifier", produit);
            request.setAttribute("produits", produits);
            request.getRequestDispatcher("/jsp/gestionProduits.jsp").forward(request, response);
            
        } else if (action.equals("delete")) {
            Long id = Long.parseLong(request.getParameter("id"));
            produitService.deleteProduct(id);
            response.sendRedirect("produit?action=list");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utilisateur") == null) {
            response.sendRedirect(request.getContextPath() + "/auth?action=login");
            return;
        }
        
        String action = request.getParameter("action");
        
        if (action.equals("add")) {
            String nom = request.getParameter("nom");
            double prix = Double.parseDouble(request.getParameter("prix"));
            produitService.addProduct(nom, prix);
            
        } else if (action.equals("update")) {
            Long id = Long.parseLong(request.getParameter("id"));
            String nom = request.getParameter("nom");
            double prix = Double.parseDouble(request.getParameter("prix"));
            produitService.updateProduct(id, nom, prix);
        }
        
        response.sendRedirect("produit?action=list");
    }
}