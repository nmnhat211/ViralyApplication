package com.example.viralyapplication.repository;

import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import androidx.arch.core.executor.TaskExecutor;

import com.bumptech.glide.load.engine.Resource;
import com.example.viralyapplication.R;
import com.example.viralyapplication.repository.api.LoginApi;
import com.example.viralyapplication.repository.model.LoginModel;
import com.example.viralyapplication.repository.model.UserModel;
import com.example.viralyapplication.ui.activity.SignInActivity;
import com.example.viralyapplication.ui.event.SigInEvent;
import com.example.viralyapplication.utility.Constrain;
import com.example.viralyapplication.utility.NetworkProfile;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallApi {




    public static void createAccount(String username, String name, String email, String password, String filterName) {
        String urlImage = "null";
    }


    public static void loginAuthAccount(String username, String password, String filterName) {
        LoginApi mLoginApi = NetworkProfile.getRetrofitInstance().create(LoginApi.class);
        Map<String, String> requestBody = new HashMap<>();

        requestBody.put("email", username);
        requestBody.put("password", password);

        Call<LoginModel> callRequest = mLoginApi.loginAccount(requestBody);
        callRequest.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                int status = response.code();
                if (status == 200) {
                    Log.e("StatusCode", "" +response.body().getAccount());
                    EventBus.getDefault().post(new SigInEvent(true, null, response.body().getAccount(), filterName));
                } else if (status == Constrain.IS_FORBIDDEN){
                    Log.e("StatusCode", "" +response.code());
                }
                else {
                    String errorMessage = Resources.getSystem().getString(R.string.errorServer_text);
                    EventBus.getDefault().post(new SigInEvent(false, errorMessage, null, filterName));
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                EventBus.getDefault().post(new SigInEvent(false, t.getMessage(), null, filterName));
                Log.e("onFailure: ", "" + t.getMessage());
            }
        });

    }



//        TaskExecuter.getInstance(app).execute(() -> {
//            try {
//                String response = OrderRequest.updateStatusOrder(orderNumber, status);
//                APIResultObject object = new APIResultObject(response);
//                if (Constant.STATUS_0.equalsIgnoreCase(String.valueOf(object.getStatus()))) {
//                    String result = object.getResult();
//                    JSONObject jsonObject = new JSONObject(result);
//                    OrderStatusItem orderStatusItem = new OrderStatusItem(jsonObject);
//                    EventBus.getInstance().post(new GetStatusItemOrderEvent(true, "", orderStatusItem, null, filterName));
//
//                } else {
//                    String errorMessage = "null";
//                    if (Constant.ORDER_STATUS_INVALID.equalsIgnoreCase(object.getStatusCode())) {
//                        errorMessage = Constant.ORDER_STATUS_INVALID;
//                    }else if (Constant.ORDER_COMPLETED.equalsIgnoreCase(object.getStatusCode())) {
//                        errorMessage = Constant.ORDER_COMPLETED;
//                    }
//
//                    EventBus.getInstance().post(new GetStatusItemOrderEvent(
//                            false, errorMessage, null, null, filterName));
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                EventBus.getInstance().post(new GetStatusItemOrderEvent(false, Utils.getErrorMessage(app, e), null,null, filterName));
//            }
//        });
//    }

}
