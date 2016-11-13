package tech.paypal.app.ngo.vexcel.network.config;


import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import tech.paypal.app.ngo.vexcel.model.customers.Customer;
import tech.paypal.app.ngo.vexcel.model.forgotpassword.ForgotPassword;
import tech.paypal.app.ngo.vexcel.model.group.GroupCreateModel;
import tech.paypal.app.ngo.vexcel.model.login.Login;
import tech.paypal.app.ngo.vexcel.model.member.MemberCreateData;
import tech.paypal.app.ngo.vexcel.model.profile.ProfileConfig;
import tech.paypal.app.ngo.vexcel.network.responses.EmptyForgotResponse;
import tech.paypal.app.ngo.vexcel.network.responses.GroupRestResponse;
import tech.paypal.app.ngo.vexcel.network.responses.LoginRestResponse;
import tech.paypal.app.ngo.vexcel.network.responses.MemberDataRestResponse;
import tech.paypal.app.ngo.vexcel.network.responses.RegisterResponse;
import tech.paypal.app.ngo.vexcel.network.responses.group.GroupUpdateRestResponse;
import tech.paypal.app.ngo.vexcel.network.responses.group.Result;
import tech.paypal.app.ngo.vexcel.network.responses.UpdateProfileRestResponse;
import tech.paypal.app.ngo.vexcel.model.registration.Registration;
import tech.paypal.app.ngo.vexcel.network.responses.member.ResultMember;

/**
 * Created by chokkar
 */

public interface Restapi {
    @POST("/auth/login/")
    void login(@Body Login login, Callback<LoginRestResponse> callback);


    @GET("/api/customers/")
    void getCustomersList(Callback<List<Customer>> callback);

    @POST("/auth/password/reset/")
    void resetUserLogin(@Body ForgotPassword forgotPassword, Callback<EmptyForgotResponse> callback);

    @POST("/auth/register/")
    void register(@Body Registration registration, Callback<RegisterResponse> callback);

    @GET("/auth/me/")
    void userProfile(@Header("Authorization") String token, Callback<UpdateProfileRestResponse> callback);

    @PUT("/auth/me/")
    void userUpdateProfile(@Header("Authorization") String token, @Body ProfileConfig profileConfig, Callback<UpdateProfileRestResponse> callback);

    @POST("/auth/logout/")
    void logout(@Header("Authorization") String token, @Body Object dummy, Callback<EmptyForgotResponse> callback);

    @GET("/v1/groups/")
    void groupsList(@Header("Authorization") String token, Callback<GroupRestResponse> callback);

    @DELETE("/v1/groups/{id}/")
    void groupDatasDelete(@Header("Authorization") String token, @Path("id") String groupId, Callback<EmptyForgotResponse> callback);

    @POST("/v1/groups/")
    void groupCreate(@Header("Authorization") String token, @Body GroupCreateModel groups, Callback<Result> callback);

    @GET("/v1/groups/{id}/members/")
    void membersList(@Header("Authorization") String token, @Path("id") String groupId, Callback<MemberDataRestResponse> callback);

    @PUT("/v1/groups/{id}/")
    void groupUpdate(@Header("Authorization") String token, @Path("id") String groupId, @Body GroupCreateModel groups, Callback<GroupUpdateRestResponse> callback);

    @POST("/v1/groups/{id}/members/")
    void membersCreate(@Header("Authorization") String token, @Path("id") String groupId, @Body MemberCreateData memberGroups, Callback<ResultMember> callback);

