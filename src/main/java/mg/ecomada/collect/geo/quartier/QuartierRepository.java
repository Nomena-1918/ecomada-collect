package mg.ecomada.collect.geo.quartier;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuartierRepository extends JpaRepository<Quartier, Long> {
    java.util.List<Quartier> findByCommuneId(Long communeId);
}
