#include <stdio.h>
#include <fcntl.h>
#include <errno.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <linux/i2c.h> 
#include <linux/i2c-dev.h>
#include <sys/ioctl.h>
#include <jni.h>
#include "jarmadeus_I2CTalker.h"

JNIEXPORT jchar JNICALL Java_jarmadeus_I2CTalker_i2cread(JNIEnv *env, jobject obj, jchar addr, jchar  reg, jboolean debug) {
    int fd = open("/dev/i2c-0",O_RDWR);
    if(fd < 0){
	printf("can't open /dev/i2c-0");
    }
    unsigned char* buf;
    // create an I2C write message (only one byte: the address)
    struct i2c_msg msg = {addr, 0, 1, buf};
    // create an I2C IOCTL request
    struct i2c_rdwr_ioctl_data rdwr = { &msg, 1 };
    buf[0] = reg; // select reg to read
    // write the desired register address
    if ( ioctl( fd, I2C_RDWR, &rdwr ) < 0 ){
	printf("I2C Write error\n");
    }
    msg.flags = I2C_M_RD; // read
    // read the result and write it in buf[0]
    if ( ioctl( fd, I2C_RDWR, &rdwr ) < 0 ){
	printf("I2C Read error\n");
    }
    close(fd);
    if (debug)
        printf("I2C read \t : %02x \n",buf[0]);
    jchar value=buf[0];
    return value;
}

JNIEXPORT jint JNICALL Java_jarmadeus_I2CTalker_i2cwrite(JNIEnv *env, jobject obj, jchar addr, jchar  reg, jchar data, jboolean debug) {
	jint coderet=0;
	// ouverture du descripteur de fichier pour le bus i2c
    int fd = open("/dev/i2c-0",O_RDWR);
    if(fd < 0){
	    printf("can't open /dev/i2c-0");
		coderet=-1;
	}
	unsigned char buf[2] = {reg,data}; // initialise a data buffer with 
	// address and data
	// create an I2C write message
	struct i2c_msg msg = {addr, 0, sizeof(buf), buf };
	// create a I2C IOCTL request
	struct i2c_rdwr_ioctl_data rdwr = { &msg, 1 };
	if ( ioctl( fd, I2C_RDWR, &rdwr ) < 0 ){
		printf("I2C write error\n");
		coderet=-2;
	}
	close(fd);
	if (debug)
        printf("I2C write \t : %02x \n",buf[1]);
	return coderet;
}

