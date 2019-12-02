package org.alie.aliehermes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Alie on 2019/12/2.
 * 类描述
 * 版本
 */
public class Response implements Parcelable {
    protected Response(Parcel in) {
    }

    public static final Creator<Response> CREATOR = new Creator<Response>() {
        @Override
        public Response createFromParcel(Parcel in) {
            return new Response(in);
        }

        @Override
        public Response[] newArray(int size) {
            return new Response[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
