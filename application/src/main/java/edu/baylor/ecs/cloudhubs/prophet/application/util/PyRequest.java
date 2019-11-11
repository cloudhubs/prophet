package edu.baylor.ecs.cloudhubs.prophet.application.util;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PyRequest {
    @NotNull
    @NotBlank
    @NotEmpty
    String fileName;

    @NotNull
    @NotBlank
    @NotEmpty
    boolean gitIgnore = true;
}
