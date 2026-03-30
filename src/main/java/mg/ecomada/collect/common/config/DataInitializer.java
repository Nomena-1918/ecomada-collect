package mg.ecomada.collect.common.config;

import lombok.RequiredArgsConstructor;
import mg.ecomada.collect.collecteur.Collecteur;
import mg.ecomada.collect.collecteur.CollecteurRepository;
import mg.ecomada.collect.dechet.status.Status;
import mg.ecomada.collect.dechet.status.StatusRepository;
import mg.ecomada.collect.dechet.typedechet.TypeDechet;
import mg.ecomada.collect.dechet.typedechet.TypeDechetRepository;
import mg.ecomada.collect.depot.Depot;
import mg.ecomada.collect.depot.DepotRepository;
import mg.ecomada.collect.geo.adresse.Adresse;
import mg.ecomada.collect.geo.adresse.AdresseRepository;
import mg.ecomada.collect.geo.commune.Commune;
import mg.ecomada.collect.geo.commune.CommuneRepository;
import mg.ecomada.collect.geo.pointcollecte.PointCollecte;
import mg.ecomada.collect.geo.pointcollecte.PointCollecteRepository;
import mg.ecomada.collect.geo.quartier.Quartier;
import mg.ecomada.collect.geo.quartier.QuartierRepository;
import mg.ecomada.collect.geo.ville.Ville;
import mg.ecomada.collect.geo.ville.VilleRepository;
import mg.ecomada.collect.recompense.Recompense;
import mg.ecomada.collect.recompense.RecompenseRepository;
import mg.ecomada.collect.recycleur.Recycleur;
import mg.ecomada.collect.recycleur.RecycleurRepository;
import mg.ecomada.collect.role.Role;
import mg.ecomada.collect.role.RoleRepository;
import mg.ecomada.collect.user.User;
import mg.ecomada.collect.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final VilleRepository villeRepository;
    private final CommuneRepository communeRepository;
    private final QuartierRepository quartierRepository;
    private final AdresseRepository adresseRepository;
    private final PointCollecteRepository pointCollecteRepository;
    private final TypeDechetRepository typeDechetRepository;
    private final StatusRepository statusRepository;
    private final CollecteurRepository collecteurRepository;
    private final RecycleurRepository recycleurRepository;
    private final RecompenseRepository recompenseRepository;
    private final DepotRepository depotRepository;

    @Override
    @Transactional
    public void run(String... args) {
        // Roles
        Role adminRole = getOrCreateRole("ADMIN");
        Role citoyenRole = getOrCreateRole("CITOYEN");
        Role collecteurRole = getOrCreateRole("COLLECTEUR");
        Role recycleurRole = getOrCreateRole("RECYCLEUR");

        // Statuts
        getOrCreateStatus("ENREGISTRE", 1);
        Status collectedStatus = getOrCreateStatus("COLLECTE", 2);
        Status transferred = getOrCreateStatus("TRANSFERE", 3);
        Status valorized = getOrCreateStatus("VALORISE", 4);

        // Types de dechets
        TypeDechet plastique = getOrCreateTypeDechet("Plastique");
        TypeDechet verre = getOrCreateTypeDechet("Verre");
        TypeDechet metal = getOrCreateTypeDechet("Metal");
        TypeDechet organique = getOrCreateTypeDechet("Organique");

        // Géo - Villes
        Ville tana = getOrCreateVille("Antananarivo");
        Ville toamasina = getOrCreateVille("Toamasina");
        getOrCreateVille("Mahajanga");
        getOrCreateVille("Toliara");

        // Communes
        Commune tanaRenivohitra = getOrCreateCommune("Antananarivo Renivohitra", tana);
        Commune ambohidratrimo = getOrCreateCommune("Ambohidratrimo", tana);
        Commune toamasinaI = getOrCreateCommune("Toamasina I", toamasina);

        // Quartiers
        Quartier analakely = getOrCreateQuartier("Analakely", tanaRenivohitra);
        Quartier isotry = getOrCreateQuartier("Isotry", tanaRenivohitra);
        getOrCreateQuartier("Ambodivona", ambohidratrimo);
        Quartier anjoma = getOrCreateQuartier("Anjoma", toamasinaI);

        // Adresses
        Adresse analakelyAddr = getOrCreateAdresse("Rue de l'Independance", analakely);
        Adresse isotryAddr = getOrCreateAdresse("Avenue Andrianampoinimerina", isotry);
        Adresse anjomaAddr = getOrCreateAdresse("Boulevard Ratsimilaho", anjoma);

        // Points de collecte
        PointCollecte p1 = getOrCreatePointCollecte("Point Analakely Centre", -18.9137, 47.5261, analakelyAddr);
        PointCollecte p2 = getOrCreatePointCollecte("Point Isotry Marche", -18.9180, 47.5190, isotryAddr);
        PointCollecte p3 = getOrCreatePointCollecte("Point Toamasina Port", -18.1492, 49.3958, anjomaAddr);

        // Users
        getOrCreateUser("Admin System", "admin@ecomada.mg", "password123", adminRole);
        User rakoto = getOrCreateUser("Rakoto Jean", "rakoto@ecomada.mg", "password123", citoyenRole);
        User rasoa = getOrCreateUser("Rasoa Marie", "rasoa@ecomada.mg", "password123", citoyenRole);
        getOrCreateUser("Collecteur Pro", "collecteur@ecomada.mg", "password123", collecteurRole);
        getOrCreateUser("Recycleur Expert", "recycleur@ecomada.mg", "password123", recycleurRole);

        // Collecteurs
        Collecteur express = getOrCreateCollecteur("Collecte Express", "034 00 000 01");
        Collecteur ecoRamasse = getOrCreateCollecteur("EcoRamasse", "034 00 000 02");

        // Recycleurs
        Recycleur madaRecycle = getOrCreateRecycleur("MadaRecycle", "Zone industrielle Antsirabe", Set.of(plastique, verre));
        getOrCreateRecycleur("GreenPlast", "Tanjombato, Antananarivo", Set.of(plastique, metal));

        // Récompenses
        Recompense bronze = getOrCreateRecompense("Eco-Citoyen Bronze", "Badge pour 100 points cumules", 100);
        getOrCreateRecompense("Eco-Citoyen Argent", "Badge pour 500 points cumules", 500);
        getOrCreateRecompense("Eco-Citoyen Or", "Badge pour 1000 points cumules", 1000);

        // Attribution récompense (Test)
        if (rakoto.getRoles().contains(citoyenRole) && !bronze.getUsers().contains(rakoto)) {
            bronze.getUsers().add(rakoto);
            recompenseRepository.save(bronze);
        }

        // Dépôts de Démo
        if (depotRepository.count() == 0) {
            // D1: 8kg Plastique, Rakoto Jean, Point Analakely Centre, VALORISE, Collecte Express, MadaRecycle
            depotRepository.save(Depot.builder()
                    .poidsKg(8.0).dateDepot(LocalDateTime.now().minusDays(10)).user(rakoto)
                    .pointCollecte(p1).typeDechet(plastique).status(valorized)
                    .collecteur(express).recycleur(madaRecycle).build());

            // D2: 3.5kg Verre, Rakoto Jean, Point Analakely Centre, TRANSFERE, Collecte Express
            depotRepository.save(Depot.builder()
                    .poidsKg(3.5).dateDepot(LocalDateTime.now().minusDays(5)).user(rakoto)
                    .pointCollecte(p1).typeDechet(verre).status(transferred)
                    .collecteur(express).build());

            // D3: 5.0kg Organique, Rasoa Marie, Point Isotry Marche, COLLECTE, EcoRamasse
            depotRepository.save(Depot.builder()
                    .poidsKg(5.0).dateDepot(LocalDateTime.now().minusDays(3)).user(rasoa)
                    .pointCollecte(p2).typeDechet(organique).status(collectedStatus)
                    .collecteur(ecoRamasse).build());

            // D4: 2.0kg Metal, Rasoa Marie, Point Toamasina Port, ENREGISTRE
            depotRepository.save(Depot.builder()
                    .poidsKg(2.0).dateDepot(LocalDateTime.now().minusDays(1)).user(rasoa)
                    .pointCollecte(p3).typeDechet(metal).status(getOrCreateStatus("ENREGISTRE", 1)).build());
        }
    }

    private Role getOrCreateRole(String nom) {
        return roleRepository.findByNom(nom)
                .orElseGet(() -> roleRepository.save(Role.builder().nom(nom).build()));
    }

    private Status getOrCreateStatus(String nom, int rang) {
        return statusRepository.findAll().stream()
                .filter(s -> s.getNom().equals(nom))
                .findFirst()
                .orElseGet(() -> statusRepository.save(Status.builder().nom(nom).rang(rang).build()));
    }

    private TypeDechet getOrCreateTypeDechet(String nom) {
        return typeDechetRepository.findAll().stream()
                .filter(t -> t.getNom().equals(nom))
                .findFirst()
                .orElseGet(() -> typeDechetRepository.save(TypeDechet.builder().nom(nom).build()));
    }

    private Ville getOrCreateVille(String nom) {
        return villeRepository.findAll().stream()
                .filter(v -> v.getNom().equals(nom))
                .findFirst()
                .orElseGet(() -> villeRepository.save(Ville.builder().nom(nom).build()));
    }

    private Commune getOrCreateCommune(String nom, Ville ville) {
        return communeRepository.findAll().stream()
                .filter(c -> c.getNom().equals(nom))
                .findFirst()
                .orElseGet(() -> communeRepository.save(Commune.builder().nom(nom).ville(ville).build()));
    }

    private Quartier getOrCreateQuartier(String nom, Commune commune) {
        return quartierRepository.findAll().stream()
                .filter(q -> q.getNom().equals(nom))
                .findFirst()
                .orElseGet(() -> quartierRepository.save(Quartier.builder().nom(nom).commune(commune).build()));
    }

    private Adresse getOrCreateAdresse(String rue, Quartier quartier) {
        return adresseRepository.findAll().stream()
                .filter(a -> a.getRue().equals(rue))
                .findFirst()
                .orElseGet(() -> adresseRepository.save(Adresse.builder().rue(rue).quartier(quartier).build()));
    }

    private PointCollecte getOrCreatePointCollecte(String nom, double lat, double lon, Adresse addr) {
        return pointCollecteRepository.findAll().stream()
                .filter(p -> p.getNom().equals(nom))
                .findFirst()
                .orElseGet(() -> pointCollecteRepository.save(PointCollecte.builder()
                        .nom(nom).latitude(lat).longitude(lon).actif(true).adresse(addr).build()));
    }

    private User getOrCreateUser(String nom, String email, String pass, Role role) {
        return userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(User.builder()
                        .nom(nom).email(email).motDePasse(passwordEncoder.encode(pass)).roles(Set.of(role)).build()));
    }

    private Collecteur getOrCreateCollecteur(String nom, String tel) {
        return collecteurRepository.findAll().stream()
                .filter(c -> c.getNom().equals(nom))
                .findFirst()
                .orElseGet(() -> collecteurRepository.save(Collecteur.builder().nom(nom).telephone(tel).actif(true).build()));
    }

    private Recycleur getOrCreateRecycleur(String nom, String addr, Set<TypeDechet> capabilities) {
        return recycleurRepository.findAll().stream()
                .filter(r -> r.getNom().equals(nom))
                .findFirst()
                .orElseGet(() -> recycleurRepository.save(Recycleur.builder()
                        .nom(nom).adresse(addr).actif(true).typeDechets(capabilities).build()));
    }

    private Recompense getOrCreateRecompense(String nom, String desc, int pts) {
        return recompenseRepository.findAll().stream()
                .filter(r -> r.getNom().equals(nom))
                .findFirst()
                .orElseGet(() -> recompenseRepository.save(Recompense.builder().nom(nom).description(desc).pointsRequis(pts).build()));
    }
}
