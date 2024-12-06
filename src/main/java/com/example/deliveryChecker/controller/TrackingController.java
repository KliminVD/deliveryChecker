package com.example.deliveryChecker.controller;

import com.example.deliveryChecker.model.Parcel;
import com.example.deliveryChecker.service.ParcelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TrackingController {

    private final ParcelService parcelService;

    public TrackingController(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    @GetMapping("/trackParcel")
    public String trackParcel(@RequestParam(value = "trackingCode", required = false) String trackingCode, Model model) {
        if (trackingCode != null && !trackingCode.isEmpty()) {
            Parcel parcel = parcelService.getParcelByTrackingCode(trackingCode);

            if (parcel != null) {
                model.addAttribute("parcel", parcel);
            } else {
                model.addAttribute("error", "Parcel not found!");
            }
        }
        return "trackParcel";
    }
}
