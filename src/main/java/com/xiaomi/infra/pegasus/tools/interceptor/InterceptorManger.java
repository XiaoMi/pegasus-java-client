package com.xiaomi.infra.pegasus.tools.interceptor;

import java.util.ArrayList;
import java.util.List;

public class InterceptorManger {

    List<TableInterceptor> interceptors = new ArrayList<>();

    public InterceptorManger add(TableInterceptor interceptor) {
        interceptors.add(interceptor);
        return this;
    }

    public void excuteBefore() {
        for(TableInterceptor interceptor: interceptors) {
            if(interceptor.isEnable()){
                interceptor.
            }
        }
    }

}
