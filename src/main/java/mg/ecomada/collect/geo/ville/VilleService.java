package mg.ecomada.collect.geo.ville;

import java.util.List;

public interface VilleService {
    List<VilleDto> findAll();

    VilleDto findById(Long id);

    VilleDto create(VilleDto dto);

    VilleDto update(Long id, VilleDto dto);

    void delete(Long id);
}
