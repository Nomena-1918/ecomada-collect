package mg.ecomada.collect.user;

import lombok.RequiredArgsConstructor;
import mg.ecomada.collect.common.exception.ResourceNotFoundException;
import mg.ecomada.collect.depot.Depot;
import mg.ecomada.collect.depot.DepotRepository;
import mg.ecomada.collect.recompense.Recompense;
import mg.ecomada.collect.recompense.RecompenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final DepotRepository depotRepository;
    private final RecompenseRepository recompenseRepository;

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
    public UserDto update(Long id, UserDto dto) {
        User entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
        entity.setNom(dto.getNom());
        entity.setEmail(dto.getEmail());
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
}
