package edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Module {

    @NonNull
    private String name;

    private List<Entity> entities;

    public Module(@NonNull String name) {
        this.name = name;
    }
}
