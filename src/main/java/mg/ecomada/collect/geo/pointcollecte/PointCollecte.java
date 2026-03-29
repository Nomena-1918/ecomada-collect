package mg.ecomada.collect.geo.pointcollecte;

import jakarta.persistence.*;
import lombok.*;
import mg.ecomada.collect.geo.adresse.Adresse;

@Entity
@Table(name = "points_collecte")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointCollecte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;
    private Double latitude;
    private Double longitude;
    @Builder.Default
    private Boolean actif = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adresse_id", nullable = false)
    private Adresse adresse;
}
