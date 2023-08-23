package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.utils.validation.Create;
import ru.practicum.shareit.utils.validation.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto {
    private Long id;

    @NotBlank(message = "Name cannot be empty", groups = {Create.class, Update.class})
    private String name;

    @NotBlank(message = "Description cannot be empty", groups = {Create.class, Update.class})
    private String description;

    @NotNull(message = "Booking status cannot be empty", groups = {Create.class})
    private Boolean available;
}
