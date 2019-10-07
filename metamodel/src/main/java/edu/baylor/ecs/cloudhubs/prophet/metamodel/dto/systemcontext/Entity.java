package edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Entity {
    private List<Field> fields;
    private String entityName;

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}
