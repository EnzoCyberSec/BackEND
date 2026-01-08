package fr.isen.wokandroll.service;

import java.util.List;
import fr.isen.wokandroll.model.LigneCommande;

public interface LigneCommandeService {
    LigneCommande creerLigneCommande(final LigneCommande ligneCommande);

    List<LigneCommande> findByCommande(final int idCommande);

}
