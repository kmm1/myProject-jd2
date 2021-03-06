package com.itacademy.controller;


import com.itacademy.entity.*;
import com.itacademy.service.ProfileService;
import com.itacademy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Controller
public class ProfileController {

    private final ProfileService profileService;
    private final UserService userService;

    @Autowired
    public ProfileController(ProfileService profileService, UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }

    @ModelAttribute("roles")
    public Collection<? extends GrantedAuthority> roles() {
        Collection<? extends GrantedAuthority> roles = SecurityContextHolder.getContext()
                .getAuthentication().getAuthorities();
        return roles;
    }

    @ModelAttribute("userName")
    public String userName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = ((UserDetails) principal).getUsername();
        return userName;
    }

    @ModelAttribute("profile")
    public Profile profile() {
        return new Profile();
    }

    @ModelAttribute("allEnumGender")
    public List<EnumGender> enumGenders() {
        return Arrays.asList(EnumGender.values());
    }

    @ModelAttribute("allEnumMaritalStatus")
    public List<EnumMaritalStatus> enumMaritalStatus() {
        return Arrays.asList(EnumMaritalStatus.values());
    }

    @ModelAttribute("days")
    public List<Integer> days() {
        ArrayList<Integer> allDays = new ArrayList<>();
        for (int i = 1; i < 32; i++) {
            allDays.add(i);
        }
        return allDays;
    }

    @ModelAttribute("months")
    public List<Integer> months() {
        ArrayList<Integer> allMonth = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            allMonth.add(i);
        }
        return allMonth;
    }

    @ModelAttribute("years")
    public List<Integer> years() {
        ArrayList<Integer> allYears = new ArrayList<>();
        for (int i = 1900; i < 2018; i++) {
            allYears.add(i);
        }
        return allYears;
    }

    @ModelAttribute("allMonth")
    public List<Integer> month() {
        ArrayList<Integer> month = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            month.add(i);
        }
        return month;
    }

    @GetMapping(path = "/profile")
    public String showMyProfile(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = ((UserDetails) principal).getUsername();
        Long userId = userService.findOneUserByName(userName).getId();
        List<Profile> profile2 = profileService.findProfileByUserId(userId);
        if (profile2.isEmpty()) {
            return "profile-form-save";
        } else
            model.addAttribute("profile", profile2);
        model.addAttribute("userName", userName);
        return "/profile";
    }

    @GetMapping(path = "/addProfile")
    public String profileAdd(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = ((UserDetails) principal).getUsername();
        Long userId = userService.findOneUserByName(userName).getId();
        Profile profile = profileService.findOneProfileByUserId(userId);
        model.addAttribute("profile", profile);
        return "profile-form-update";
    }

    @PostMapping(path = "/saveProfile")
    public String profileSave(Profile profile, Model model,
                              @RequestParam String aboutMe,
                              @RequestParam int dayOfBirth,
                              @RequestParam int monthOfBirth,
                              @RequestParam int yearOfBirth,
                              @RequestParam EnumGender gender,
                              @RequestParam String homeCity,
                              @RequestParam String homeCountry,
                              @RequestParam EnumMaritalStatus maritalStatus,
                              @RequestParam String workCountry,
                              @RequestParam String workCity) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = ((UserDetails) principal).getUsername();
        SystemUser user = userService.findOneUserByName(userName);
        Long userId = userService.findOneUserByName(userName).getId();

        profile.setUser(user);
        profile.setAboutMe(aboutMe);
        profile.setHomeAddress(new Address(homeCountry, homeCity));
        profile.setWorkAddress(new Address(workCountry, workCity));
        profile.setBirthday(new Birthday(yearOfBirth, monthOfBirth, dayOfBirth));
        profile.setGender(gender);
        profile.setMaritalStatus(maritalStatus);

        Long id = profileService.save(profile);
        List<Profile> profile2 = profileService.findProfileByUserId(userId);
        model.addAttribute("profile", profile2);
        model.addAttribute("userName", userName);
        return "profile";
    }

    @PostMapping(path = "/updateProfile")
    public String profileUpdate(Model model,
                                @RequestParam String aboutMe,
                                @RequestParam int dayOfBirth,
                                @RequestParam int monthOfBirth,
                                @RequestParam int yearOfBirth,
                                @RequestParam EnumGender gender,
                                @RequestParam String homeCity,
                                @RequestParam String homeCountry,
                                @RequestParam EnumMaritalStatus maritalStatus,
                                @RequestParam String workCountry,
                                @RequestParam String workCity) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = ((UserDetails) principal).getUsername();
        SystemUser user = userService.findOneUserByName(userName);
        Long userId = userService.findOneUserByName(userName).getId();
        List<Profile> profile2 = profileService.findProfileByUserId(userId);
        Profile profile = profile2.get(0);

        profile.setAboutMe(aboutMe);
        profile.setHomeAddress(new Address(homeCountry, homeCity));
        profile.setWorkAddress(new Address(workCountry, workCity));
        profile.setBirthday(new Birthday(yearOfBirth, monthOfBirth, dayOfBirth));
        profile.setGender(gender);
        profile.setMaritalStatus(maritalStatus);

        profileService.update(profile);
        List<Profile> profile3 = profileService.findProfileByUserId(userId);
        model.addAttribute("profile", profile3);
        model.addAttribute("userName", userName);
        return "profile";
    }
}
