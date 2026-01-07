package fr.isen.wokandroll.service;

import java.util.List;
import fr.isen.wokandroll.model.Plat;

public interface StatistiqueService {
    long compterCommandes();

    double calculerPanierMoyen();

    List<Plat> trouverPlatsLesPlusVendus(final int limit);

}
