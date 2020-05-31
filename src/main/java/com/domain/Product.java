package com.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    private String description;
    private String domain;
    private Double price;
    private LocalDateTime date;
    private boolean sold = false;
    private boolean placed = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User owner;

    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    private UUID oldOwner;

    public String getAuthorName() {
        return owner != null ? owner.getUsername() : "<none>";
    }
}
