package fr.isen.wokandroll.service;

import java.util.ArrayList;
import java.util.List;

import fr.isen.wokandroll.config.DatabaseConfig;
import fr.isen.wokandroll.model.Commande;
import fr.isen.wokandroll.model.LigneCommande;
import fr.isen.wokandroll.model.Plat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LigneCommandeServiceImpl implements LigneCommandeService {

    @Override
    public LigneCommande creerLigneCommande(final LigneCommande ligneCommande) {
//begin of modifiable zone(JavaCode)......C/e8b85dbf-770f-4472-8770-07d446bd822c
        final String SQL_INSERT =
                "INSERT INTO ligne_commande (quantite, prix_unitaire, id_commande, id_plat) " +
                        "VALUES (?, ?, ?, ?)";

        // On renverra cet objet (potentiellement enrichi avec l'id généré)
        LigneCommande result = ligneCommande;

        try (Connection connection = DriverManager.getConnection(
                DatabaseConfig.JDBC_URL,
                DatabaseConfig.JDBC_USER,
                DatabaseConfig.JDBC_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(
                     SQL_INSERT,
                     Statement.RETURN_GENERATED_KEYS)) {

            // Champs simples
            stmt.setInt(1, ligneCommande.getQuantite());
            stmt.setDouble(2, ligneCommande.getPrixUnitaire());

            // Id de la commande associée
            if (ligneCommande.getCommande() == null) {
                throw new IllegalArgumentException("La commande associée ne peut pas être null");
            }
            stmt.setInt(3, ligneCommande.getCommande().getIdCommande());

            // Id du plat associé
            if (ligneCommande.getPlat() == null) {
                throw new IllegalArgumentException("Le plat associé ne peut pas être null");
            }
            stmt.setInt(4, ligneCommande.getPlat().getIdPlat());

            // Exécution
            stmt.executeUpdate();

            // Récupération de la clé générée (id_ligne_commande)
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    result.setIdLigneCommande(generatedId);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la création de la ligne de commande", e);
        }
//end of modifiable zone(JavaCode)........E/e8b85dbf-770f-4472-8770-07d446bd822c
//begin of modifiable zone(JavaReturned)..C/e8b85dbf-770f-4472-8770-07d446bd822c
        return result;
//end of modifiable zone(JavaReturned)....E/e8b85dbf-770f-4472-8770-07d446bd822c
    }

    @Override
    public List<LigneCommande> findByCommande(final int idCommande) {
//begin of modifiable zone(JavaCode)......C/665a0611-8e2b-4811-8fd0-6297c4442759
        final String SQL_SELECT =
                "SELECT id_ligne_commande, quantite, prix_unitaire, id_commande, id_plat " +
                        "FROM ligne_commande WHERE id_commande = ?";

        List<LigneCommande> result = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(
                DatabaseConfig.JDBC_URL,
                DatabaseConfig.JDBC_USER,
                DatabaseConfig.JDBC_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(SQL_SELECT)) {

            stmt.setInt(1, idCommande);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    LigneCommande lc = new LigneCommande();
                    lc.setIdLigneCommande(rs.getInt("id_ligne_commande"));
                    lc.setQuantite(rs.getInt("quantite"));
                    lc.setPrixUnitaire(rs.getDouble("prix_unitaire"));

                    // Commande associée : on met au moins l'id
                    Commande c = new Commande();
                    c.setIdCommande(rs.getInt("id_commande"));
                    lc.setCommande(c);

                    // Plat associé : on met au moins l'id
                    Plat p = new Plat();
                    p.setIdPlat(rs.getInt("id_plat"));
                    lc.setPlat(p);

                    // options[] non gérées ici (table séparée probablement)
                    result.add(lc);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de la récupération des lignes pour la commande id=" + idCommande, e);
        }
//end of modifiable zone(JavaCode)........E/665a0611-8e2b-4811-8fd0-6297c4442759
//begin of modifiable zone(JavaReturned)..C/665a0611-8e2b-4811-8fd0-6297c4442759
        return result;
//end of modifiable zone(JavaReturned)....E/665a0611-8e2b-4811-8fd0-6297c4442759
    }
}
