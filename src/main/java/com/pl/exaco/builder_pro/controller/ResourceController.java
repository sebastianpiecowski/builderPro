package com.pl.exaco.builder_pro.controller;

import com.pl.exaco.builder_pro.utils.Configuration;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

@RestController
@RequestMapping(value = Configuration.VERSION)
public class ResourceController {

    @GetMapping(value = "/icons/{icon}")
    private void GetIcon(HttpServletResponse response, @PathVariable("icon") String icon) {
        //set right MediaType (depending on the given icon)
        String extension = icon.substring(icon.lastIndexOf('.'));
        switch (extension) {
            case ".png":
                response.setContentType(MediaType.IMAGE_PNG_VALUE);
                break;
            case ".jpg":
                response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        }
        try {
            File newFile = new File("storage/icons/" + icon);
            StreamUtils.copy(new FileInputStream(newFile), response.getOutputStream());
        } catch (Exception e) {
            //optional, web-app should handle no icon returned
        }
    }

    @GetMapping(value = "/logs", produces = MediaType.TEXT_HTML_VALUE)
    private void GetLogs(HttpServletResponse response) {
        response.setContentType("text/html");
        try {
            File newFile = new File("logs/access.html");
            StreamUtils.copy(new FileInputStream(newFile), response.getOutputStream());
        } catch (Exception e) {
            //optional, web-app should handle no icon returned
        }
    }
}
