package mg.ecomada.collect.geo.adresse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdresseDto {
    private Long id;
    private String rue;
    private Long quartierId;
    private String quartierNom;
}
