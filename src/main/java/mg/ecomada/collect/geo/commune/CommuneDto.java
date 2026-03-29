package mg.ecomada.collect.geo.commune;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommuneDto {
    private Long id;
    private String nom;
    private Long villeId;
    private String villeNom;
}
