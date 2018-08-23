package com.pl.exaco.builder_pro.controller;

import com.pl.exaco.builder_pro.dto.FileDTO;
import com.pl.exaco.builder_pro.dto.IdDTO;
import com.pl.exaco.builder_pro.dto.UpdateFileStatusDTO;
import com.pl.exaco.builder_pro.entity.FileEntity;
import com.pl.exaco.builder_pro.model.FilePaginationRequest;
import com.pl.exaco.builder_pro.model.FileStatusUpdateRequest;
import com.pl.exaco.builder_pro.service.FileService;
import com.pl.exaco.builder_pro.utils.ApkInfo;
import com.pl.exaco.builder_pro.utils.AuthenticationHelper;
import com.pl.exaco.builder_pro.utils.Configuration;
import com.pl.exaco.builder_pro.utils.FileAdapter;
import com.pl.exaco.builder_pro.utils.diawi.DiawiService;
import com.pl.exaco.builder_pro.utils.diawi.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping(value = Configuration.VERSION)
public class FileController {

    @Autowired
    private FileService fileService;
    @Autowired
    private DiawiService diawiService;

    @PostMapping(value = "/file")
    private ResponseEntity<List<FileDTO>> getFiles(@RequestHeader(AuthenticationHelper.HEADER_FIELD) String token, @RequestBody FilePaginationRequest pagination) {
        try {
            AuthenticationHelper.Authorize(token);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(fileService.getFiles(pagination), HttpStatus.OK);

    }

    @GetMapping(value = "/file/{id}")
    private ResponseEntity<FileDTO> getFile(@PathVariable("id") Integer id, @RequestHeader(AuthenticationHelper.HEADER_FIELD) String token) {
        try {
            AuthenticationHelper.Authorize(token);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(fileService.getFile(id), HttpStatus.OK);
    }

    @PostMapping(value = "/SendAppToFtp", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<IdDTO> SendAppToFtp(@RequestPart("file") MultipartFile file, @RequestHeader(AuthenticationHelper.HEADER_FIELD) String token) {
        if (file != null) {
            try {
                try {
                    AuthenticationHelper.Authorize(token);
                } catch (Exception e) {
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }
                File savedFile = FileAdapter.parseMultipartFileToFile(file);
                ApkInfo apkInfo = new ApkInfo(savedFile.getName());
                StatusResponse status = diawiService.uploadFileAndWaitForResponse(savedFile);
                if (status.getStatus() == 2000) {
                    apkInfo.setDiawiUrl(status.getLink());

                    IdDTO id = new IdDTO();
                    id.setId(fileService.storeApk(apkInfo));
                    return new ResponseEntity<>(id, HttpStatus.OK);
                } else if (status.getStatus() == 4000) {
                    if (!savedFile.delete()) {
                        System.err.println("File could not be deleted. Something went wrong.");
                    } else {
                        System.err.println("File was deleted");
                    }
                }
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
            }
        }
        return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }

    @PostMapping(value = "/file/{id}")
    private ResponseEntity<UpdateFileStatusDTO> ChangeFileStatus(@PathVariable("id") Integer id, @RequestBody FileStatusUpdateRequest request, @RequestHeader(AuthenticationHelper.HEADER_FIELD) String token) {
        try {
            AuthenticationHelper.Authorize(token);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            return new ResponseEntity<>(fileService.updateFileStatus(id, request.getStatusId()), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/file/{id}/refresh")
    private ResponseEntity<Void> RefreshDiawiLink(@PathVariable("id") Integer id, @RequestHeader(AuthenticationHelper.HEADER_FIELD) String token) {
        try {
            AuthenticationHelper.Authorize(token);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            FileEntity fileEntity = fileService.findById(id);
            if (fileEntity != null) {
                File file = new File(Configuration.DIRECTORY_PATH + fileEntity.getFileName());
                StatusResponse status = diawiService.uploadFileAndWaitForResponse(file);
                if (status.getStatus() == 2000) {
                    fileService.updateFileDiawiLink(fileEntity.getId(), status.getLink());
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }

    @DeleteMapping(value = "/file/{id}")
    private ResponseEntity<Void> DeleteFile(@PathVariable("id") Integer id, @RequestHeader(AuthenticationHelper.HEADER_FIELD) String token) {
        try {
            AuthenticationHelper.Authorize(token);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            fileService.deleteFileFromDbAndStorage(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}