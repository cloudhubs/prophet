package edu.baylor.ecs.cloudhubs.prophet.metamodel.dto.parser;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class JPSystem {
    private String name;
    private List<JPModule> modules;
    private JPSecurity jpSecurity;
    //ToDo
    //types classes/method/variables used in the system
    //types primitives
    //system global variables
    //method next method
    //method has_output variable
    //method has_inputs primitive/class/library

    public JPSystem(String name){
        this.name = name;
    }
}
