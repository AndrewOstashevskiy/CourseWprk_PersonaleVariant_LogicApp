package com.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "MESSAGE", schema = "PUBLIC")
public class Message {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    private String text;
    private String tag;
}
