package com.iota.eshopping.service;

import com.iota.eshopping.model.Login;
import com.iota.eshopping.service.datahelper.datasource.online.GenerateToken;

/**
 * Created by channarith.bong on 1/10/18.
 */

public class ClientTokenService {
    private static ClientTokenService instance;
    private InvokeOnCompleteAsync onCompleteAsync;

    /**
     * @return
     */
    public static ClientTokenService getInstance() {
        if (instance == null) {
            instance = new ClientTokenService();
        }
        return instance;
    }

    /**
     * @param login
     * @param onCompleteAsync
     * @return
     */
    public void generateToken(Login login, InvokeOnCompleteAsync onCompleteAsync) {
        new GenerateToken(login, new GenerateToken.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(String tokenKey) {
                if (tokenKey != null) {
                    if (onCompleteAsync != null) {
                        onCompleteAsync.onComplete(tokenKey);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                onCompleteAsync.onError(e);
            }
        });
    }

    /**
     *
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(String token);

        void onError(Throwable e);
    }
}
