package mg.ecomada.collect.role;

import java.util.List;

public interface RoleService {
    List<RoleDto> findAll();

    RoleDto findById(Long id);

    RoleDto create(RoleDto dto);

    RoleDto update(Long id, RoleDto dto);

    void delete(Long id);
}
