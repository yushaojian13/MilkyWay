package com.ysj.milkyway.jni;

/**
 * Created by Yu Shaojian on 2015 12 15.
 */
public class HelloJni {
    /* this is used to load the 'hello-jni' library on application
     * startup. The library has already been unpacked into
     * /data/data/com.example.hellojni/lib/libhello-jni.so at
     * installation time by the package manager.
     */
    static {
        System.loadLibrary("NativeStudy");
    }


    /* A native method that is implemented by the
     * 'hello-jni' native library, which is packaged
     * with this application.
     */
    public native String stringFromJNI();
    public static native String stringFromCppJNI();
}
