package mg.ecomada.collect.dechet.status;

import java.util.List;

public interface StatusService {
    List<StatusDto> findAll();

    StatusDto findById(Long id);

    StatusDto create(StatusDto dto);

    StatusDto update(Long id, StatusDto dto);

    void delete(Long id);
}
