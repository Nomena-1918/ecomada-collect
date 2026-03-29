package mg.ecomada.collect.role;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toDto(Role entity);

    Role toEntity(RoleDto dto);
}
