package mg.ecomada.collect.geo.pointcollecte;

import lombok.RequiredArgsConstructor;
import mg.ecomada.collect.common.exception.ResourceNotFoundException;
import mg.ecomada.collect.depot.Depot;
import mg.ecomada.collect.depot.DepotRepository;
import mg.ecomada.collect.geo.adresse.AdresseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PointCollecteServiceImpl implements PointCollecteService {
    private final PointCollecteRepository repository;
    private final PointCollecteMapper mapper;
    private final AdresseRepository adresseRepository;
    private final DepotRepository depotRepository;

    @Override
    public List<PointCollecteDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public List<PointCollecteDto> findByActif(Boolean actif) {
        return repository.findByActif(actif).stream().map(mapper::toDto).toList();
    }

    @Override
    public PointCollecteDto findById(Long id) {
        return repository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("PointCollecte", id));
    }

    @Override
    public PointCollecteDto create(PointCollecteDto dto) {
        PointCollecte entity = mapper.toEntity(dto);
        entity.setAdresse(adresseRepository.findById(dto.getAdresseId())
                .orElseThrow(() -> new ResourceNotFoundException("Adresse", dto.getAdresseId())));
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public PointCollecteDto update(Long id, PointCollecteDto dto) {
        PointCollecte entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PointCollecte", id));
        entity.setNom(dto.getNom());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setActif(dto.getActif());
        if (dto.getAdresseId() != null) {
            entity.setAdresse(adresseRepository.findById(dto.getAdresseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Adresse", dto.getAdresseId())));
        }
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("PointCollecte", id);
        repository.deleteById(id);
    }

    @Override
    public Map<String, Object> getStats(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("PointCollecte", id);
        List<Depot> depots = depotRepository.findByPointCollecteId(id);
        Map<String, Double> parType = depots.stream()
                .collect(Collectors.groupingBy(d -> d.getTypeDechet().getNom(),
                        Collectors.summingDouble(Depot::getPoidsKg)));
        double totalKg = depots.stream().mapToDouble(Depot::getPoidsKg).sum();
        return Map.of("pointCollecteId", id, "totalDepots", depots.size(),
                "totalKg", totalKg, "volumesParType", parType);
    }
}
