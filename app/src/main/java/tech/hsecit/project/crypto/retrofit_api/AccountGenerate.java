package tech.hsecit.project.crypto.retrofit_api;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AccountGenerate {
    @GET("api/gen/{uid}")
    Call<Void> generateAccount(@Path("uid") String UID);
}
