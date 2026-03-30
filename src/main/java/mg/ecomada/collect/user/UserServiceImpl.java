package mg.ecomada.collect.user;

import lombok.RequiredArgsConstructor;
import mg.ecomada.collect.common.exception.BusinessException;
import mg.ecomada.collect.common.exception.ResourceNotFoundException;
import mg.ecomada.collect.depot.Depot;
import mg.ecomada.collect.depot.DepotRepository;
import mg.ecomada.collect.recompense.Recompense;
import mg.ecomada.collect.recompense.RecompenseRepository;
import mg.ecomada.collect.role.Role;
import mg.ecomada.collect.role.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final RoleRepository roleRepository;
    private final DepotRepository depotRepository;
    private final RecompenseRepository recompenseRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public UserDto findById(Long id) {
        return repository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
    }

    @Override
    public UserDto create(UserDto dto) {
        User entity = User.builder()
                .nom(dto.getNom())
                .email(dto.getEmail())
                .motDePasse(passwordEncoder.encode(dto.getMotDePasse()))
                .roles(resolveRoles(dto.getRoles()))
                .build();
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public UserDto update(Long id, UserDto dto) {
        User entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
        entity.setNom(dto.getNom());
        entity.setEmail(dto.getEmail());
        if (dto.getMotDePasse() != null && !dto.getMotDePasse().isEmpty()) {
            entity.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));
        }
        if (dto.getRoles() != null) {
            entity.setRoles(resolveRoles(dto.getRoles()));
        }
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("User", id);
        repository.deleteById(id);
    }

    @Override
    public Map<String, Object> getImpact(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("User", id);
        List<Depot> depots = depotRepository.findByUserId(id);
        double totalKg = depots.stream().mapToDouble(Depot::getPoidsKg).sum();
        long points = (long) (totalKg * 10);
        List<Recompense> recompenses = recompenseRepository.findAll().stream()
                .filter(r -> r.getUsers().stream().anyMatch(u -> u.getId().equals(id)))
                .toList();
        return Map.of("userId", id, "totalDepots", depots.size(),
                "totalKg", totalKg, "pointsCumules", points,
                "recompenses", recompenses.stream().map(Recompense::getNom).toList());
    }

    private Set<Role> resolveRoles(Set<String> roleNames) {
        if (roleNames == null || roleNames.isEmpty()) {
            return new HashSet<>();
        }
        return roleNames.stream()
                .map(roleName -> roleRepository.findByNom(roleName)
                        .orElseThrow(() -> new BusinessException("Role " + roleName + " introuvable")))
                .collect(java.util.stream.Collectors.toCollection(HashSet::new));
    }
}
