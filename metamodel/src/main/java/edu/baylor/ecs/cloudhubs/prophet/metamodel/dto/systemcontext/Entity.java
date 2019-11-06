package edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext;

import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Entity {

    @NonNull
    private String entityName;

    private List<Field> fields = new LinkedList<>();

    public Entity(@NonNull String entityName) {
        this.entityName = entityName;
    }
}
