package com.kai.pthings.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Data
@Entity
@Table(name = "image")
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    private String added_at;

    private String added_by;


}
