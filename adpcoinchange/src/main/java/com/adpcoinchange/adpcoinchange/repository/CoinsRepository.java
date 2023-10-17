package com.adpcoinchange.adpcoinchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CoinsRepository extends JpaRepository<CoinEntity, UUID> {

}
