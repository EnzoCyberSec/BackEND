package fr.isen.wokandroll.service;

import java.util.List;
import fr.isen.wokandroll.model.Commande;

public interface CommandeService {
    Commande creerCommande(final Commande commande);

    Commande findById(final int id);

    List<Commande> findAll();

}
