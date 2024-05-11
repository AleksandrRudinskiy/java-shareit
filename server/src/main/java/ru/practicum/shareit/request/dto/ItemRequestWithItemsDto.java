package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.ItemInfo;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestWithItemsDto {
    private long id;
    @NotBlank
    private String description;
    private long requestorId;
    private String created;
    private List<ItemInfo> items;
}
