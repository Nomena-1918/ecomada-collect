package mg.ecomada.collect.geo.adresse;

import lombok.RequiredArgsConstructor;
import mg.ecomada.collect.common.exception.ResourceNotFoundException;
import mg.ecomada.collect.geo.quartier.QuartierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdresseServiceImpl implements AdresseService {
    private final AdresseRepository repository;
    private final AdresseMapper mapper;
    private final QuartierRepository quartierRepository;

    @Override
    public List<AdresseDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public AdresseDto findById(Long id) {
        return repository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Adresse", id));
    }

    @Override
    public AdresseDto create(AdresseDto dto) {
        Adresse entity = mapper.toEntity(dto);
        entity.setQuartier(quartierRepository.findById(dto.getQuartierId())
                .orElseThrow(() -> new ResourceNotFoundException("Quartier", dto.getQuartierId())));
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public AdresseDto update(Long id, AdresseDto dto) {
        Adresse entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adresse", id));
        entity.setRue(dto.getRue());
        if (dto.getQuartierId() != null) {
            entity.setQuartier(quartierRepository.findById(dto.getQuartierId())
                    .orElseThrow(() -> new ResourceNotFoundException("Quartier", dto.getQuartierId())));
        }
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Adresse", id);
        repository.deleteById(id);
    }
}
