package fr.isen.wokandroll.service;

import java.util.List;
import fr.isen.wokandroll.model.Option;

public interface OptionService {
    List<Option> findAll();

    List<Option> findByPlat(final int platId);

}
