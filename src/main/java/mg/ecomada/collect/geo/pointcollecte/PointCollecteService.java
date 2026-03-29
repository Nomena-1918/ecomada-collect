package mg.ecomada.collect.geo.pointcollecte;

import java.util.List;
import java.util.Map;

public interface PointCollecteService {
    List<PointCollecteDto> findAll();

    List<PointCollecteDto> findByActif(Boolean actif);

    PointCollecteDto findById(Long id);

    PointCollecteDto create(PointCollecteDto dto);

    PointCollecteDto update(Long id, PointCollecteDto dto);

    void delete(Long id);

    Map<String, Object> getStats(Long id);
}
