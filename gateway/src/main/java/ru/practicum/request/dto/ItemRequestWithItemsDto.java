package ru.practicum.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.item.ItemInfo;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestWithItemsDto {
    private long id;
    @NotBlank
    private String description;
    private long requestorId;
    private LocalDateTime created;
    private List<ItemInfo> items;
}
