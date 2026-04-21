<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion des Produits</title>
    <!-- Bootstrap CSS (très simple) -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f0f2f5;
            padding: 20px;
        }
        .container {
            max-width: 1200px;
            margin: auto;
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #2c3e50;
            margin-bottom: 30px;
        }
        .btn-group {
            gap: 5px;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="d-flex align-items-center justify-content-between mb-3">
        <h1 class="text-center flex-grow-1 m-0">📦 Gestion des Produits</h1>
        <a class="btn btn-outline-danger ms-3" href="${pageContext.request.contextPath}/auth?action=logout">
            Déconnexion
        </a>
    </div>
    
    <!-- ============================================ -->
    <!-- SECTION 1: FORMULAIRE POUR AJOUTER/MODIFIER -->
    <!-- ============================================ -->
    <div class="card mb-4">
        <div class="card-header bg-primary text-white">
            <c:choose>
                <c:when test="${not empty produitAModifier}">
                    ✏️ Modifier le produit
                </c:when>
                <c:otherwise>
                    ➕ Ajouter un nouveau produit
                </c:otherwise>
            </c:choose>
        </div>
        <div class="card-body">
            <form action="produit" method="post" class="row g-3">
                <c:if test="${not empty produitAModifier}">
                    <input type="hidden" name="id" value="${produitAModifier.id}">
                </c:if>
                
                <div class="col-md-8">
                    <label class="form-label">Nom du produit :</label>
                    <input type="text" name="nom" class="form-control" 
                           value="${produitAModifier.nom}" required>
                </div>
                
                <div class="col-md-4">
                    <label class="form-label">Prix (DH) :</label>
                    <input type="number" step="0.01" name="prix" class="form-control" 
                           value="${produitAModifier.prix}" required>
                </div>
                
                <div class="col-12">
                    <c:choose>
                        <c:when test="${not empty produitAModifier}">
                            <input type="hidden" name="action" value="update">
                            <button type="submit" class="btn btn-warning"> Mettre à jour</button>
                            <a href="produit?action=list" class="btn btn-secondary"> Annuler</a>
                        </c:when>
                        <c:otherwise>
                            <input type="hidden" name="action" value="add">
                            <button type="submit" class="btn btn-success"> Ajouter</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </form>
        </div>
    </div>
    
    <!-- ============================================ -->
    <!-- SECTION 2: LISTE DES PRODUITS -->
    <!-- ============================================ -->
    <div class="card">
        <div class="card-header bg-secondary text-white">
             Liste des produits
        </div>
        <div class="card-body">
            <c:choose>
                <c:when test="${empty produits}">
                    <div class="alert alert-warning text-center">
                        Aucun produit trouvé. Ajoutez votre premier produit !
                    </div>
                </c:when>
                <c:otherwise>
                    <table class="table table-bordered table-hover">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Nom</th>
                                <th>Prix (DH)</th>
                                <th style="width: 150px">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="produit" items="${produits}">
                                <tr>
                                    <td>${produit.id}</td>
                                    <td>${produit.nom}</td>
                                    <td>${produit.prix} DH</td>
                                    <td>
                                        <div class="btn-group btn-group-sm">
                                            <a href="produit?action=edit&id=${produit.id}" 
                                               class="btn btn-warning">✏️</a>
                                            <a href="produit?action=delete&id=${produit.id}" 
                                               class="btn btn-danger"
                                               onclick="return confirm('Supprimer ${produit.nom} ?')">🗑️</a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    
                    <div class="alert alert-info text-center">
                        Total : ${produits.size()} produit(s)
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>