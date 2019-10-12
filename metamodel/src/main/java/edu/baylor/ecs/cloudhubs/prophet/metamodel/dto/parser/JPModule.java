package edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.parser;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class JPModule {
    private String name;
    private List<JPClass> classes;
}
