package fr.isen.wokandroll.service.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import fr.isen.wokandroll.config.DatabaseConfig;
import fr.isen.wokandroll.model.Option;
import fr.isen.wokandroll.service.OptionService;

public class OptionServiceImpl implements OptionService {
    public List<Option> findAll() {
//begin of modifiable zone................T/9117442d-0f82-4751-a350-edb016f311e7
        List<Option> options = new ArrayList<>();
        String sql = "SELECT id_option, libelle, type, prix FROM `option`";
        
        try (Connection conn = DriverManager.getConnection(
                DatabaseConfig.JDBC_URL,
                DatabaseConfig.JDBC_USER,
                DatabaseConfig.JDBC_PASSWORD
        );
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
        
            while (rs.next()) {
                Option opt = new Option();
                opt.setIdOption(rs.getInt("id_option"));
                opt.setLibelle(rs.getString("libelle"));
                opt.setType(rs.getString("type"));
                // adapte si ton attribut est BigDecimal au lieu de double
                opt.setPrix(rs.getDouble("prix"));
        
                options.add(opt);
            }
        
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du chargement des options", e);
        }
//end of modifiable zone..................E/9117442d-0f82-4751-a350-edb016f311e7
//begin of modifiable zone................T/81cd227f-3836-4465-b038-918ddb545e2e
        return options;
//end of modifiable zone..................E/81cd227f-3836-4465-b038-918ddb545e2e
    }

    public List<Option> findByPlat(final int platId) {
//begin of modifiable zone................T/cdfaf80e-0ddb-40a5-b48f-ac5b05d8132d
        List<Option> options = new ArrayList<>();
        
        String sql =
                "SELECT o.id_option, o.libelle, o.type, o.prix " +
                        "FROM `option` o " +
                        "JOIN avoir a ON a.id_option = o.id_option " +
                        "WHERE a.id_plat = ?";
        
        try (Connection conn = DriverManager.getConnection(
                DatabaseConfig.JDBC_URL,
                DatabaseConfig.JDBC_USER,
                DatabaseConfig.JDBC_PASSWORD
        );
             PreparedStatement ps = conn.prepareStatement(sql)) {
        
            ps.setInt(1, platId);
        
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Option opt = new Option();
                    opt.setIdOption(rs.getInt("id_option"));
                    opt.setLibelle(rs.getString("libelle"));
                    opt.setType(rs.getString("type"));
                    // adapte si ton attribut est BigDecimal au lieu de double
                    opt.setPrix(rs.getDouble("prix"));
        
                    options.add(opt);
                }
            }
        
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors du chargement des options pour le plat id=" + platId, e
            );
        }
//end of modifiable zone..................E/cdfaf80e-0ddb-40a5-b48f-ac5b05d8132d
//begin of modifiable zone................T/ee80ce6f-ccc1-41a9-9061-d49441d8427b
        return options;
//end of modifiable zone..................E/ee80ce6f-ccc1-41a9-9061-d49441d8427b
    }

}
