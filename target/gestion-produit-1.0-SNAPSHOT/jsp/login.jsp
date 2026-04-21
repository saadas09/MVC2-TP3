<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f0f2f5; padding: 20px; }
        .container { max-width: 520px; margin: auto; background: white; padding: 25px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
    </style>
</head>
<body>

<div class="container">
    <h2 class="text-center mb-4">Connexion</h2>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/auth" class="row g-3">
        <input type="hidden" name="action" value="login" />

        <div class="col-12">
            <label class="form-label">Email</label>
            <input type="email" name="email" class="form-control" required value="${param.email}" />
        </div>

        <div class="col-12">
            <label class="form-label">Mot de passe</label>
            <input type="password" name="password" class="form-control" required />
        </div>

        <div class="col-12 d-grid">
            <button type="submit" class="btn btn-primary">Se connecter</button>
        </div>
    </form>

    <div class="mt-3 text-center">
        <span>Pas de compte ? </span>
        <a href="${pageContext.request.contextPath}/auth?action=signup">Créer un compte</a>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
