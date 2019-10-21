package edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.parser;

import lombok.*;

import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class JPSecurity {
    /**
     * Key: Security name: User, Admin, SuperAdmin
     * Value: Method names
     */
    private HashMap<String, List<String>> securityMethods;
}
