package mg.ecomada.collect.geo.quartier;

import lombok.RequiredArgsConstructor;
import mg.ecomada.collect.common.exception.ResourceNotFoundException;
import mg.ecomada.collect.geo.commune.CommuneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class QuartierServiceImpl implements QuartierService {
    private final QuartierRepository repository;
    private final QuartierMapper mapper;
    private final CommuneRepository communeRepository;

    @Override
    public List<QuartierDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public List<QuartierDto> findByCommuneId(Long communeId) {
        return repository.findByCommuneId(communeId).stream().map(mapper::toDto).toList();
    }

    @Override
    public QuartierDto findById(Long id) {
        return repository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Quartier", id));
    }

    @Override
    public QuartierDto create(QuartierDto dto) {
        Quartier entity = mapper.toEntity(dto);
        entity.setCommune(communeRepository.findById(dto.getCommuneId())
                .orElseThrow(() -> new ResourceNotFoundException("Commune", dto.getCommuneId())));
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public QuartierDto update(Long id, QuartierDto dto) {
        Quartier entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quartier", id));
        entity.setNom(dto.getNom());
        if (dto.getCommuneId() != null) {
            entity.setCommune(communeRepository.findById(dto.getCommuneId())
                    .orElseThrow(() -> new ResourceNotFoundException("Commune", dto.getCommuneId())));
        }
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Quartier", id);
        repository.deleteById(id);
    }
}
