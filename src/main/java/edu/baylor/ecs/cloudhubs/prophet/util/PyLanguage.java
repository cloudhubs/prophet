package edu.baylor.ecs.cloudhubs.prophet.util;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PyLanguage {

    @NotBlank
    private String language;
}
