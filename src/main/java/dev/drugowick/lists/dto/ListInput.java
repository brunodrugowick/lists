package dev.drugowick.lists.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ListInput {

    @NotBlank(message = "Title is mandatory")
    @Length(max = 144, message = "Title should have less than 144 characters")
    private String title;

    private String description = "";
}
