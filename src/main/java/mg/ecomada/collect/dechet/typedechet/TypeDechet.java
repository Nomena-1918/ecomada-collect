package mg.ecomada.collect.dechet.typedechet;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "types_dechet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeDechet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nom;
}
