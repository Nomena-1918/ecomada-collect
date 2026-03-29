package mg.ecomada.collect.geo.adresse;

import jakarta.persistence.*;
import lombok.*;
import mg.ecomada.collect.geo.quartier.Quartier;

@Entity
@Table(name = "adresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Adresse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String rue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quartier_id", nullable = false)
    private Quartier quartier;
}
