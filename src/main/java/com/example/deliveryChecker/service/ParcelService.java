package com.example.deliveryChecker.service;

import com.example.deliveryChecker.model.Parcel;
import com.example.deliveryChecker.repository.ParcelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParcelService {

    private final ParcelRepository parcelRepository;
    private final EmailService emailService;

    public ParcelService(ParcelRepository parcelRepository, EmailService emailService) {
        this.parcelRepository = parcelRepository;
        this.emailService = emailService;
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
        if (parcelRepository.findByTrackingCode(parcel.getTrackingCode()).isEmpty()){
            parcelRepository.save(parcel);
            String email = parcel.getUser().getEmail();
            String subject = "К вам едет посылочка";
            String text = String.format(
                    "Здравствуйте, %s!<br><br>К Вам была отправлена посылка с номером <strong>%s</strong>.<br><br>Статус посылки - <strong>%s</strong><br><br>Спасибо, что пользуетесь нашими услугами! Статус посылки можно отследить на нашем сайте.",
                    parcel.getUser().getUsername(), parcel.getTrackingCode(), parcel.getStatus()
            );
            emailService.sendEmail(email, subject, text);
        }

    }

    // Обновление состояния и местоположения посылки
    public void updateParcel(Long id, String status, String location) {
        Optional<Parcel> optionalParcel = parcelRepository.findById(id);
        if (optionalParcel.isPresent()) {
            Parcel parcel = optionalParcel.get();
            parcel.setStatus(status);
            parcel.setLocation(location);
            parcelRepository.save(parcel);
            // Отправка уведомления пользователю\
            String email = parcel.getUser().getEmail();
            String subject = "Статус вашей посылки изменился";
            String text = String.format(
                    "Здравствуйте, %s!<br><br>Статус вашей посылки с номером <strong>%s</strong> изменился на <strong>%s</strong>. На данный момент посылка находится в <strong>%s</strong>.<br><br>Спасибо, что пользуетесь нашими услугами!",
                    parcel.getUser().getUsername(), parcel.getTrackingCode(), status, location
            );
            emailService.sendEmail(email, subject, text);

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
