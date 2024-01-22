package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.Marker;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;

    @NotBlank(groups = Marker.OnCreate.class)
    @Size(min = 1, max = 64, groups = {Marker.OnCreate.class, Marker.OnUpdate.class})
    private String name;

    @NotBlank(groups = Marker.OnCreate.class)
    @Email(groups = {Marker.OnCreate.class, Marker.OnUpdate.class})
    @Size(min = 1, max = 64, groups = {Marker.OnCreate.class, Marker.OnUpdate.class})
    private String email;
}