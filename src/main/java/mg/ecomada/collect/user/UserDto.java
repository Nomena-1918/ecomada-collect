package mg.ecomada.collect.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String nom;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String motDePasse;
    private Set<String> roles;
}
