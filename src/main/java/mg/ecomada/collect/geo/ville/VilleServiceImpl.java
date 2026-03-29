package mg.ecomada.collect.geo.ville;

import lombok.RequiredArgsConstructor;
import mg.ecomada.collect.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VilleServiceImpl implements VilleService {
    private final VilleRepository repository;
    private final VilleMapper mapper;

    @Override
    public List<VilleDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public VilleDto findById(Long id) {
        return repository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Ville", id));
    }

    @Override
    public VilleDto create(VilleDto dto) {
        Ville entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public VilleDto update(Long id, VilleDto dto) {
        Ville entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ville", id));
        entity.setNom(dto.getNom());
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Ville", id);
        repository.deleteById(id);
    }
}
