package mg.ecomada.collect.geo.quartier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuartierDto {
    private Long id;
    private String nom;
    private Long communeId;
    private String communeNom;
}
