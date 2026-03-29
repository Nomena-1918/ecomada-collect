package mg.ecomada.collect.dechet.typedechet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeDechetDto {
    private Long id;
    private String nom;
}
