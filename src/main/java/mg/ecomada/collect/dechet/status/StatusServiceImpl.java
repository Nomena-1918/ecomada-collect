package mg.ecomada.collect.dechet.status;

import lombok.RequiredArgsConstructor;
import mg.ecomada.collect.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StatusServiceImpl implements StatusService {
    private final StatusRepository repository;
    private final StatusMapper mapper;

    @Override
    public List<StatusDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public StatusDto findById(Long id) {
        return repository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Status", id));
    }

    @Override
    public StatusDto create(StatusDto dto) {
        Status entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public StatusDto update(Long id, StatusDto dto) {
        Status entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Status", id));
        entity.setNom(dto.getNom());
        entity.setRang(dto.getRang());
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Status", id);
        repository.deleteById(id);
    }
}
