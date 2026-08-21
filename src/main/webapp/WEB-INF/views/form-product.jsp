<%@ page isELIgnored="false" %>


<form action="${pageContext.request.contextPath}/products" method="post">
    <input type="hidden" name="id" value="${product.id}" />
    <input type="text" name="libelle" value="${product.libelle}" />
    <input type="number" name="prix" value="${product.prix}" />
    <button type="submit">Enregistrer</button>
</form>