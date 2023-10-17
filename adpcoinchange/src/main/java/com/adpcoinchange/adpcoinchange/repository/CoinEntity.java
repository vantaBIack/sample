package com.adpcoinchange.adpcoinchange.repository;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity(name = "coin")
@Table
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoinEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(unique = true)
    private BigDecimal value;
    @Column
    private Integer availableAmount;

}


