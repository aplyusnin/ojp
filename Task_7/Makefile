all: cpuinfo.bc Main.class

cpuinfo.bc: cpuinfo.c
	clang -g -O1 -c -emit-llvm -I$$GRAALVM_HOME/languages/llvm/include -I$$GRAALVM_HOME/include/linux -target x86_64-unknown-linux-gnu cpuinfo.c -o cpuinfo.bc
	
Main.class: Main.java
	javac Main.java
	
clean:
	rm cpuinfo.bc; rm Main.class
