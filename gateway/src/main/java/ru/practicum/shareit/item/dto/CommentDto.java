package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    @Size(min = 1, max = 512)
    @NotBlank
    private String text;
}