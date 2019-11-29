package com.sovannarith.imageuploader.controller;

import com.sovannarith.imageuploader.service.ImageUploadService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UploadController {

    private final ImageUploadService uploadService;

    public UploadController(ImageUploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("image") MultipartFile image) throws IOException {
        return uploadService.upload(image);
    }

    @GetMapping("/preseed")
    public String preseed(){
        return "## Options to set on the command line\n" +
                "d-i debian-installer/locale string en_US\n" +
                "d-i console-setup/ask_detect boolean false\n" +
                "d-i console-setup/layoutcode string us\n" +
                "d-i netcfg/get_hostname string unassigned-hostname\n" +
                "d-i netcfg/get_domain string unassigned-domain\n" +
                "\n" +
                "d-i netcfg/choose_interface select auto\n" +
                "d-i netcfg/wireless_wep string\n" +
                "\n" +
                "d-i base-installer/kernel/override-image string linux-server\n" +
                "d-i clock-setup/utc-auto boolean true\n" +
                "d-i clock-setup/utc boolean true\n" +
                "d-i time/zone string US/Pacific\n" +
                "d-i clock-setup/ntp boolean true\n" +
                "\n" +
                "d-i mirror/country string US\n" +
                "d-i mirror/http/proxy string\n" +
                "d-i pkgsel/install-language-support boolean false\n" +
                "tasksel tasksel/first multiselect standard, ubuntu-server\n" +
                "\n" +
                "d-i partman-auto/method string regular\n" +
                "d-i partman-auto/purge_lvm_from_device boolean true\n" +
                "d-i partman-lvm/confirm boolean true\n" +
                "d-i partman-auto/choose_recipe select atomic\n" +
                "d-i partman/confirm_write_new_label boolean true\n" +
                "d-i partman/choose_partition select finish\n" +
                "d-i partman/confirm boolean true\n" +
                "d-i passwd/user-fullname string Ubuntu User\n" +
                "d-i passwd/username string ubuntu\n" +
                "d-i passwd/user-password password insecure\n" +
                "d-i passwd/user-password-again password insecure\n" +
                "\n" +
                "d-i grub-installer/only_debian boolean true\n" +
                "d-i grub-installer/with_other_os boolean true\n" +
                "d-i finish-install/reboot_in_progress note";
    }


}
