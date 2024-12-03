package com.example.deliveryChecker.service;

import com.example.deliveryChecker.model.Parcel;
import com.example.deliveryChecker.repository.ParcelRepository;
import org.springframework.stereotype.Service;

@Service
public class ParcelService {

    private final ParcelRepository parcelRepository;

    public ParcelService(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    public Parcel getParcelByTrackingCode(String trackingCode) {
        return parcelRepository.findByTrackingCode(trackingCode);
    }
}
