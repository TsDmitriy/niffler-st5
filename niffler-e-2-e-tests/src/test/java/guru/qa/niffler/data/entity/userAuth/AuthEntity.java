package guru.qa.niffler.data.entity.userAuth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class AuthEntity implements Serializable {
    private UUID id;
    private Authority authority;
    @JsonProperty("user_id")
    private UUID userId;
}
