package mg.ecomada.collect.user;

import mg.ecomada.collect.role.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "motDePasse", ignore = true)
    @Mapping(target = "roles", expression = "java(mapRoles(entity.getRoles()))")
    UserDto toDto(User entity);

    default Set<String> mapRoles(Set<Role> roles) {
        if (roles == null) return null;
        return roles.stream().map(Role::getNom).collect(Collectors.toSet());
    }
}
