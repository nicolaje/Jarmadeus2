#define PLATFORM "APF27"
#define FPGA_ADDRESS 0xD6000000
#define BYTE_ACCESS (1)
#define WORD_ACCESS (2)
#define LONG_ACCESS (4)

/* file management */
#include <sys/stat.h>
#include <fcntl.h>

/* as name said */
#include <signal.h>

/* sleep, write(), read() */
#include <unistd.h>
/* converting string */
#include <string.h>

/* memory management */
#include <sys/mman.h>

/* JNI */
#include <jni.h>
#include "jarmadeus_FPGATalker.h"


//////////////////////// 16 BITS
JNIEXPORT jint JNICALL Java_jarmadeus_FPGATalker_fpgawriteU16(JNIEnv *env, jobject obj, jchar  reg, jchar val, jboolean debug)
{
	unsigned short address;
	unsigned int value;
	jint coderet=0;
	int ffpga, accesstype = LONG_ACCESS;
	void* ptr_fpga;
	ffpga = open("/dev/mem", O_RDWR|O_DSYNC|O_RSYNC);
	//ffpga = open("/dev/mem", O_RDWR|O_SYNC);
	if (ffpga < 0) {
		printf("can't open file /dev/mem\n");
		coderet=-1;
	}
	ptr_fpga = mmap(0, 8192, PROT_READ|PROT_WRITE, MAP_SHARED, ffpga, FPGA_ADDRESS);
	if (ptr_fpga == MAP_FAILED) {
		printf("mmap failed\n");
		coderet=-2;
	}
	address = (unsigned int)reg;  
	accesstype = WORD_ACCESS;
	if (address%2 != 0) { 
		printf("Can't do word access at odd address (%d). Use a 16bits aligned one.\n", address);
		coderet=-3;
	}
	value = val; 
	*(unsigned short*)(ptr_fpga+(address)) = (unsigned short)value;
	if (debug)
		printf("FPGA Write 0x%04x at 0x%04x\n", (unsigned short)value, address);
	close(ffpga);
	return coderet;
}

JNIEXPORT jchar JNICALL Java_jarmadeus_FPGATalker_fpgareadU16(JNIEnv *env, jobject obj, jchar  reg, jboolean debug)
{
	unsigned short address;
	jchar value;
	int ffpga, accesstype = LONG_ACCESS;
	void* ptr_fpga;
	ffpga = open("/dev/mem", O_RDWR|O_DSYNC|O_RSYNC);
	//ffpga = open("/dev/mem", O_RDWR|O_SYNC);
	if (ffpga < 0) {
		printf("can't open file /dev/mem\n");
	}
	ptr_fpga = mmap(0, 8192, PROT_READ|PROT_WRITE, MAP_SHARED, ffpga, FPGA_ADDRESS);
	if (ptr_fpga == MAP_FAILED) {
		printf("mmap failed\n");
	}
	address = (unsigned int)reg;   // strtol(, (char **)NULL, 16);
	accesstype = WORD_ACCESS;
	if (address%2 != 0) {
		printf("Can't do word access at odd address (%d). Use a 16bits aligned one.\n", address);
	}
	value = *(unsigned short*)(ptr_fpga+(address));
	if (debug)
		printf("FPGA Read 0x%04x at 0x%04x\n", (unsigned short)value, address);
	close(ffpga);
	return value;
}


JNIEXPORT jint JNICALL Java_jarmadeus_FPGATalker_fpgawriteS16(JNIEnv *env, jobject obj, jchar  reg, jshort val, jboolean debug)
{
	unsigned short address;

	jint coderet=0;
	int ffpga, accesstype = LONG_ACCESS;
	void* ptr_fpga;
	ffpga = open("/dev/mem", O_RDWR|O_DSYNC|O_RSYNC);
	//ffpga = open("/dev/mem", O_RDWR|O_SYNC);
	if (ffpga < 0) {
		printf("can't open file /dev/mem\n");
		coderet=-1;
	}
	ptr_fpga = mmap(0, 8192, PROT_READ|PROT_WRITE, MAP_SHARED, ffpga, FPGA_ADDRESS);
	if (ptr_fpga == MAP_FAILED) {
		printf("mmap failed\n");
		coderet=-2;
	}
	address = (unsigned int)reg;  
	accesstype = WORD_ACCESS;
	if (address%2 != 0) { 
		printf("Can't do word access at odd address (%d). Use a 16bits aligned one.\n", address);
		coderet=-3;
	}
	*(unsigned short*)(ptr_fpga+(address)) = (signed short)val;
	if (debug)
		printf("FPGA Write 0x%04x at 0x%04x\n", (signed short)val, address);
	close(ffpga);
	return coderet;
}

JNIEXPORT jshort JNICALL Java_jarmadeus_FPGATalker_fpgareadS16(JNIEnv *env, jobject obj, jchar  reg, jboolean debug)
{
	unsigned short address;
	jshort value;
	int ffpga, accesstype = LONG_ACCESS;
	void* ptr_fpga;
	ffpga = open("/dev/mem", O_RDWR|O_DSYNC|O_RSYNC);
	if (ffpga < 0) {
		printf("can't open file /dev/mem\n");
	}
	ptr_fpga = mmap(0, 8192, PROT_READ|PROT_WRITE, MAP_SHARED, ffpga, FPGA_ADDRESS);
	if (ptr_fpga == MAP_FAILED) {
		printf("mmap failed\n");
	}
	address = (unsigned int)reg;   // strtol(, (char **)NULL, 16);
	accesstype = WORD_ACCESS;
	if (address%2 != 0) {
		printf("Can't do word access at odd address (%d). Use a 16bits aligned one.\n", address);
	}
	value = *(unsigned short*)(ptr_fpga+(address));
	if (debug) 
		printf("FPGA Read 0x%04x at 0x%04x\n", (signed short)value, address);
	close(ffpga);
	return value;
}


