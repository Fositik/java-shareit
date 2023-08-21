package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotBlank;

/**
 * TODO Sprint add-controllers.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    private Long id;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Description cannot be empty")
    private String description;
    @NotBlank(message = "Booking status cannot be empty")
    private Boolean available;
    private User owner;
    private ItemRequest request;
}
