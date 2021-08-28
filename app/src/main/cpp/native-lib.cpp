#include <jni.h>

//
// Created by hband on 2021. 08. 28..
//

extern "C"
JNIEXPORT jstring JNICALL
Java_hu_ahomolya_androidbase_provider_SecretsProviderImpl_getClientId(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF("69bfdce9-2c9f-4a12-aa7b-4fe15e1228dc");
}