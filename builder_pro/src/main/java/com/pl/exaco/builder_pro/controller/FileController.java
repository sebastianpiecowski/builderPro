package com.pl.exaco.builder_pro.controller;

import com.pl.exaco.builder_pro.dto.FileDTO;
import com.pl.exaco.builder_pro.service.BuildService;
import com.pl.exaco.builder_pro.service.FileService;
import com.pl.exaco.builder_pro.service.ProjectService;
import com.pl.exaco.builder_pro.utils.*;
import okhttp3.*;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    final static String DIAWI_API = "https://upload.diawi.com";
    final static String TOKEN = "DGePpPm1BQX9wZtq4BfYSo6lUfoJrp8pmL8n5NEoBr";

    @GetMapping(value = "/file")
    public ResponseEntity<List<FileDTO>> getFiles() {
        return new ResponseEntity<>(fileService.getFiles(), HttpStatus.OK);
    }

    @GetMapping(value = "/file/{fileId}")
    public ResponseEntity<FileDTO> getFile(@PathVariable("fileId") Integer id) {
        return new ResponseEntity<>(fileService.getFile(id), HttpStatus.OK);
    }

    @PostMapping(value = "/SendAppToFtp")
    public ResponseEntity<Void> SendAppToFtp(@RequestParam("name") String name, @RequestPart("file") MultipartFile file) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = setRetrofit(httpClient);
        DiawiApiService service = retrofit.create(DiawiApiService.class);
        File newFile = parseMultipartFileToFile(file);
        Tika tika = new Tika();

        try {
            okhttp3.RequestBody requestFile = okhttp3.RequestBody.create(MediaType.parse(tika.detect(newFile)), newFile);
            MultipartBody.Part fileBody = MultipartBody.Part.createFormData("file", file.getOriginalFilename(), requestFile);
            Call<UploadResponse> uploadCall = service.uploadFile(okhttp3.RequestBody.create(MediaType.parse("text/plain"), TOKEN), fileBody);
            UploadResponse uploadResponse = null;
            uploadResponse = getStatus(uploadCall);
            String job = uploadResponse.getJob();

            StatusResponse status = checkStatus(service, job);

            if (status.getStatus() == 2000) {
                Map<String,String> applicationInfo=appNameParser.parseApk(newFile.getName(), status);

                int projectId=projectService.findOrAddProject(applicationInfo);
                int buildId=buildService.findOrAddBuild(projectId, applicationInfo);

                System.out.println(projectId);
               // System.out.println(buildId);
                return new ResponseEntity<>(HttpStatus.OK);

            } else {
                if (!newFile.delete()) {
                    System.err.println("Nie udalo sie usunac pliku");
                } else {
                    System.err.println("Udalo sie usunac pliku");

                }
                return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping(value = "/file/{id}")
    public void ChangeFileStatus(@Path("id") Integer fileId, @RequestBody Integer statusId) {
        if (fileId != null && statusId != null) {
            //fileService.updateFileStatus(fileId, statusId);
        } else {

        }

    }



    //else







    private StatusResponse checkStatus(DiawiApiService service, String job) {
        StatusResponse status = null;
        try {
            Call<StatusResponse> statusCall;
            do {
                statusCall = service.checkStatus(TOKEN, job);
                retrofit2.Response<StatusResponse> response = statusCall.execute();
                status = response.body();
                System.out.println(status.getStatus());
            } while (status.getStatus() != 2000 && status.getStatus() != 4000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    private UploadResponse getStatus(Call<UploadResponse> uploadCall) {
        UploadResponse uploadResponse = new UploadResponse();
        try {
            retrofit2.Response<UploadResponse> response = uploadCall.execute();
            uploadResponse = response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadResponse;
    }

    private Retrofit setRetrofit(OkHttpClient.Builder httpClient) {
        // OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("Content-Type", "multipart/form-data").build();
                return chain.proceed(request);
            }
        });
        return new Retrofit.Builder()
                .baseUrl(DIAWI_API)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

    private File parseMultipartFileToFile(MultipartFile file) {
        try {
            Files.createDirectories(Paths.get("/storage/"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        File newFile=null;
        FileOutputStream fos;
        try {
            newFile = new File("storage/" + file.getOriginalFilename());
            fos = new FileOutputStream(newFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFile;
    }



}
