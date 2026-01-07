package fr.isen.wokandroll.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import fr.isen.wokandroll.config.DatabaseConfig;
import fr.isen.wokandroll.model.Categorie;
import fr.isen.wokandroll.model.Plat;

public class PlatServiceImpl implements PlatService {
    public List<Plat> findAll() {
//begin of modifiable zone................T/ae5aaf1d-dda5-4635-85a4-b186f4e1224e
        List<Plat> plats = new ArrayList<>();
        
        String sql = "SELECT p.id_plat, p.nom, p.description, p.prix, p.disponible, " +
                "c.id_categorie, c.nom AS categorie_nom " +
                "FROM plat p " +
                "JOIN categorie c ON p.id_categorie = c.id_categorie";
        
        try (Connection conn = DriverManager.getConnection(
                DatabaseConfig.JDBC_URL,
                DatabaseConfig.JDBC_USER,
                DatabaseConfig.JDBC_PASSWORD
        );
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
        
            while (rs.next()) {
                Plat plat = new Plat();
                plat.setIdPlat(rs.getInt("id_plat"));
                plat.setNom(rs.getString("nom"));
                plat.setDescription(rs.getString("description"));
                plat.setPrix(rs.getDouble("prix"));
                plat.setDisponible(rs.getBoolean("disponible"));
        
                Categorie categorie = new Categorie();
                categorie.setIdCategorie(rs.getInt("id_categorie"));
                categorie.setNom(rs.getString("categorie_nom"));
                plat.setCategorie(categorie);
        
                plats.add(plat);
            }
        
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du chargement des plats", e);
        }
//end of modifiable zone..................E/ae5aaf1d-dda5-4635-85a4-b186f4e1224e
//begin of modifiable zone................T/034a57c2-f8c8-42d9-90ee-a2738c08da67
        return plats;
//end of modifiable zone..................E/034a57c2-f8c8-42d9-90ee-a2738c08da67
    }

    public List<Plat> findByCategorie(final int categorieId) {
//begin of modifiable zone................T/adb353bb-6b09-4ad3-be93-9b032699f6b2
        List<Plat> plats = new ArrayList<>();
        
        String sql = "SELECT p.id_plat, p.nom, p.description, p.prix, p.disponible, " +
                "c.id_categorie, c.nom AS categorie_nom " +
                "FROM plat p " +
                "JOIN categorie c ON p.id_categorie = c.id_categorie " +
                "WHERE c.id_categorie = ?";
        
        try (Connection conn = DriverManager.getConnection(
                DatabaseConfig.JDBC_URL,
                DatabaseConfig.JDBC_USER,
                DatabaseConfig.JDBC_PASSWORD
        );
             PreparedStatement ps = conn.prepareStatement(sql)) {
        
            ps.setInt(1, categorieId);
        
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Plat plat = new Plat();
                    plat.setIdPlat(rs.getInt("id_plat"));
                    plat.setNom(rs.getString("nom"));
                    plat.setDescription(rs.getString("description"));
                    plat.setPrix(rs.getDouble("prix"));
                    plat.setDisponible(rs.getBoolean("disponible"));
        
                    Categorie categorie = new Categorie();
                    categorie.setIdCategorie(rs.getInt("id_categorie"));
                    categorie.setNom(rs.getString("categorie_nom"));
                    plat.setCategorie(categorie);
        
                    plats.add(plat);
                }
            }
        
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du chargement des plats pour la catégorie id=" + categorieId, e);
        }
//end of modifiable zone..................E/adb353bb-6b09-4ad3-be93-9b032699f6b2
//begin of modifiable zone................T/96ae1bec-cd84-4f66-a4fc-e3ea10ca51fe
        return plats;
//end of modifiable zone..................E/96ae1bec-cd84-4f66-a4fc-e3ea10ca51fe
    }

    public Plat findById(final int id) {
//begin of modifiable zone................T/3a728ce7-447d-48b8-821d-c3e9a40749f5
        Plat plat = null;
        
        String sql = "SELECT p.id_plat, p.nom, p.description, p.prix, p.disponible, " +
                "c.id_categorie, c.nom AS categorie_nom " +
                "FROM plat p " +
                "JOIN categorie c ON p.id_categorie = c.id_categorie " +
                "WHERE p.id_plat = ?";
        
        try (Connection conn = DriverManager.getConnection(
                DatabaseConfig.JDBC_URL,
                DatabaseConfig.JDBC_USER,
                DatabaseConfig.JDBC_PASSWORD
        );
             PreparedStatement ps = conn.prepareStatement(sql)) {
        
            ps.setInt(1, id);
        
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    plat = new Plat();
                    plat.setIdPlat(rs.getInt("id_plat"));
                    plat.setNom(rs.getString("nom"));
                    plat.setDescription(rs.getString("description"));
                    plat.setPrix(rs.getDouble("prix"));
                    plat.setDisponible(rs.getBoolean("disponible"));
        
                    Categorie categorie = new Categorie();
                    categorie.setIdCategorie(rs.getInt("id_categorie"));
                    categorie.setNom(rs.getString("categorie_nom"));
                    plat.setCategorie(categorie);
                }
            }
        
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche du plat id=" + id, e);
        }
//end of modifiable zone..................E/3a728ce7-447d-48b8-821d-c3e9a40749f5
//begin of modifiable zone................T/a2d1cb25-b526-41cf-acdd-13a314871bcd
        return plat;
//end of modifiable zone..................E/a2d1cb25-b526-41cf-acdd-13a314871bcd
    }

}
