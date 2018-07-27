package com.pl.exaco.builder_pro.controller;

import com.google.gson.JsonObject;
import com.pl.exaco.builder_pro.dto.FileDTO;
import com.pl.exaco.builder_pro.dto.IdDTO;
import com.pl.exaco.builder_pro.entity.BuildEntity;
import com.pl.exaco.builder_pro.entity.FileEntity;
import com.pl.exaco.builder_pro.entity.ProjectEntity;
import com.pl.exaco.builder_pro.model.FileStatusUpdateRequest;
import com.pl.exaco.builder_pro.service.BuildService;
import com.pl.exaco.builder_pro.service.FileService;
import com.pl.exaco.builder_pro.service.ProjectService;
import com.pl.exaco.builder_pro.utils.appNameParser;
import com.pl.exaco.builder_pro.utils.diawi.DiawiService;
import com.pl.exaco.builder_pro.utils.diawi.StatusResponse;
import com.pl.exaco.builder_pro.utils.diawi.UploadResponse;
import com.pl.exaco.builder_pro.utils.multipartFileParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import retrofit2.http.Path;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/builderpro/v1")
public class FileController {

    @Autowired
    private FileService fileService;
    @Autowired
    private BuildService buildService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private DiawiService diawiService;

    @GetMapping(value = "/file")
    public ResponseEntity<List<FileDTO>> getFiles() {
        return new ResponseEntity<>(fileService.getFiles(), HttpStatus.OK);
    }

    @GetMapping(value = "/file/{fileId}")
    public ResponseEntity<FileDTO> getFile(@PathVariable("fileId") Integer id) {
        return new ResponseEntity<>(fileService.getFile(id), HttpStatus.OK);
    }

    @PostMapping(value = "/SendAppToFtp", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IdDTO> SendAppToFtp(@RequestPart("file") MultipartFile file) {
        if (file != null) {
            try {
                File savedFile = multipartFileParser.parseMultipartFileToFile(file);
                UploadResponse uploadResponse = diawiService.uploadFile(savedFile);
                StatusResponse status = diawiService.getJobFinalStatus(uploadResponse.getJob());
                if (status.getStatus() == 2000) {
                    Map<String, String> applicationInfo = appNameParser.parseApk(savedFile.getName(), status);
                    ProjectEntity project = projectService.findOrAddProject(applicationInfo);
                    BuildEntity build = buildService.findOrAddBuild(project, applicationInfo);
                    IdDTO id = new IdDTO();
                    id.setId(fileService.addFile(build, applicationInfo));
                    return new ResponseEntity<>(id, HttpStatus.OK);
                } else if (status.getStatus() == 4000) {
                    if (!savedFile.delete()) {
                        System.err.println("File could not be deleted. Something went wrong.");
                    } else {
                        System.err.println("File was deleted");
                    }
                }
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
            }
        }
        return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }

    @PostMapping(value = "/file/{id}")
    public ResponseEntity<Void> ChangeFileStatus(@PathVariable("id") Integer id, @RequestBody FileStatusUpdateRequest request) {
        try {
            fileService.updateFileStatus(id, request.getStatusId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

}
