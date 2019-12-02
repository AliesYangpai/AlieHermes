package org.alie.aliehermes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Alie on 2019/12/2.
 * 类描述
 * 版本
 */
public class Request implements Parcelable {
    protected Request(Parcel in) {
    }

    public static final Creator<Request> CREATOR = new Creator<Request>() {
        @Override
        public Request createFromParcel(Parcel in) {
            return new Request(in);
        }

        @Override
        public Request[] newArray(int size) {
            return new Request[size];
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
