package guru.qa.niffler.api;

import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.CurrencyJson;
import guru.qa.niffler.model.FriendJson;
import guru.qa.niffler.model.SessionJson;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.model.StatisticJson;
import guru.qa.niffler.model.UserJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

public interface GatewayApi {

    @GET("/api/categories/all")
    Call<List<CategoryJson>> getCategories(@Header("Authorization") String bearerToken);

    @POST("/api/categories/add")
    Call<CategoryJson> addCategory(@Header("Authorization") String bearerToken, @Body CategoryJson category);

    @GET("/api/currencies/all")
    Call<List<CurrencyJson>> getAllCurrencies(@Header("Authorization") String bearerToken);

    @GET("/api/friends/all")
    Call<List<UserJson>> friends(@Header("Authorization") String bearerToken);

    @DELETE("/api/friends/remove")
    Call<Void> removeFriend(@Header("Authorization") String bearerToken,
                            @Query("username") String targetUsername);

    @GET("/api/invitations/income")
    Call<List<UserJson>> incomeInvitations(@Header("Authorization") String bearerToken);

    @GET("/api/invitations/outcome")
    Call<List<UserJson>> outcomeInvitations(@Header("Authorization") String bearerToken);

    @POST("/api/invitations/send")
    Call<UserJson> sendInvitation(@Header("Authorization") String bearerToken,
                                  @Body FriendJson friend);

    @POST("/api/invitations/accept")
    Call<UserJson> acceptInvitation(@Header("Authorization") String bearerToken,
                                    @Body FriendJson friend);

    @POST("/api/invitations/decline")
    Call<UserJson> declineInvitation(@Header("Authorization") String bearerToken,
                                     @Body FriendJson friend);

    @GET("/api/session/current")
    Call<SessionJson> session(@Header("Authorization") String bearerToken);

    @GET("/api/spends/all")
    Call<List<SpendJson>> getSpends(@Header("Authorization") String bearerToken);

    @POST("/api/spends/add")
    Call<SpendJson> addSpend(@Body SpendJson spend,
                             @Header("Authorization") String bearerToken);

    @PATCH("/api/spends/edit")
    Call<SpendJson> editSpend(@Body SpendJson spend,
                              @Header("Authorization") String bearerToken);

    @DELETE("/api/spends/remove")
    Call<Void> deleteSpends(@Header("Authorization") String bearerToken,
                            @Query("ids") List<String> ids);

    @GET("/api/stat/total")
    Call<List<StatisticJson>> getTotalStatistic(@Header("Authorization") String bearerToken);

    @GET("/api/users/current")
    Call<UserJson> currentUser(@Header("Authorization") String bearerToken);

    @GET("/api/users/all")
    Call<List<UserJson>> allUsers(@Header("Authorization") String bearerToken);

    @POST("/api/users/update")
    Call<UserJson> updateUserInfo(@Header("Authorization") String bearerToken);

}
