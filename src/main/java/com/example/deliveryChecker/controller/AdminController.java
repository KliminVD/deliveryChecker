package com.example.deliveryChecker.controller;

import com.example.deliveryChecker.model.Parcel;
import com.example.deliveryChecker.model.User;
import com.example.deliveryChecker.service.ParcelService;
import com.example.deliveryChecker.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);
    private final ParcelService parcelService;
    private final UserService userService;

    public AdminController(ParcelService parcelService, UserService userService) {
        this.parcelService = parcelService;
        this.userService = userService;
    }

    // Страница с управлением посылками
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/parcels")
    public String adminParcelsPage(Model model, @AuthenticationPrincipal User user) {
        // Получаем список всех посылок
        model.addAttribute("parcels", parcelService.findAll());
        return "admin/parcels";
    }

    // Страница добавления новой посылки
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/parcels/add")
    public String addParcelPage() {
        return "admin/addParcel"; // Шаблон для добавления новой посылки
    }

    // Обработка формы добавления новой посылки
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/parcels/add")
    public String addParcel(@RequestParam String trackingCode,
                            @RequestParam String status,
                            @RequestParam String location,
                            @RequestParam String email,
                            Model model) {
        // Здесь можно добавить логику для нахождения пользователя по email
        // или создать посылку, если такой пользователь существует
        Parcel parcel = new Parcel();
        parcel.setTrackingCode(trackingCode);
        parcel.setStatus(status);
        parcel.setLocation(location);
        if (userService.findByEmail(email).isPresent()){
            parcel.setUser(userService.findByEmail(email).get());
            parcelService.createParcel(parcel);
            return "redirect:/admin/parcels";
        } else {
            model.addAttribute("error", "Ошибка при создании: отсутствует пользователь");
            return "admin/addParcel";
        }
    }

    // Страница для редактирования посылки
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/parcels/edit/{id}")
    public String editParcelPage(@PathVariable Long id, Model model) {
        Parcel parcel = parcelService.getParcelById(id);
        model.addAttribute("parcel", parcel);
        return "admin/editParcel"; // Шаблон для редактирования посылки
    }

    // Обработка формы редактирования посылки
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/parcels/edit/{id}")
    public String editParcel(@PathVariable Long id,
                             @RequestParam String trackingCode,
                             @RequestParam String status,
                             @RequestParam String location) {
        Parcel parcel = parcelService.getParcelById(id);
        if (parcel != null) {
            parcel.setTrackingCode(trackingCode);
            parcel.setStatus(status);
            parcel.setLocation(location);
            parcelService.updateParcel(parcel.getId(), parcel.getStatus(), parcel.getLocation());
        }
        return "redirect:/admin/parcels";
    }

    // Удаление посылки
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/parcels/delete/{id}")
    public String deleteParcel(@PathVariable Long id) {
        parcelService.deleteParcel(id);
        return "redirect:/admin/parcels";
    }

    // Поиск посылок по email пользователя
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/parcels/search")
    public String searchParcelByEmail(@RequestParam String email, Model model) {
        if (!email.isEmpty()){
            model.addAttribute("parcels", parcelService.findAllByUserEmail(email));
        } else
            model.addAttribute("parcels", parcelService.findAll());
        log.info(email);
        return "admin/parcels"; // Шаблон для отображения результатов поиска
    }
}
