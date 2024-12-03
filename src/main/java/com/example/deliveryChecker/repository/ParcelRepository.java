package com.example.deliveryChecker.repository;

import com.example.deliveryChecker.model.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelRepository extends JpaRepository<Parcel, Long> {
    Parcel findByTrackingCode(String trackingCode);
}