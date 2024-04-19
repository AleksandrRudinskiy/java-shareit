package ru.practicum.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.item.ItemInfo;
import ru.practicum.item.model.Item;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestWithItemsDto {
    private long id;
    @NotNull
    @NotBlank
    private String description;
    private long requestorId;
    private LocalDateTime created = LocalDateTime.now();
    private List<ItemInfo> items;
}
