package fr.isen.wokandroll.service;

import java.util.List;
import fr.isen.wokandroll.model.Plat;

public interface PlatService {
    List<Plat> findAll();

    List<Plat> findByCategorie(final int categorieId);

    Plat findById(final int id);

}
