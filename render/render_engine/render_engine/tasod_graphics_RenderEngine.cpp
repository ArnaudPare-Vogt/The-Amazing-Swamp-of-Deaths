#include "tasod_graphics_RenderEngine.h"
#include <iostream>

JNIEXPORT void JNICALL Java_tasod_graphics_RenderEngine_method(JNIEnv *, jobject)
{
	std::cout << "hello from c++" << std::endl;
}
