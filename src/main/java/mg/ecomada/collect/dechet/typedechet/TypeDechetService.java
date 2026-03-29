package mg.ecomada.collect.dechet.typedechet;

import java.util.List;

public interface TypeDechetService {
    List<TypeDechetDto> findAll();

    TypeDechetDto findById(Long id);

    TypeDechetDto create(TypeDechetDto dto);

    TypeDechetDto update(Long id, TypeDechetDto dto);

    void delete(Long id);
}
