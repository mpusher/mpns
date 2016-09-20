/*
 * (C) Copyright 2015-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     ohun@live.cn (夜色)
 */

package com.mpush.mpns.web.common;

import com.mpush.tools.Jsons;
import io.vertx.core.json.JsonObject;

/**
 * Created by ohun on 16/9/15.
 *
 * @author ohun@live.cn (夜色)
 */
public class ApiResult<T> {
    private int code;
    private T data;
    private String msg;
    private boolean success;

    public ApiResult(T data) {
        this.data = data;
        this.code = 200;
        this.success = true;
    }

    public ApiResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public ApiResult setCode(int code) {
        this.code = code;
        return this;
    }

    public T getData() {
        return data;
    }

    public ApiResult setData(T data) {
        this.data = data;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ApiResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public ApiResult setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    @Override
    public String toString() {
        return Jsons.toJson(this);
    }
}
