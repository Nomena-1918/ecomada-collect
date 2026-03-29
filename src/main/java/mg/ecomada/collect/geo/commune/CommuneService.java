package mg.ecomada.collect.geo.commune;

import java.util.List;

public interface CommuneService {
    List<CommuneDto> findAll();

    List<CommuneDto> findByVilleId(Long villeId);

    CommuneDto findById(Long id);

    CommuneDto create(CommuneDto dto);

    CommuneDto update(Long id, CommuneDto dto);

    void delete(Long id);
}
