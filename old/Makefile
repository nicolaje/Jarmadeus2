CC = arm-linux-gcc
CFLAGS = -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux
LFLAGS = -shared -fpic
JAVAC = javac
JAVAH = javah -jni -classpath .

OBJECTS = ji2c.o jfpga.o
TARGET  = PseudoFileTalker.class jarmadeus_FPGATalker.h FPGATalker.class libjfpga.so  jarmadeus_I2CTalker.h I2CTalker.class libji2c.so 


default: $(TARGET)

libji2c.so: ji2c.o 
	$(CC) $^ -o $@ $(LFLAGS)

libjfpga.so : jfpga.o
	$(CC) $^ -o $@ $(LFLAGS)

%.o: %.c
	$(CC) $< -c $(CFLAGS)

%.class: %.java
	$(JAVAC) $<
	
jarmadeus_I2CTalker.h: I2CTalker.class
	$(JAVAH) $<

jarmadeus_FPGATalker.h: FPGATalker.class
	$(JAVAH) $<

clean:
	rm -f $(OBJECTS) $(TARGET)
