package edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.systemcontext;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Annotation {
    private String name;
    private String stringValue;
    private Integer intValue;
}
