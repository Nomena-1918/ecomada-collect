package mg.ecomada.collect.geo.adresse;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdresseRepository extends JpaRepository<Adresse, Long> {
    java.util.List<Adresse> findByQuartierId(Long quartierId);
}
