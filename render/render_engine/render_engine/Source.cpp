
#include <iostream>
#include <GL\glew.h>
#include <GL\freeglut.h>
#include <glm\glm.hpp>

#include "engine.h"

extern "C" {
	__declspec(dllexport) bool Init() {
		return render_engine::Init();
	}
}
