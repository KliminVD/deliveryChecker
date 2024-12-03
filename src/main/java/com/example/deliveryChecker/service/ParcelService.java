package com.example.deliveryChecker.service;

import com.example.deliveryChecker.model.Parcel;
import com.example.deliveryChecker.repository.ParcelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParcelService {

    private final ParcelRepository parcelRepository;

    public ParcelService(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    // Получение посылки по трек-коду
    public Parcel getParcelByTrackingCode(String trackingCode) {
        Optional<Parcel> parcel = parcelRepository.findByTrackingCode(trackingCode);
        return parcel.orElse(null);
    }

    // Получение всех посылок
    public List<Parcel> findAll() {
        return parcelRepository.findAll();
    }

    // Получение всех посылок по email пользователя
    public List<Parcel> findAllByUserEmail(String email) {
        return parcelRepository.findAllByUserEmail(email);
    }

    // Добавление новой посылки
    public void createParcel(Parcel parcel) {
        parcelRepository.save(parcel);
    }

    // Обновление состояния и местоположения посылки
    public void updateParcel(Long id, String status, String location) {
        Optional<Parcel> optionalParcel = parcelRepository.findById(id);
        if (optionalParcel.isPresent()) {
            Parcel parcel = optionalParcel.get();
            parcel.setStatus(status);
            parcel.setLocation(location);
            parcelRepository.save(parcel);
        }
    }

    public Parcel getParcelById(Long id) {
        Optional<Parcel> parcel = parcelRepository.getParcelById(id);
        return parcel.orElse(null);
    }

    // Удаление посылки
    public void deleteParcel(Long id) {
        parcelRepository.deleteById(id);
    }
}
