package mg.ecomada.collect.geo.ville;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VilleDto {
    private Long id;
    private String nom;
}
