package mg.ecomada.collect.dechet.typedechet;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TypeDechetMapper {
    TypeDechetDto toDto(TypeDechet entity);

    TypeDechet toEntity(TypeDechetDto dto);
}
