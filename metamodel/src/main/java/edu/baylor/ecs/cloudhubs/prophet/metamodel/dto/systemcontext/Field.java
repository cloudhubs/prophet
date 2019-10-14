package edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext;

import lombok.*;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Field {
    private Set<Annotation> annotations;
    private String type;
    private String name;
    private Entity entityReference;

    public Set<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Set<Annotation> annotations) {
        this.annotations = annotations;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Entity getEntityReference() {
        return entityReference;
    }

    public void setEntityReference(Entity entityReference) {
        this.entityReference = entityReference;
    }
}
