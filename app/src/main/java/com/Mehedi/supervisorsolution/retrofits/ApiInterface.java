package com.mehedihasan.supervisorsolution.retrofits;

import com.mehedihasan.supervisorsolution.models.AcceptedGroupList;
import com.mehedihasan.supervisorsolution.models.GroupStatusList;
import com.mehedihasan.supervisorsolution.models.LoginResponse;
import com.mehedihasan.supervisorsolution.models.RequestedGroupList;
import com.mehedihasan.supervisorsolution.models.ServerResponse;
import com.mehedihasan.supervisorsolution.models.SupervisorList;
import com.mehedihasan.supervisorsolution.models.TitleDefense;
import com.mehedihasan.supervisorsolution.models.TopicList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiInterface {

    @GET("topic_list")
    Call<TopicList> getTopics();

    @GET("supervisor_list")
    Call<SupervisorList> getSupervisors();

    @FormUrlEncoded
    @POST("create_user")
    Call<ServerResponse> signUp(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login_or_signin")
    Call<LoginResponse> loginOrSignIn(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("token") String token,
            @Field("user_role") String userRole,
            @Field("login_type") String loginType
    );

    @FormUrlEncoded
    @POST("verification")
    Call<ServerResponse> verifyEmail(
            @Field("email") String email,
            @Field("verification_code") int verificationCode
    );

    @FormUrlEncoded
    @POST("forgot_password")
    Call<ServerResponse> forgotPassword(@Field("email") String email);

    @FormUrlEncoded
    @PUT("reset_password")
    Call<ServerResponse> resetPassword(
            @Field("email") String email,
            @Field("verification_code") int verificationCode,
            @Field("new_password") String newPassword
    );

    @FormUrlEncoded
    @PUT("change_password")
    Call<ServerResponse> changePassword(
            @Field("email") String email,
            @Field("current_password") String currentPassword,
            @Field("new_password") String newPassword
    );

    @FormUrlEncoded
    @POST("group_list_status")
    Call<GroupStatusList> groupListStatus(@Field("group_email") String groupEmail);

    @FormUrlEncoded
    @POST("requested_group_list")
    Call<RequestedGroupList> requestedGroupList(@Field("supervisor_email") String supervisorEmail);

    @FormUrlEncoded
    @POST("accepted_group_list")
    Call<AcceptedGroupList> acceptedGroupList(@Field("supervisor_email") String supervisorEmail);

    @FormUrlEncoded
    @POST("group_accept_or_decline")
    Call<ServerResponse> groupAcceptOrDecline(
            @Field("supervisor_email") String supervisorEmail,
            @Field("group_email") String groupEmail,
            @Field("accept_or_decline") int acceptOrDecline
    );

    @POST("title_defense_registration")
    Call<ServerResponse> titleDefenseRegistration(@Body TitleDefense titleDefense);
}
