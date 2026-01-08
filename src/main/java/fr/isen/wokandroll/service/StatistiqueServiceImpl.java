package fr.isen.wokandroll.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import fr.isen.wokandroll.config.DatabaseConfig;
import fr.isen.wokandroll.model.Categorie;
import fr.isen.wokandroll.model.Plat;

public class StatistiqueServiceImpl implements StatistiqueService {
    public long compterCommandes() {
//begin of modifiable zone(JavaCode)......C/efb55ea7-7330-4ac8-b13f-e5558c4b3e25
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
//end of modifiable zone(JavaCode)........E/efb55ea7-7330-4ac8-b13f-e5558c4b3e25
//begin of modifiable zone(JavaReturned)..C/efb55ea7-7330-4ac8-b13f-e5558c4b3e25
        return count;
//end of modifiable zone(JavaReturned)....E/efb55ea7-7330-4ac8-b13f-e5558c4b3e25
    }

    public double calculerPanierMoyen() {
//begin of modifiable zone(JavaCode)......C/c3b59172-18f9-4d76-b4c6-90edd3b856dc
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
//end of modifiable zone(JavaCode)........E/c3b59172-18f9-4d76-b4c6-90edd3b856dc
//begin of modifiable zone(JavaReturned)..C/c3b59172-18f9-4d76-b4c6-90edd3b856dc
        return moyen;
//end of modifiable zone(JavaReturned)....E/c3b59172-18f9-4d76-b4c6-90edd3b856dc
    }

    public List<Plat> trouverPlatsLesPlusVendus(final int limit) {
//begin of modifiable zone(JavaCode)......C/b823cbaf-ec58-4e80-889a-7ea8e09cf081
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

//end of modifiable zone(JavaCode)........E/b823cbaf-ec58-4e80-889a-7ea8e09cf081
//begin of modifiable zone(JavaReturned)..C/b823cbaf-ec58-4e80-889a-7ea8e09cf081
        return plats;
//end of modifiable zone(JavaReturned)....E/b823cbaf-ec58-4e80-889a-7ea8e09cf081
    }

}
