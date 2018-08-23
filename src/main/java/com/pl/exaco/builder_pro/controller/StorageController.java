package com.pl.exaco.builder_pro.controller;

import com.pl.exaco.builder_pro.dto.StorageInfoDTO;
import com.pl.exaco.builder_pro.utils.AuthenticationHelper;
import com.pl.exaco.builder_pro.utils.Configuration;
import com.pl.exaco.builder_pro.utils.FileAdapter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = Configuration.VERSION)
public class StorageController {

    @GetMapping(value = "/storage")
    private ResponseEntity<StorageInfoDTO> getStorageInfo(@RequestHeader(AuthenticationHelper.HEADER_FIELD) String token) throws IOException {
        try {
            AuthenticationHelper.Authorize(token);
        } catch (Exception e){
            return  new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            return new ResponseEntity<>(FileAdapter.getStorageData(), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
