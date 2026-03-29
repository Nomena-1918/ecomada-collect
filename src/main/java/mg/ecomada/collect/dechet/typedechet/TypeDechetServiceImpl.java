package mg.ecomada.collect.dechet.typedechet;

import lombok.RequiredArgsConstructor;
import mg.ecomada.collect.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TypeDechetServiceImpl implements TypeDechetService {
    private final TypeDechetRepository repository;
    private final TypeDechetMapper mapper;

    @Override
    public List<TypeDechetDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public TypeDechetDto findById(Long id) {
        return repository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("TypeDechet", id));
    }

    @Override
    public TypeDechetDto create(TypeDechetDto dto) {
        TypeDechet entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public TypeDechetDto update(Long id, TypeDechetDto dto) {
        TypeDechet entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TypeDechet", id));
        entity.setNom(dto.getNom());
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("TypeDechet", id);
        repository.deleteById(id);
    }
}
