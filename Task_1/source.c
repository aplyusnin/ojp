#define _CRT_SECURE_NO_WARNINGS
#include "stdio.h"
#include "stdlib.h"
#include "string.h"

typedef unsigned char uchar;
typedef unsigned short ushort;
typedef unsigned int uint;
typedef unsigned long long ulong;

uchar buffer[12];

typedef struct cp_info_s {
	uchar tag;
	uchar* data;
} cp_info;

ushort switchOrder2(ushort val){
	return (val & 255) << 8 | (val >> 8);
}

void swap(uchar* a, uchar* b){
	if (a != b)
	{
		*a ^= *b;
		*b ^= *a;
		*a ^= *b;
	}
}

cp_info constantPool[65536];

void writeUtf8(int id){
	printf("%s", constantPool[id].data + 2);
}

void writeInteger(int id){
	printf("%d", *((int*)constantPool[id].data));
}

void writeFloat(int id){
	printf("%f", *((float*)constantPool[id].data));
}

void writeLong(int id) {
	printf("%lld", *((long long*)constantPool[id].data));
}

void writeDouble(int id) {
	printf("%lf", *((double*)constantPool[id].data));
}

void write2Addr(int id);

void writeAddr(int id){
	if (constantPool[id].tag == 1)
		writeUtf8(id);
	else if ((7 <= constantPool[id].tag && constantPool[id].tag <= 8) 
		|| constantPool[id].tag >= 19 || constantPool[id].tag == 16)
		writeAddr(*((ushort*)constantPool[id].data));
	else if (9 <= constantPool[id].tag && constantPool[id].tag <= 12)
		write2Addr(id);
}

void write2Addr(int id){
	writeAddr(*((ushort*)constantPool[id].data));
	printf(".");
	writeAddr(*((ushort*)(constantPool[id].data + 2)));
}

void writeHandle(int id){
	switch (constantPool[id].data[0])	{
	case 1:
		printf("REF_getField ");
	case 2:
		printf("REF_getStatic ");
	case 3:
		printf("REF_putField ");
	case 4:
		printf("REF_putStatic ");
	case 5:
		printf("REF_invokeVirtual ");
	case 6:
		printf("REF_invokeStatic ");
	case 7:
		printf("REF_invokeSpecial ");
	case 8:
		printf("REF_newInvokeSpecial ");
	case 9:
		printf("REF_invokeInterface ");
	}
	write2Addr(*((ushort*)(constantPool[id].data + 1)));
}

