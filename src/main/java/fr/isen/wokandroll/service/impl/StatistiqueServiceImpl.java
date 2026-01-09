package fr.isen.wokandroll.service.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import fr.isen.wokandroll.config.DatabaseConfig;
import fr.isen.wokandroll.model.Categorie;
import fr.isen.wokandroll.model.Plat;
import fr.isen.wokandroll.service.StatistiqueService;

public class StatistiqueServiceImpl implements StatistiqueService {
    public long compterCommandes() {
//begin of modifiable zone................T/18b20ac7-34a8-409a-b01f-44666b992f52
        long count = 0;
        String sql = "SELECT COUNT(*) AS total FROM commande";
        
        try (Connection conn = DriverManager.getConnection(
                DatabaseConfig.JDBC_URL,
                DatabaseConfig.JDBC_USER,
                DatabaseConfig.JDBC_PASSWORD
        );
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
        
            if (rs.next()) {
                count = rs.getLong("total");
            }
        
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du comptage des commandes", e);
        }
//end of modifiable zone..................E/18b20ac7-34a8-409a-b01f-44666b992f52
//begin of modifiable zone................T/139dc019-a0f3-4a8b-a926-d36bc2943c43
        return count;
//end of modifiable zone..................E/139dc019-a0f3-4a8b-a926-d36bc2943c43
    }

    public double calculerPanierMoyen() {
//begin of modifiable zone................T/6045e90f-3fc4-4861-a6ad-24d58a51e264
        double moyen = 0.0;
        String sql = "SELECT AVG(montant_total) AS moyenne FROM commande";
        
        try (Connection conn = DriverManager.getConnection(
                DatabaseConfig.JDBC_URL,
                DatabaseConfig.JDBC_USER,
                DatabaseConfig.JDBC_PASSWORD
        );
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
        
            if (rs.next()) {
                moyen = rs.getDouble("moyenne");
            }
        
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du calcul du panier moyen", e);
        }
//end of modifiable zone..................E/6045e90f-3fc4-4861-a6ad-24d58a51e264
//begin of modifiable zone................T/2cf014af-00c9-463b-b365-1bfd68216363
        return moyen;
//end of modifiable zone..................E/2cf014af-00c9-463b-b365-1bfd68216363
    }

    public List<Plat> trouverPlatsLesPlusVendus(final int limit) {
//begin of modifiable zone................T/3b161d46-008d-4839-b0b7-fa790f677170
        List<Plat> plats = new ArrayList<>();
        String sql = "SELECT p.id_plat, p.nom, p.description, p.prix, p.disponible, " +
                "c.id_categorie, c.nom AS categorie_nom, " +
                "SUM(lc.quantite) as total_ventes " +
                "FROM ligne_commande lc " +
                "JOIN plat p ON lc.id_plat = p.id_plat " +
                "JOIN categorie c ON p.id_categorie = c.id_categorie " +
                "GROUP BY p.id_plat " +
                "ORDER BY total_ventes DESC " +
                "LIMIT ?";
        
        try (Connection conn = DriverManager.getConnection(
                DatabaseConfig.JDBC_URL,
                DatabaseConfig.JDBC_USER,
                DatabaseConfig.JDBC_PASSWORD
        );
             PreparedStatement ps = conn.prepareStatement(sql)) {
        
            ps.setInt(1, limit);
        
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
            throw new RuntimeException("Erreur lors de la récupération des plats les plus vendus", e);
        }
//end of modifiable zone..................E/3b161d46-008d-4839-b0b7-fa790f677170
//begin of modifiable zone................T/dec48520-abb6-4a8d-a2c7-d2f8122f4d80
        return plats;
//end of modifiable zone..................E/dec48520-abb6-4a8d-a2c7-d2f8122f4d80
    }

}
