package com.mehedihasan.supervisorsolution.retrofits;

import com.mehedihasan.supervisorsolution.models.AcceptedGroupList;
import com.mehedihasan.supervisorsolution.models.GroupStatusList;
import com.mehedihasan.supervisorsolution.models.LoginResponse;
import com.mehedihasan.supervisorsolution.models.RequestedGroupList;
import com.mehedihasan.supervisorsolution.models.ServerResponse;
import com.mehedihasan.supervisorsolution.models.SupervisorList;
import com.mehedihasan.supervisorsolution.models.TitleDefense;
import com.mehedihasan.supervisorsolution.models.TopicList;

public interface MyApiService {
    void getTopicsFromServer(ResponseCallback<TopicList> responseCallback);

    void getSupervisorsFromServer(ResponseCallback<SupervisorList> responseCallback);

    void signUp(String name, String email, String password, ResponseCallback<ServerResponse> responseCallback);

    void loginOrSignIn(String name, String email, String password, String token, String userRole, String loginType, ResponseCallback<LoginResponse> responseCallback);

    void verifyEmail(String email, int verificationCode, ResponseCallback<ServerResponse> responseCallback);

    void forgotPassword(String email, ResponseCallback<ServerResponse> responseCallback);

    void resetPassword(String email, int verificationCode, String newPassword, ResponseCallback<ServerResponse> responseCallback);

    void changePassword(String email, String currentPassword, String newPassword, ResponseCallback<ServerResponse> responseCallback);

    void groupListStatus(String groupEmail, ResponseCallback<GroupStatusList> responseCallback);

    void requestedGroupList(String supervisorEmail, ResponseCallback<RequestedGroupList> responseCallback);

    void acceptedGroupList(String supervisorEmail, ResponseCallback<AcceptedGroupList> responseCallback);

    void groupAcceptOrDecline(String supervisorEmail, String groupEmail, int acceptOrDecline, ResponseCallback<ServerResponse> responseCallback);

    void titleDefenseRegistration(TitleDefense titleDefense, ResponseCallback<ServerResponse> responseCallback);
}
