package mg.ecomada.collect.dechet.status;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
    java.util.Optional<Status> findByNom(String nom);

    java.util.Optional<Status> findByRang(Integer rang);
}
