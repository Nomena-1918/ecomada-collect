package mg.ecomada.collect.geo.pointcollecte;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PointCollecteRepository extends JpaRepository<PointCollecte, Long> {
    java.util.List<PointCollecte> findByAdresseId(Long adresseId);

    java.util.List<PointCollecte> findByActif(Boolean actif);
}
