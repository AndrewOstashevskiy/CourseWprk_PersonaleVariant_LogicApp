package com.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserBalance {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    private User userId;

    private double balance;
}
