package dev.drugowick.lists.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ListItemInput {

    @NotBlank(message = "Description is mandatory")
    private String description = "";
}
