#include "graalvm/llvm/polyglot.h"
#include "stdio.h"
#include "string.h"
#include "stdlib.h"

void* cpuInfo() {
	FILE* in = fopen("/proc/cpuinfo", "r");
	if (in == NULL) return NULL;

	void *hashMapType = polyglot_java_type("java.util.HashMap");
	if (hashMapType == NULL) return NULL;
	void *hmap = polyglot_new_instance(hashMapType);
	if (hmap == NULL) return NULL;
	int keysize = 1024;
	char *key = calloc(keysize, sizeof(char));
	int valsize = 1024;
	char *val = calloc(valsize, sizeof(char));
	int ch;
	int p = 0, q = 0, t = 0;
	while ((ch = fgetc(in)) != -1) {
		if (ch == '\n') {
			void* pkey = polyglot_from_string(key, "US-ASCII");
			void* pval = polyglot_from_string(val, "US-ASCII");
			if (pkey == NULL || pval == NULL) {
				continue;
			}
			polyglot_invoke(hmap, "put", pkey, pval);
			memset(key, 0, p * sizeof(char));
			memset(val, 0, q * sizeof(char));
			t = q = p = 0;
		}
		else if (ch == ':') {
			t = 1;
		}
		else if (t) {
			val[q++] = (char)ch;
			if (q == valsize) {
				valsize *= 2;
				val = realloc(val, valsize);
			}
		}
		else {
			key[p++] = (char)ch;
			if (p == keysize) {
				keysize *= 2;
				key = realloc(key, keysize);
			}
		}
	}
	free(val);
	free(key);
	fclose(in);
	return hmap;
}