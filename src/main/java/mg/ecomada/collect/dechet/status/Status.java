package mg.ecomada.collect.dechet.status;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "statuts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nom;

    @Column(nullable = false)
    private Integer rang;
}
