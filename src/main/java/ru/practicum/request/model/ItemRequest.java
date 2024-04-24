package ru.practicum.request.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.practicum.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Table(name = "requests", schema = "public")
@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    private String description;

    @ManyToOne
    @JoinColumn(name = "requestor_id", nullable = false)
    @ToString.Exclude
    @NotNull
    private User requestor;
    private LocalDateTime created = LocalDateTime.now();
}
