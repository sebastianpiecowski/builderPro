package com.pl.exaco.builder_pro.controller;

import com.pl.exaco.builder_pro.dto.FileDTO;
import com.pl.exaco.builder_pro.entity.FileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pl.exaco.builder_pro.service.FileService;
import com.pl.exaco.builder_pro.utils.DiawiApiService;
import com.pl.exaco.builder_pro.utils.StatusResponse;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;

import java.util.List;

@RestController
@RequestMapping(value = "/builderpro/v1")
public class FileController {

	@Autowired
	private FileService fileService;

	@GetMapping(value="/file")
	public ResponseEntity<List<FileDTO>> getFiles(){
		return new ResponseEntity<>(fileService.getFiles(), HttpStatus.OK);
	}
	@GetMapping(value="/file/{fileId}")
	public ResponseEntity<FileDTO> getFile(@PathVariable("fileId") Integer id) {
		return new ResponseEntity<>(fileService.getFile(id), HttpStatus.OK);
	}

    @PostMapping(value = "/SendAppToFtp")
    public String SendAppToFtp(@RequestParam("name") String name, @RequestPart("file") MultipartFile file) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://upload.diawi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        DiawiApiService service = retrofit.create(DiawiApiService .class);

        //Call<UploadResponse> uploadCall = service.uploadFile(okhttp3.RequestBody.create(MediaType.parse("multipart/form-data"), "DGePpPm1BQX9wZtq4BfYSo6lUfoJrp8pmL8n5NEoBr"),file);


        String token = "DGePpPm1BQX9wZtq4BfYSo6lUfoJrp8pmL8n5NEoBr";
        String job = "GfB3aMPa3t5g4XPgcIhohTudZyvDLb48N4anclvlis";


        //Loop checking status
        try {
            Call<StatusResponse> statusCall;
            StatusResponse status;
            do {
                statusCall = service.checkStatus(token, job);
                retrofit2.Response<StatusResponse> response = statusCall.execute();
                status = response.body();
            } while (status.getStatus() != 2000 && status.getStatus() != 4000);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "OK";
    }

    @PostMapping(value= "/file/{id}")
    public void ChangeFileStatus(@Path("id") Integer fileId, @RequestBody Integer statusId){
        if(fileId != null && statusId != null) {
            fileService.updateFileStatus(fileId, statusId);
        } else {

        }

    }

}
