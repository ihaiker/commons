package com.yipingfang.commons.api;

import com.yipingfang.commons.api.annotation.ReturnStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class SynchronousCallAdapterFactory extends CallAdapter.Factory {

    public static CallAdapter.Factory create() {
        return new SynchronousCallAdapterFactory();
    }

    @Override
    public CallAdapter<Object, Object> get(final Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (returnType.toString().contains("retrofit2.Call")) {
            return null;
        }
        boolean returnHttpCode = false;
        for (int i = 0; i < annotations.length; i++) {
            Annotation annotation = annotations[i];
            if(annotation instanceof ReturnStatus){
                returnHttpCode = true;
            }
        }
        return new CallAdapterFactory(returnType,returnHttpCode);
    }

    @Data
    @AllArgsConstructor
    static class CallAdapterFactory implements CallAdapter{
        private Type responseType;
        private boolean returnHttpCode;

        @Override
        public Type responseType() {
            return this.responseType;
        }

        @Override
        public Object adapt(Call call) {
            Response response = null;
            try {
                response = call.execute();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if(returnHttpCode){
                return response.code();
            }else{

                return response.body();
            }
        }
    }
}