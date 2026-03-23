package com.qubid.backend.repository;

import com.qubid.backend.entities.BasePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasePriceRepository extends JpaRepository<BasePrice, Long> {
}
