package com.app.FishTracker.repository;

import com.app.FishTracker.model.Fish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FishRepository extends JpaRepository<Fish, Long> {
    Fish findByName(String name);
}