    /*@POST("/account/auth/{auth_type}/")
    void loginWithOauth(@Path("auth_type") String auth_type, @Body DeviceConfigAuth deviceConfigAuth, Callback<OauthResponse> callback);

   @POST("/account/register/")
    void register(@Body Registration registration, Callback<RegisterRestResponse> callback);

    @GET("/account/users/")
    void userProfile(@Header("Authorization") String token, Callback<UpdateProfileRestResponse> callback);

    @PUT("/account/resetpasswd/")
    void resetUserLogin(@Body ResetCreditionals resetCreditionals, Callback<LoginRestResponse> callback);

    @PUT("/account/users/")
    void userUpdateProfile(@Header("Authorization") String token, @Body ProfileConfig profileConfig, Callback<UserProfileUpdationRestResponse> callback);

    @POST("/account/setpasswd/")
    void changePassword(@Header("Authorization") String token, @Body ChangePassword changePassword, Callback<RegisterRestResponse> callback);

    @POST("/account/logout/")
    void logout(@Header("Authorization") String token, @Body Object dummy, Callback<RegisterRestResponse> callback);

    @POST("/groups/")
    void groupCreate(@Header("Authorization") String token, @Body Groups groups, Callback<GroupRestResponse> callback);

    @GET("/groups/")
    void groupsList(@Header("Authorization") String token, Callback<List<GroupRestResponseList>> callback);

    @GET("/groups/{id}")
    void groupsDatasFetch(@Header("Authorization") String token, @Path("id") String groupId, Callback<GroupRestResponse> callback);

    @DELETE("/groups/{id}/")
    void groupDatasDelete(@Header("Authorization") String token, @Path("id") String groupId, Callback<PlacesListRestResponse> callback);

    @PUT("/groups/{id}")
    void groupDatasUpdate(@Header("Authorization") String token, @Path("id") String groupId, Callback<GroupRestResponse> callback);

    @PUT("/groups/{id}/")
    void groupUpdate(@Header("Authorization") String token, @Path("id") String groupId, @Body Groups groups, Callback<GroupRestResponse> callback);


    @GET("/groups/{id}/places/")
    void placesList(@Header("Authorization") String token, @Path("id") String groupId, Callback<List<PlacesListRestResponse>> callback);

    @GET("/groups/{id}/members/")
    void membersList(@Header("Authorization") String token, @Path("id") String groupId, Callback<List<MembersDataRestResponses>> callback);

    @GET("/groups/{id}/members/{memberid}")
    void membersInfoFetch(@Header("Authorization") String token, @Path("id") String groupId, @Path("memberid") String memberId, Callback<MembersInfoRestResponse> callback);

    @POST("/groups/{groupId}/members/")
    void membersCreate(@Header("Authorization") String token, @Path("groupId") String groupId, @Body MemberGroups memberGroups, Callback<MemberCreateRestResponse> callback);

    @DELETE("/groups/{id}/members/{memberid}/")
    void membersDataDelete(@Header("Authorization") String token, @Path("id") String groupId, @Path("memberid") String memberId, Callback<MembersDeleteRestResponse> callback);

    @POST("/nodes/")
    void nodeCreate(@Header("Authorization") String token, @Body Node node, Callback<NodeRestResponse> callback);

    @GET("/nodes/")
    void nodesList(@Header("Authorization") String token, Callback<List<NodeRestResponse>> callback);

    @GET("/nodes/{id}")
    void nodeInfoFetch(@Header("Authorization") String token, @Path("id") String groupId, Callback<NodeRestResponse> callback);

    @DELETE("/nodes/{imei}/")
    void nodeDatasDelete(@Header("Authorization") String token, @Path("imei") String nodeImei, Callback<NodeDeleteRestResponse> callback);

    @PUT("/nodes/{imei}")
    void nodeDatasUpdate(@Header("Authorization") String token, @Path("imei") String nodeImei, @Body Node node, Callback<NodeRestResponse> callback);

    @POST("/groups/{group_id}/places/")
    void placesCreate(@Header("Authorization") String token, @Path("group_id") String groupId, @Body Places places, Callback<PlacesCreateRestResponse> callback);

    @DELETE("/groups/{group_id}/places/{place_id}/")
    void placeDatasDelete(@Header("Authorization") String token, @Path("group_id") String groupId, @Path("place_id") String placeId, Callback<Void> callback);

    @GET("/groups/{group_id}/places/{place_id}")
    void placeDetailsFetch(@Header("Authorization") String token, @Path("group_id") String groupId, @Path("place_id") String placeId, Callback<PlaceDetailsRestResponse> callback);

    @PUT("/groups/{group_id}/places/{place_id}/")
    void placeDatasUpdate(@Header("Authorization") String token, @Path("group_id") String groupId, @Path("place_id") String placeId, @Body Places places, Callback<PlacesUpdateResponse> callback);

    @POST("/sync/{imei}/")
    void syncLocationUpdate(@Header("Authorization") String token, @Path("imei") String nodeImei, @Body SyncLocAttribute syncLocAttribute, Callback<LoginRestResponse> callback);

    @GET("/info/?type=places")
    void getUsersCurrentPlaces(@Header("Authorization") String token, Callback<List<SyncLocationResponse>> callback);


    @POST("/resources/")
    void createResources(@Header("Authorization") String token, @Body CreateResource createResource, Callback<CreateResourceResponse> callback);

    @PUT("/resources/{id}/")
    void updateResources(@Header("Authorization") String token, @Path("id") String placeId, @Body CreateResource createResource, Callback<CreateResourceResponse> callback);

    @GET("/resources/")
    void getResources(@Header("Authorization") String token, Callback<List<CreateResourceResponse>> callback);

    @POST("/account/payments/")
    void createPayment(@Header("Authorization") String token, @Body AccountData accountData, Callback<WalletAccount> callback);

    @GET("/account/payments/")
    void getPaymentsList(@Header("Authorization") String token, Callback<List<WalletAccountResponse>> callback);

    @GET("/resources/types/")
    void getResourceTypes(@Header("Authorization") String token, Callback<List<ResourceType>> callback);

    @POST("/resources/{resourceId}/plans/")
    void planCreation(@Header("Authorization") String token, @Path("resourceId") String resourceId, @Body Plan plan, Callback<PlansResponse> callback);

    @PUT("/resources/{resourceId}/plans/{planId}/")
    void planUpdation(@Header("Authorization") String token, @Path("resourceId") String resourceId, @Path("planId") String planId, @Body Plan plan, Callback<PlansResponse> callback);

    @GET("/resources/{resourceId}/plans/{planId}/")
    void getPlanData(@Header("Authorization") String token, @Path("resourceId") String resourceId, @Path("planId") String planId, Callback<PlansResponse> callback);

    @GET("/resources/{resourceId}/plans/")
    void getPlansList(@Header("Authorization") String token, @Path("resourceId") String resourceId, Callback<List<PlansResponse>> callback);

    @DELETE("/resources/{resource_id}/")
    void deleteResource(@Header("Authorization") String token, @Path("resource_id") String resourceId, Callback<LoginRestResponse> callback);

    @DELETE("/resources/{resource_id}/plans/{plans_id}/")
    void deletePlans(@Header("Authorization") String token, @Path("resource_id") String resourceId, @Path("plans_id") String plans_id, Callback<LoginRestResponse> callback);

    @GET("/resources/{resourceId}/promos/")
    void getPromoList(@Header("Authorization") String token, @Path("resourceId") String resourceId, Callback<List<PromoResponse>> callback);

    @POST("/resources/{resourceId}/promos/")
    void promoCreation(@Header("Authorization") String token, @Path("resourceId") String resourceId, @Body Promo promo, Callback<PromoResponse> callback);

    @DELETE("/account/payments/{payment_id}/")
    void deletePayment(@Header("Authorization") String token, @Path("payment_id") String paymentId, Callback<LoginRestResponse> callback);

    @DELETE("/resources/{resource_id}/promos/{promo_id}/")
    void deletePromo(@Header("Authorization") String token, @Path("resource_id") String resourceId, @Path("promo_id") String promoId, Callback<LoginRestResponse> callback);

    @GET("/resources/{resourceId}/subscriptions/")
    void getSubscriptionList(@Header("Authorization") String token, @Path("resourceId") String resourceId, Callback<List<SubscriptionResponse>> callback);

    @POST("/resources/{resource_id}/subscriptions/")
    void subScriptionCreate(@Header("Authorization") String token, @Path("resource_id") String resourceId, @Body SubscriptionRequest subscriptionRequest, Callback<CreateSubscriptionResponse> callback);

    @DELETE("/resources/{resource_id}/subscriptions/{subscription_id}/")
    void deleteSubsription(@Header("Authorization") String token, @Path("resource_id") String resourceId, @Path("subscription_id") String subscriptionId, Callback<LoginRestResponse> callback);

    @PUT("/resources/{resource_id}/subscriptions/{subscription_id}/")
    void updateSubscription(@Header("Authorization") String token, @Path("resource_id") String resourceId, @Path("subscription_id") String subscriptionId, @Body SubscriptionRequest SubscriptionRequest, Callback<CreateSubscriptionResponse> callback);
*/
}
