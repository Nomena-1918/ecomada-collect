package mg.ecomada.collect.geo.commune;

import lombok.RequiredArgsConstructor;
import mg.ecomada.collect.common.exception.ResourceNotFoundException;
import mg.ecomada.collect.geo.ville.VilleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommuneServiceImpl implements CommuneService {
    private final CommuneRepository repository;
    private final CommuneMapper mapper;
    private final VilleRepository villeRepository;

    @Override
    public List<CommuneDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public List<CommuneDto> findByVilleId(Long villeId) {
        return repository.findByVilleId(villeId).stream().map(mapper::toDto).toList();
    }

    @Override
    public CommuneDto findById(Long id) {
        return repository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Commune", id));
    }

    @Override
    public CommuneDto create(CommuneDto dto) {
        Commune entity = mapper.toEntity(dto);
        entity.setVille(villeRepository.findById(dto.getVilleId())
                .orElseThrow(() -> new ResourceNotFoundException("Ville", dto.getVilleId())));
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public CommuneDto update(Long id, CommuneDto dto) {
        Commune entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commune", id));
        entity.setNom(dto.getNom());
        if (dto.getVilleId() != null) {
            entity.setVille(villeRepository.findById(dto.getVilleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ville", dto.getVilleId())));
        }
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Commune", id);
        repository.deleteById(id);
    }
}
