package web;

import model.Utilisateur;
import service.UtilisateurService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

    private UtilisateurService utilisateurService;

    @Override
    public void init() {
        utilisateurService = new UtilisateurService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null || action.trim().isEmpty()) {
            action = "login";
        }

        switch (action) {
            case "signup":
                request.getRequestDispatcher("/jsp/signup.jsp").forward(request, response);
                break;
            case "logout":
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.invalidate();
                }
                response.sendRedirect(request.getContextPath() + "/auth?action=login");
                break;
            case "login":
            default:
                request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null || action.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/auth?action=login");
            return;
        }

        switch (action) {
            case "login":
                handleLogin(request, response);
                break;
            case "signup":
                handleSignup(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/auth?action=login");
                break;
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.trim().isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("error", "Veuillez remplir l'email et le mot de passe.");
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
            return;
        }

        Utilisateur utilisateur = utilisateurService.login(email, password);
        if (utilisateur == null) {
            request.setAttribute("error", "Email ou mot de passe incorrect.");
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("utilisateur", utilisateur);

        response.sendRedirect(request.getContextPath() + "/produit?action=list");
    }

    private void handleSignup(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nom = request.getParameter("nom");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmation = request.getParameter("confirmation");

        if (nom == null || nom.trim().isEmpty() || email == null || email.trim().isEmpty()) {
            request.setAttribute("error", "Veuillez remplir le nom et l'email.");
            request.getRequestDispatcher("/jsp/signup.jsp").forward(request, response);
            return;
        }

        if (password == null || password.isEmpty() || confirmation == null || confirmation.isEmpty()) {
            request.setAttribute("error", "Veuillez saisir et confirmer votre mot de passe.");
            request.getRequestDispatcher("/jsp/signup.jsp").forward(request, response);
            return;
        }

        if (!password.equals(confirmation)) {
            request.setAttribute("error", "Les mots de passe ne correspondent pas.");
            request.getRequestDispatcher("/jsp/signup.jsp").forward(request, response);
            return;
        }

        boolean registered = utilisateurService.register(nom, email, password);
        if (!registered) {
            request.setAttribute("error", "Email déjà utilisé (ou données invalides).");
            request.getRequestDispatcher("/jsp/signup.jsp").forward(request, response);
            return;
        }

        // Connexion automatique après inscription
        Utilisateur utilisateur = utilisateurService.login(email, password);
        if (utilisateur != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("utilisateur", utilisateur);
            response.sendRedirect(request.getContextPath() + "/produit?action=list");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/auth?action=login");
    }
}
