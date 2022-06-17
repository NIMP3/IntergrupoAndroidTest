#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_co_dev_yovany_intergrupoandroidtest_common_SecurityUtility_getKey(JNIEnv *env, jobject instance) {
    std::string key = "KEY_INTERGRUPO_2022";
    return (*env).NewStringUTF(key.c_str());
}