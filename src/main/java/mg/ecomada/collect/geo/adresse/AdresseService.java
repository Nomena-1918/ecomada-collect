package mg.ecomada.collect.geo.adresse;

import java.util.List;

public interface AdresseService {
    List<AdresseDto> findAll();

    AdresseDto findById(Long id);

    AdresseDto create(AdresseDto dto);

    AdresseDto update(Long id, AdresseDto dto);

    void delete(Long id);
}
