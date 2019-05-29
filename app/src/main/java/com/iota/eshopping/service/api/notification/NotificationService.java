package com.iota.eshopping.service.api.notification;

import com.iota.eshopping.model.UserPlayerId;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author yeakleang.ay on 8/22/18.
 */

public interface NotificationService {

    @POST("V2/eshopping/customer/registerNotification")
    Observable<List<UserPlayerId>> saveUserPlayerId(@Body UserPlayerId userPlayerId);

}
