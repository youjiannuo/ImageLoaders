LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := yjn_ndk
LOCAL_SRC_FILES := com_yjn_text_JniClient.c

include $(BUILD_SHARED_LIBRARY)
