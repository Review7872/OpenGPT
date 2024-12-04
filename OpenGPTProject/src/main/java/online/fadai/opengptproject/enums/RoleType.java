package online.fadai.opengptproject.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleType {
    USER("user", 1),
    Assistant("assistant", 2);

    private final String roleName;
    private final Integer roleType;
}
