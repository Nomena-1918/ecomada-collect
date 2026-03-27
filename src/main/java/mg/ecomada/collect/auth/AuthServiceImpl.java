package mg.ecomada.collect.auth;

import lombok.RequiredArgsConstructor;
import mg.ecomada.collect.common.exception.BusinessException;
import mg.ecomada.collect.role.Role;
import mg.ecomada.collect.role.RoleRepository;
import mg.ecomada.collect.user.User;
import mg.ecomada.collect.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public AuthResponse register(AuthRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Cet email est deja utilise");
        }
        Role citoyenRole = roleRepository.findByNom("CITOYEN")
                .orElseThrow(() -> new BusinessException("Role CITOYEN introuvable"));
        User user = User.builder()
                .nom(request.getNom())
                .email(request.getEmail())
                .motDePasse(passwordEncoder.encode(request.getMotDePasse()))
                .roles(Set.of(citoyenRole))
                .build();
        userRepository.save(user);
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getMotDePasse()));
        String token = jwtTokenProvider.generateToken(auth);
        return AuthResponse.builder()
                .token(token).email(user.getEmail()).nom(user.getNom()).build();
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getMotDePasse()));
        String token = jwtTokenProvider.generateToken(auth);
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException("Utilisateur introuvable"));
        return AuthResponse.builder()
                .token(token).email(user.getEmail()).nom(user.getNom()).build();
    }
}
