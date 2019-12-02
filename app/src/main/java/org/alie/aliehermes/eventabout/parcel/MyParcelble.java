package org.alie.aliehermes.eventabout.parcel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Alie on 2019/12/2.
 * 类描述
 * 版本
 */
public class MyParcelble implements Parcelable {

    private String name;
    private int age;


    protected MyParcelble(Parcel in) {
        /**
         * 2 从native对象中读出
         *  (write顺序是什么样的，read就是什么顺序)
         */
        name = in.readString();
        age = in.readInt();
    }


    /**
     * 反序列化的内部类
     */
    public static final Creator<MyParcelble> CREATOR = new Creator<MyParcelble>() {

        /**
         * 反序列化方法
         * @param in
         * @return
         */
        @Override
        public MyParcelble createFromParcel(Parcel in) {
            return new MyParcelble(in);
        }

        @Override
        public MyParcelble[] newArray(int size) {
            return new MyParcelble[size];
        }
    };


    /**
     * 提供内容描述的方法
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 序列化的方法
     * @param parcel
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        /**
         * 1 进行序列化，这里是将java对象序列到native中
         */
        parcel.writeString(name);
        parcel.writeInt(age);
    }
}
