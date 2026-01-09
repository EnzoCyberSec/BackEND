package fr.isen.wokandroll.service.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import fr.isen.wokandroll.config.DatabaseConfig;
import fr.isen.wokandroll.model.Categorie;
import fr.isen.wokandroll.service.CategorieService;

public class CategorieServiceImpl implements CategorieService {
    public List<Categorie> findAll() {
//begin of modifiable zone................T/e52991c0-245d-4270-8da5-ea0a49765f12
        List<Categorie> categories = new ArrayList<>();
        String sql = "SELECT id_categorie, nom FROM categorie";
        
        try (Connection conn = DriverManager.getConnection(
                DatabaseConfig.JDBC_URL,
                DatabaseConfig.JDBC_USER,
                DatabaseConfig.JDBC_PASSWORD
        );
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
        
            while (rs.next()) {
                Categorie c = new Categorie();
                c.setIdCategorie(rs.getInt("id_categorie"));
                c.setNom(rs.getString("nom"));
                categories.add(c);
            }
        
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du chargement des catégories", e);
        }
//end of modifiable zone..................E/e52991c0-245d-4270-8da5-ea0a49765f12
//begin of modifiable zone................T/ff2720cc-79fa-4052-a139-edf916309b79
        return categories;
//end of modifiable zone..................E/ff2720cc-79fa-4052-a139-edf916309b79
    }

    public Categorie findById(final int id) {
//begin of modifiable zone................T/7aefc69e-b214-4c07-9062-bb83fef9e7e6
        Categorie categorie = null;
        String sql = "SELECT id_categorie, nom FROM categorie WHERE id_categorie = ?";
        
        try (Connection conn = DriverManager.getConnection(
                DatabaseConfig.JDBC_URL,
                DatabaseConfig.JDBC_USER,
                DatabaseConfig.JDBC_PASSWORD
        );
             PreparedStatement ps = conn.prepareStatement(sql)) {
        
            ps.setInt(1, id);
        
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    categorie = new Categorie();
                    categorie.setIdCategorie(rs.getInt("id_categorie"));
                    categorie.setNom(rs.getString("nom"));
                }
            }
        
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de la catégorie id=" + id, e);
        }
//end of modifiable zone..................E/7aefc69e-b214-4c07-9062-bb83fef9e7e6
//begin of modifiable zone................T/bd772ae4-6aaa-41c1-97e0-f5c6e2bf97b6
        return categorie;
//end of modifiable zone..................E/bd772ae4-6aaa-41c1-97e0-f5c6e2bf97b6
    }

}
