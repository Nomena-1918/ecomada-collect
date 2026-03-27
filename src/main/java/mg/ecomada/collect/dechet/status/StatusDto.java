package mg.ecomada.collect.dechet.status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusDto {
    private Long id;
    private String nom;
    private Integer rang;
}
