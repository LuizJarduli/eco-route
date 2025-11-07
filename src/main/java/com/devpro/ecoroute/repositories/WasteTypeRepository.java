package com.devpro.ecoroute.repositories;

import com.devpro.ecoroute.models.WasteType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WasteTypeRepository extends JpaRepository<WasteType, Long> {
    /**
     * Finds all waste types that start with the given prefix, ignoring case.
     * Example: "rec" would find "Recyclable" and "recyclable".
     * SQL: WHERE LOWER(name) LIKE 'rec%'
     */
    List<WasteType> findByNameStartingWithIgnoreCase(String prefix);
}
