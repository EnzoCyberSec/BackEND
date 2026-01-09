package fr.isen.wokandroll.service.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import fr.isen.wokandroll.config.DatabaseConfig;
import fr.isen.wokandroll.model.Commande;
import fr.isen.wokandroll.service.CommandeService;

public class CommandeServiceImpl implements CommandeService {
    public Commande creerCommande(final Commande commande) {
//begin of modifiable zone................T/ab370940-2887-4b74-9b1f-156f00da7d3e
        final String SQL_INSERT =
                "INSERT INTO commande(date_commande, montant_total) VALUES (?, ?)";
        
        Commande result = commande;
        
        try (Connection connection = DriverManager.getConnection(
                DatabaseConfig.JDBC_URL,
                DatabaseConfig.JDBC_USER,
                DatabaseConfig.JDBC_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(
                     SQL_INSERT,
                     Statement.RETURN_GENERATED_KEYS)) {
        
            java.util.Date date = (commande.getDateCommande() != null)
                    ? commande.getDateCommande()
                    : new java.util.Date();
        
            stmt.setTimestamp(1, new Timestamp(date.getTime()));
            stmt.setDouble(2, commande.getMontantTotal());
        
            stmt.executeUpdate();
        
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    commande.setIdCommande(generatedId);
                }
            }
        
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la création de la commande", e);
        }
//end of modifiable zone..................E/ab370940-2887-4b74-9b1f-156f00da7d3e
//begin of modifiable zone................T/029d63a3-11f1-492e-b187-8732e657b335
        return result;
//end of modifiable zone..................E/029d63a3-11f1-492e-b187-8732e657b335
    }

    public Commande findById(final int id) {
//begin of modifiable zone................T/6c58f65a-eeb9-4f50-bb46-3abc11001371
        final String SQL_FIND_BY_ID =
                "SELECT id_commande, date_commande, montant_total " +
                        "FROM commande WHERE id_commande = ?";
        
        Commande result = null;
        
        try (Connection connection = DriverManager.getConnection(
                DatabaseConfig.JDBC_URL,
                DatabaseConfig.JDBC_USER,
                DatabaseConfig.JDBC_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(SQL_FIND_BY_ID)) {
        
            stmt.setInt(1, id);
        
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Commande c = new Commande();
                    c.setIdCommande(rs.getInt("id_commande"));
        
                    Timestamp ts = rs.getTimestamp("date_commande");
                    if (ts != null) {
                        c.setDateCommande(new java.util.Date(ts.getTime()));
                    }
        
                    c.setMontantTotal(rs.getDouble("montant_total"));
                    result = c;
                }
            }
        
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de la commande id=" + id, e);
        }
//end of modifiable zone..................E/6c58f65a-eeb9-4f50-bb46-3abc11001371
//begin of modifiable zone................T/e7c514f7-12ce-425a-a830-46b2f388ca60
        return result;
//end of modifiable zone..................E/e7c514f7-12ce-425a-a830-46b2f388ca60
    }

    public List<Commande> findAll() {
//begin of modifiable zone................T/e378bc46-f6e1-458d-aaa9-511696973ea2
        final String SQL_FIND_ALL =
                "SELECT id_commande, date_commande, montant_total " +
                        "FROM commande ORDER BY date_commande DESC";
        
        List<Commande> result = new ArrayList<>();
        
        try (Connection connection = DriverManager.getConnection(
                DatabaseConfig.JDBC_URL,
                DatabaseConfig.JDBC_USER,
                DatabaseConfig.JDBC_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = stmt.executeQuery()) {
        
            while (rs.next()) {
                Commande c = new Commande();
                c.setIdCommande(rs.getInt("id_commande"));
        
                Timestamp ts = rs.getTimestamp("date_commande");
                if (ts != null) {
                    c.setDateCommande(new java.util.Date(ts.getTime()));
                }
        
                c.setMontantTotal(rs.getDouble("montant_total"));
                result.add(c);
            }
        
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de toutes les commandes", e);
        }
//end of modifiable zone..................E/e378bc46-f6e1-458d-aaa9-511696973ea2
//begin of modifiable zone................T/208def0c-39f1-49a8-9e25-4c148274fda6
        return result;
//end of modifiable zone..................E/208def0c-39f1-49a8-9e25-4c148274fda6
    }

}
