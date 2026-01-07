package fr.isen.wokandroll.service;

import java.util.List;
import fr.isen.wokandroll.model.Categorie;

public interface CategorieService {
    List<Categorie> findAll();

    Categorie findById(final int id);

}
