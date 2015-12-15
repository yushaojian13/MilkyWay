LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
#LOCAL_MODULE表示生成的库的名字，前面的lib和后缀名不用写
LOCAL_MODULE    := NativeStudy
LOCAL_SRC_FILES := CppJni.cpp

include $(BUILD_SHARED_LIBRARY)
