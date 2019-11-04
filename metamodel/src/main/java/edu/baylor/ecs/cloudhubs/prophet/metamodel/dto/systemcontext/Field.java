package edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Field {
    private Set<Annotation> annotations = new HashSet<>();

    @NonNull
    private String type;

    @NonNull
    private String name;

    private Entity entityReference = null;

    public Field(@NonNull String type, @NonNull String name) {
        this.type = type;
        this.name = name;
    }
}
