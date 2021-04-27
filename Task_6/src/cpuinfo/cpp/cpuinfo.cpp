#include "ru_nsu_fit_ojp_plyusnin_task_6_MyJNIClass.h"

#include <fstream>
#include <string>
#include <algorithm>

JNIEXPORT jobject JNICALL Java_ru_nsu_fit_ojp_plyusnin_task_16_MyJNIClass_getCpuInfo
    (JNIEnv *env, jclass thisObj){
    std::ifstream in;
    in.open("/proc/cpuinfo", ios::in);
	if (!in.is_open()) return nullptr;
	
	jclass cls = env->FindClass(env, "java/lang/HashMap");
	if (cls == nullptr) return nullptr;
	jmethodId init = env->GetMethodID(env, cls, "<init>", "()V");
	if (init == nullptr) return nullptr;
	
	jobject cur = env->newObject(env, cls, init);
	if (cur == nullptr) return nullptr;
	jmethodId put = env->GetMethodID(env, cls, "put", "(Ljava/lang/Object;Ljava/lang/String)Ljava/lang/String")
	str::string line;
	while (getline(in, line)){
		int p;
		for (p = 0; line[p] != ':'; p++);
		std::string key = line.substr(0, p);
		std::string val = line.substr(p + 1, (int)line.size() - p - 1);
		jstring jkey = env->NewStringUTF(env, key.c_str());
		jstring jval = env->NewStringUTF(env, val.c_str());
		if (jkey == nullptr || jval == nullptr) continue;
		env->CallObjectMethod(env, cur, put, jkey, jval);
	}
	
	return env->NewGlobalRef(env, cur);
}