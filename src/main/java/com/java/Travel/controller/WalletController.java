package com.java.Travel.controller;

import com.java.Travel.controller.dto.ApiError;
import com.java.Travel.controller.dto.BankCardDTO;
import com.java.Travel.security.CustomUserDetail;
import com.java.Travel.service.WalletService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping
    public String getWalletForUserView(Model model, @AuthenticationPrincipal CustomUserDetail currUser) {
        model.addAttribute("wallet", walletService.findByUserId(currUser.getId()));
        return "wallet/walletForUser";
    }

    @GetMapping("/replenish")
    public String getReplenishBalanceView(Model model) {
        model.addAttribute("bankCard", new BankCardDTO());
        return "wallet/replenishBalance";
    }

    @PostMapping("/replenish")
    public String replenishBalance(@Valid @ModelAttribute("bankCard") BankCardDTO bankCard, BindingResult result,
                                   Model model, @AuthenticationPrincipal CustomUserDetail currUser) {

        if (result.hasErrors()) {
            ApiError apiError = new ApiError();
            String message = "";
            for (FieldError str : result.getFieldErrors()) {
                message += str.getDefaultMessage();
                apiError.setMessage(message);
            }
            model.addAttribute("bankCard", bankCard);
            model.addAttribute("error", apiError);
            return "wallet/replenishBalance";
        }

        walletService.replenishBalance(bankCard, currUser);
        return "redirect:/wallet";
    }

}
