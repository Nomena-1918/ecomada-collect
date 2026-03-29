package mg.ecomada.collect.geo.pointcollecte;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointCollecteDto {
    private Long id;
    private String nom;
    private Double latitude;
    private Double longitude;
    private Boolean actif;
    private Long adresseId;
    private String adresseRue;
}
