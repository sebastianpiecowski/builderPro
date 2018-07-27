package com.pl.exaco.builder_pro.utils.diawi;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.File;
import java.io.IOException;

@Service
public class DiawiService {

    public static final int DIAWI_OK = 2000;
    public static final int DIAWI_ERROR_ = 4000;
    public static final int DIAWI_IN_PROGRESS = 2001;

    private static final String TOKEN = "DGePpPm1BQX9wZtq4BfYSo6lUfoJrp8pmL8n5NEoBr";

    public UploadResponse uploadFile(File file) throws IOException {
        Tika tika = new Tika();
        okhttp3.RequestBody requestFile = okhttp3.RequestBody.create(MediaType.parse(tika.detect(file)), file);
        DiawiApiService service = DiawiClientWrapper.getRetrofitDiawiBuilder();
        MultipartBody.Part fileBody = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        Call<UploadResponse> uploadCall = service.uploadFile(okhttp3.RequestBody.create(MediaType.parse("text/plain"), TOKEN), fileBody);
        UploadResponse uploadResponse;
        retrofit2.Response<UploadResponse> response = uploadCall.execute();
        uploadResponse = response.body();
        return uploadResponse;
    }

    public StatusResponse getJobFinalStatus(String job) throws IOException {
        DiawiApiService service = DiawiClientWrapper.getRetrofitDiawiBuilder();
        StatusResponse status;
        Call<StatusResponse> statusCall;
        do {
            statusCall = service.checkStatus(TOKEN, job);
            retrofit2.Response<StatusResponse> response = statusCall.execute();
            status = response.body();
        } while (status.getStatus() != DIAWI_OK && status.getStatus() != DIAWI_ERROR_);
        return status;
    }

    public StatusResponse uploadFileAndWaitForResponse(File file) throws IOException {
        UploadResponse uploadResponse = uploadFile(file);
        return getJobFinalStatus(uploadResponse.getJob());
    }

}
