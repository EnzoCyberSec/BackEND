package fr.isen.wokandroll.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.isen.wokandroll.config.DatabaseConfig;
import fr.isen.wokandroll.model.Commande;
import fr.isen.wokandroll.model.LigneCommande;
import fr.isen.wokandroll.model.Option;
import fr.isen.wokandroll.model.Plat;

public class LigneCommandeServiceImpl implements LigneCommandeService {
    public LigneCommande creerLigneCommande(final LigneCommande ligneCommande) {
//begin of modifiable zone................T/a945a1f0-255c-44ff-aef7-7d2606ac9221
        final String SQL_INSERT_LIGNE =
                "INSERT INTO ligne_commande(quantite, prix_unitaire, id_commande, id_plat) " +
                        "VALUES (?, ?, ?, ?)";

        // Table de jointure ligne_commande / option
        // ⚠️ adapte le nom si différent dans ta BDD
        final String SQL_INSERT_OPTION =
                "INSERT INTO ligne_commande_option(id_ligne_commande, id_option) " +
                        "VALUES (?, ?)";

        LigneCommande result = ligneCommande;

        try (Connection connection = DriverManager.getConnection(
                DatabaseConfig.JDBC_URL,
                DatabaseConfig.JDBC_USER,
                DatabaseConfig.JDBC_PASSWORD)) {

            try {
                connection.setAutoCommit(false);

                // 1) Insert de la ligne de commande
                try (PreparedStatement stmt = connection.prepareStatement(
                        SQL_INSERT_LIGNE,
                        Statement.RETURN_GENERATED_KEYS)) {

                    // champs simples
                    stmt.setInt(1, ligneCommande.getQuantite());
                    stmt.setDouble(2, ligneCommande.getPrixUnitaire());

                    // id de la commande associée
                    int idCommande = 0;
                    if (ligneCommande.getCommande() != null) {
                        idCommande = ligneCommande.getCommande().getIdCommande();
                    }

                    // id du plat associé
                    int idPlat = 0;
                    if (ligneCommande.getPlat() != null) {
                        idPlat = ligneCommande.getPlat().getIdPlat();
                    }

                    stmt.setInt(3, idCommande);
                    stmt.setInt(4, idPlat);

                    stmt.executeUpdate();

                    // récupération de l'id auto‑généré
                    try (ResultSet rs = stmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            int generatedId = rs.getInt(1);
                            ligneCommande.setIdLigneCommande(generatedId);
                        }
                    }
                }

                // 2) Insert des options liées à cette ligne (si présentes)
                Option[] options = ligneCommande.getOptions();
                if (options != null && options.length > 0 && ligneCommande.getIdLigneCommande() != 0) {
                    try (PreparedStatement stmtOpt = connection.prepareStatement(SQL_INSERT_OPTION)) {
                        for (Option opt : options) {
                            if (opt == null) {
                                continue;
                            }
                            stmtOpt.setInt(1, ligneCommande.getIdLigneCommande());
                            stmtOpt.setInt(2, opt.getIdOption());
                            stmtOpt.addBatch();
                        }
                        stmtOpt.executeBatch();
                    }
                }

                connection.commit();

            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    // on loggue éventuellement, mais on remonte l'erreur principale
                    ex.printStackTrace();
                }
                throw new RuntimeException("Erreur lors de la création de la ligne de commande", e);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la création de la ligne de commande", e);
        }
//end of modifiable zone..................E/a945a1f0-255c-44ff-aef7-7d2606ac9221
//begin of modifiable zone................T/a8671eab-167c-4c2a-8906-a5fae1a4a777
        return result;
//end of modifiable zone..................E/a8671eab-167c-4c2a-8906-a5fae1a4a777
    }

    public List<LigneCommande> findByCommande(final int idCommande) {
//begin of modifiable zone................T/24b259f1-e302-41ff-8872-e2df13facbae
        final String SQL_FIND_BY_COMMANDE =
                "SELECT id_ligne_commande, quantite, prix_unitaire, id_commande, id_plat " +
                        "FROM ligne_commande WHERE id_commande = ?";

        // On récupère ici uniquement les id_option depuis la table de jointure
        // ⚠️ adapte le nom de table/champ si besoin
        final String SQL_FIND_OPTIONS_FOR_LIGNE =
                "SELECT id_option FROM ligne_commande_option WHERE id_ligne_commande = ?";

        List<LigneCommande> result = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(
                DatabaseConfig.JDBC_URL,
                DatabaseConfig.JDBC_USER,
                DatabaseConfig.JDBC_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(SQL_FIND_BY_COMMANDE)) {

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

                    // Récupération des options associées à cette ligne
                    List<Option> options = new ArrayList<>();
                    try (PreparedStatement stmtOpt = connection.prepareStatement(SQL_FIND_OPTIONS_FOR_LIGNE)) {
                        stmtOpt.setInt(1, lc.getIdLigneCommande());
                        try (ResultSet rsOpt = stmtOpt.executeQuery()) {
                            while (rsOpt.next()) {
                                Option opt = new Option();
                                opt.setIdOption(rsOpt.getInt("id_option"));
                                options.add(opt);
                            }
                        }
                    }

                    if (!options.isEmpty()) {
                        lc.setOptions(options.toArray(new Option[0]));
                    }

                    result.add(lc);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de la récupération des lignes pour la commande id=" + idCommande, e);
        }
//end of modifiable zone..................E/24b259f1-e302-41ff-8872-e2df13facbae
//begin of modifiable zone................T/251ac745-be90-49f8-9d21-98baa53c84b0
        return result;
//end of modifiable zone..................E/251ac745-be90-49f8-9d21-98baa53c84b0
    }

}
