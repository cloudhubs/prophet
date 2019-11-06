package edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Field {

    @NonNull
    private String name;

    @NonNull
    private String type;

    private Set<Annotation> annotations = new HashSet<>();

    private Entity entityReference = null;

    public Field(@NonNull String type, @NonNull String name) {
        this.type = type;
        this.name = name;
    }
}
