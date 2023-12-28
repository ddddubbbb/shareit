package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.Marker;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;

    @NotBlank(groups = {Marker.OnCreate.class})
    private String name;

    @Email(groups = {Marker.OnCreate.class})
    @NotBlank(groups = {Marker.OnCreate.class})
    private String email;
}