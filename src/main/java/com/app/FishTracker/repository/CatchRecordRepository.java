package com.app.FishTracker.repository;

import com.app.FishTracker.model.CatchRecord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CatchRecordRepository extends JpaRepository<CatchRecord, Long> {
    List<CatchRecord> findByTripId(Long tripId);
    List<CatchRecord> findByUserId(Long userId);
    List<CatchRecord> findTop3ByUserIdOrderByIdDesc(Long userId);

    @Query("""
        SELECT c FROM CatchRecord c
        WHERE c.id = :id
    """)
    CatchRecord findByCatchId(Long id);

    List<CatchRecord> findByUserIdAndFishId(Long userId, Long fishId);

    @Modifying
    @Transactional
    @Query("UPDATE CatchRecord c SET c.isPersonalBest = false WHERE c.fish.id = :fishId AND c.isPersonalBest = true")
    void setPersonalBestFalse(@Param("fishId") Long fishId);

    @Query("SELECT c FROM CatchRecord c where c.user.id = :userId AND c.isPersonalBest = true ORDER BY c.id DESC")
    List<CatchRecord> findRecentPBs(Long userId, Pageable pageable);

    int countByUserId(Long userId);

    @Query("SELECT COUNT(c) FROM CatchRecord c where c.user.id = :userId AND c.isPersonalBest = true")
    int countPBs(Long userId);
}
