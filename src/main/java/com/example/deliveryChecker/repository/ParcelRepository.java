package com.example.deliveryChecker.repository;

import com.example.deliveryChecker.model.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Long> {
    Optional<Parcel> findByTrackingCode(String trackingCode);
    Optional<Parcel> getParcelById(Long id);
    List<Parcel> findAllByUserEmail(String email);
}