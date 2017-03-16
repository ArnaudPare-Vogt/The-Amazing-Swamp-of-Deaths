#pragma once

#include <stdio.h>
#include <stdlib.h>
#include <fstream>
#include <vector>
#include <map>
#include <string>
#include <iostream>
#include <GL\glew.h>
#include <GL\GL.h>
#include <GL\freeglut.h>
#include <GLFW\glfw3.h>
#include <glm\glm.hpp>
#include <glm\gtc\matrix_transform.hpp>
#include <glm\gtx\transform.hpp>


namespace ninecore {
	namespace shaders{
		class ShaderManager
		{
		private:
			static ShaderManager* _instance ;
			GLuint ReadShaderFile(const char*, unsigned int &, std::string&);
		protected:
			ShaderManager();
		public:
			std::map<std::string, GLuint> shaderTable;
			GLuint currentShaderProgramID;
			int CreateShaderProgram(GLuint&, const char* vertex_file_path, const char * fragment_file_path);
			void RegisterShaderProgram(std::string, GLuint);
			static ShaderManager* instance();
			void dispose();
			~ShaderManager();
		};

	};
};


