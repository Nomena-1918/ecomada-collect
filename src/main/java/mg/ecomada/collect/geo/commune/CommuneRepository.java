package mg.ecomada.collect.geo.commune;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommuneRepository extends JpaRepository<Commune, Long> {
    java.util.List<Commune> findByVilleId(Long villeId);
}