int main(int argc, char** argv){
	if (argc < 2){
		printf("No file to read");
		return 0;
	}
	FILE * in = fopen(argv[1], "rb");

	size_t read = fread(buffer, sizeof(uchar), 10, in);
	ushort minor = switchOrder2(*((ushort*)(buffer + 4)));
	ushort major = switchOrder2(*((ushort*)(buffer + 6)));
	printf("minor version: %hu\n", minor);
	printf("major version: %hu\n", major);
	
	ushort constantPoolSize = switchOrder2(*((ushort*)(buffer + 8)));
	
	for (int i = 1; i < constantPoolSize; i++){
		fread(&constantPool[i].tag, sizeof(constantPool[i].tag), 1, in);
		switch (constantPool[i].tag){
		case 1: //utf-8 string
			{
				ushort len;
				fread(&len, sizeof(ushort), 1, in);
				len = switchOrder2(len);
				constantPool[i].data = calloc(len + 3, sizeof(uchar));
				memcpy(constantPool[i].data, &len, sizeof(len));
				fread(constantPool[i].data + sizeof(len), sizeof(uchar), len, in);
			}
			break;
		case 3: //integer
			{
				constantPool[i].data = malloc(sizeof(uchar) * 4);
				fread(constantPool[i].data, sizeof(uchar), 4, in);
				swap(constantPool[i].data, constantPool[i].data + 3);
				swap(constantPool[i].data + 1, constantPool[i].data + 2);
			}
			break;
		case 4://float
			{
				constantPool[i].data = malloc(sizeof(uchar) * 4);
				fread(constantPool[i].data, sizeof(uchar), 4, in);
				swap(constantPool[i].data, constantPool[i].data + 3);
				swap(constantPool[i].data + 1, constantPool[i].data + 2);
			}
			break;
		case 5://long
			{
				constantPool[i].data = malloc(sizeof(uchar) * 8);
				fread(constantPool[i].data, sizeof(uchar), 8, in);
				for (int j = 0; j < 4; j++)
				{
					swap(constantPool[i].data + j, constantPool[i].data + 7 - i);
				}
			}
			break;
		case 6: // double
			{
				constantPool[i].data = malloc(sizeof(uchar) * 8);
				fread(constantPool[i].data, sizeof(uchar), 8, in);
				for (int j = 0; j < 4; j++)
				{
					swap(constantPool[i].data + j, constantPool[i].data + 7 - i);
				}
			}
			break;
		case 7: //class
			{
				constantPool[i].data = malloc(sizeof(uchar) * 2);
				fread(constantPool[i].data, sizeof(uchar), 2, in);
				swap(constantPool[i].data, constantPool[i].data + 1);
			}
			break;
		case 8: //string 
			{
				constantPool[i].data = malloc(sizeof(uchar) * 2);
				fread(constantPool[i].data, sizeof(uchar), 2, in);
				swap(constantPool[i].data, constantPool[i].data + 1);
			}
			break;
		case 9: // fieldRef
			{
				constantPool[i].data = malloc(sizeof(uchar) * 4);
				fread(constantPool[i].data, sizeof(uchar), 4, in);
				swap(constantPool[i].data, constantPool[i].data + 1);
				swap(constantPool[i].data + 2, constantPool[i].data + 3);
			}
			break;
		case 10: //methodRef
			{
				constantPool[i].data = malloc(sizeof(uchar) * 4);
				fread(constantPool[i].data, sizeof(uchar), 4, in);
				swap(constantPool[i].data, constantPool[i].data + 1);
				swap(constantPool[i].data + 2, constantPool[i].data + 3);
			}
			break;
		case 11: //InterfaceMethodRef
			{
				constantPool[i].data = malloc(sizeof(uchar) * 4);
				fread(constantPool[i].data, sizeof(uchar), 4, in);
				swap(constantPool[i].data, constantPool[i].data + 1);
				swap(constantPool[i].data + 2, constantPool[i].data + 3);
			}
			break;
		case 12: //nameAndType
			{
				constantPool[i].data = malloc(sizeof(uchar) * 4);
				fread(constantPool[i].data, sizeof(uchar), 4, in);
				swap(constantPool[i].data, constantPool[i].data + 1);
				swap(constantPool[i].data + 2, constantPool[i].data + 3);
			}
			break;
		case 15: //methodHandle
			{
				constantPool[i].data = malloc(sizeof(uchar) * 3);
				fread(constantPool[i].data, sizeof(uchar), 3, in);
				swap(constantPool[i].data + 1, constantPool[i].data + 2);
			}
			break;
		case 16: //methodType
			{
				constantPool[i].data = malloc(sizeof(uchar) * 2);
				fread(constantPool[i].data, sizeof(uchar), 2, in);
				swap(constantPool[i].data + 1, constantPool[i].data);
			}
			break;
		case 17: //Dynamic_info
			{
				constantPool[i].data = malloc(sizeof(uchar) * 4);
				fread(constantPool[i].data, sizeof(uchar), 4, in);
				swap(constantPool[i].data + 1, constantPool[i].data);
				swap(constantPool[i].data + 2, constantPool[i].data + 3);
			}
			break;
		case 18: //InvokeDynamic_info
			{
				constantPool[i].data = malloc(sizeof(uchar) * 4);
				fread(constantPool[i].data, sizeof(uchar), 4, in);
				swap(constantPool[i].data + 1, constantPool[i].data);
				swap(constantPool[i].data + 2, constantPool[i].data + 3);
			}
			break;
		case 19: //ModuleInfo
			{
				constantPool[i].data = malloc(sizeof(uchar) * 2);
				fread(constantPool[i].data, sizeof(uchar), 2, in);
				swap(constantPool[i].data + 1, constantPool[i].data);
			}
			break;
		case 20: //Package_info
			{
				constantPool[i].data = malloc(sizeof(uchar) * 2);
				fread(constantPool[i].data, sizeof(uchar), 2, in);
				swap(constantPool[i].data + 1, constantPool[i].data);
			}
			break;
		default:
			printf("HUI %d %d\n", i, constantPool[i].tag);
			break;
		}
	}

	for (int i = 1; i < constantPoolSize; i++){
		printf("#%d ", i);
		switch (constantPool[i].tag){
		case 1: //utf-8 string
			{
				printf("Utf8 ");
				writeUtf8(i);
			}
			break;
		case 3: //integer
			{
				printf("Integer ");
				writeInteger(i);
			}
			break;
		case 4://float
			{
				printf("Float ");
				writeFloat(i);
			}
		break;
		case 5://long
			{
				printf("Long ");
				writeLong(i);
			}
		break;
		case 6: // double
			{
				printf("Double ");
				writeDouble(i);
			}
		break;
		case 7: //class
			{
				printf("Class ");
				printf("#%d ", (int)*((ushort*)constantPool[i].data));
				writeAddr(i);
			}
		break;
		case 8: //string 
			{
				printf("String ");
				printf("#%d ", (int)*((ushort*)constantPool[i].data));
				writeAddr(i);
			}
		break;
		case 9: // fieldRef
			{
				printf("FieldRef ");
				printf("#%d.#%d ", (int)*((ushort*)constantPool[i].data), (int)*((ushort*)(constantPool[i].data + 2)));
				write2Addr(i);
			}
		break;
		case 10: //methodRef
			{
				printf("MethodRef ");
				printf("#%d.#%d ", (int)*((ushort*)constantPool[i].data), (int)*((ushort*)(constantPool[i].data + 2)));
				write2Addr(i);
			}
		break;
		case 11: //InterfaceMethodRef
			{
				printf("InterfaceMethodRef ");
				printf("#%d.#%d ", (int)*((ushort*)constantPool[i].data), (int)*((ushort*)(constantPool[i].data + 2)));
				write2Addr(i);
			}
		break;
		case 12: //nameAndType
			{
				printf("NameAndType ");
				printf("#%d.#%d ", (int)*((ushort*)constantPool[i].data), (int)*((ushort*)(constantPool[i].data + 2)));
				write2Addr(i);
			}
		break;
		case 15: //methodHandle
			{
				printf("MethodHandle ");
				writeHandle(i);
				constantPool[i].data = malloc(sizeof(uchar) * 3);
				fread(constantPool[i].data, sizeof(uchar), 3, in);
				swap(constantPool[i].data + 1, constantPool[i].data + 2);
			}
		break;
		case 16: //methodType
			{
				printf("MethodType ");
				writeAddr(i);
			}
		break;
		case 17: //DynamicInfo
			{
				printf("DynamicInfo ");
				printf("#%d.#%d ", (int)*((ushort*)constantPool[i].data), (int)*((ushort*)(constantPool[i].data + 2)));
				writeAddr((int)*((ushort*)(constantPool[i].data + 2)));
			}
			break;
		case 18: //InvokeDynamicInfo
			{
				printf("InvokeDynamicInfo ");
				printf("#%d.#%d ", (int)*((ushort*)constantPool[i].data), (int)*((ushort*)(constantPool[i].data + 2)));
				writeAddr((int)*((ushort*)(constantPool[i].data + 2)));
			}
			break;
		case 19: //ModuleInfo
			{
				printf("ModuleInfo ");
				writeAddr(i);
			}
			break;
		case 20: //Package_info
			{
				printf("PackageInfo ");
				writeAddr(i);
			}
			break;
		default:
			break;
		}
		printf("\n");
	}
	for (int i = 1; i < constantPoolSize; i++){
		free(constantPool[i].data);
	}
	return 0;
}