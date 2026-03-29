package mg.ecomada.collect.geo.quartier;

import java.util.List;

public interface QuartierService {
    List<QuartierDto> findAll();

    List<QuartierDto> findByCommuneId(Long communeId);

    QuartierDto findById(Long id);

    QuartierDto create(QuartierDto dto);

    QuartierDto update(Long id, QuartierDto dto);

    void delete(Long id);
}
