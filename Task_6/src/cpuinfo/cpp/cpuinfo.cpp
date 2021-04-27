//#include "ru_nsu_fit_ojp_plyusnin_task_6_MyJNIClass.h"
#include <jni.h>
#include <fstream>
#include <string>
#include <algorithm>

JNIEXPORT jobject JNICALL Java_ru_nsu_fit_ojp_plyusnin_task_16_MyJNIClass_getCpuInfo
    (JNIEnv *env, jclass thisObj){
    std::ifstream in;
    in.open("/proc/cpuinfo", std::ios::in);
	if (!in.is_open()) return nullptr;
	
	_jclass *cls = env->FindClass("java/lang/HashMap");
	if (cls == nullptr) return nullptr;
	_jmethodID *init = env->GetMethodID(env, cls, "<init>", "()V");
	if (init == nullptr) return nullptr;
	
	_jobject* cur = env->newObject(cls, init);
	if (cur == nullptr) return nullptr;
	_jmethodID* put = env->GetMethodID(cls, "put", "(Ljava/lang/Object;Ljava/lang/String)Ljava/lang/String");
	std::string line;
	while (getline(in, line)){
		int p;
		for (p = 0; line[p] != ':'; p++);
		std::string key = line.substr(0, p);
		std::string val = line.substr(p + 1, (int)line.size() - p - 1);
		_jstring* jkey = env->NewStringUTF(key.c_str());
		_jstring* jval = env->NewStringUTF(val.c_str());
		if (jkey == nullptr || jval == nullptr) continue;
		env->CallObjectMethod(cur, put, jkey, jval);
	}
	
	return env->NewGlobalRef(cur);
}