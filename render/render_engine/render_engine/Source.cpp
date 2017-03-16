#define _CRT_SECURE_NO_DEPRECATE

#include <stdio.h>
#include <stdlib.h>
#include <string>
#include <fstream>
#include <vector>
#include <Windows.h>
#include <ctime>
#include <iostream>
#include <GL\glew.h>
#include <GL\GL.h>
#include <GL\freeglut.h>
#include <GLFW\glfw3.h>
#include <glm\glm.hpp>
#include <glm\gtc\matrix_transform.hpp>
#include <glm\gtx\transform.hpp>

#include "GameObject.h"
#include "Meshes.h"
#include "ObjLoader.h"

#define WINDOW_WT 1024
#define WINDOW_HT 768

using namespace glm;


GLuint loadBMP_custom(const char* imagepath) {
	unsigned char header[54];
	unsigned int dataPos;
	unsigned int width, height;
	unsigned int imageSize;
	unsigned char * data;

	FILE * file = std::fopen(imagepath, "rb");
	if (!file) {
		printf("image at %s could not be opened\n", imagepath);
	}

	if (std::fread(header, 1, 54, file) != 54) {
		printf("image at %s is not a valid BMP file\n", imagepath);
		return false;
	}

	if (header[0] != 'B' && header[1] != 'M') {
		printf("image at %s is not a valid BMP file\n", imagepath);
		return false;
	}

	dataPos = *(int*)&(header[0x0a]);
	imageSize = *(int*)&(header[0x22]);
	width = *(int*)&(header[0x12]);
	height = *(int*)&(header[0x16]);

	if (imageSize == 0)
		imageSize = width * height * 3;
	if (dataPos == 0)
		dataPos = 54;

	data = new unsigned char[imageSize];
	fread(data, 1, imageSize, file);
	fclose(file);


	GLuint textureID;
	glGenTextures(1, &textureID);
	glBindTexture(GL_TEXTURE_2D, textureID);
	glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_BGR, GL_UNSIGNED_BYTE, data);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
	glGenerateMipmap(GL_TEXTURE_2D);

	return textureID;
}

GLuint LoadShaders(const char* vertex_file_path, const char * fragment_file_path) {
	GLuint VertexShaderID = glCreateShader(GL_VERTEX_SHADER);
	GLuint FragmentShaderID = glCreateShader(GL_FRAGMENT_SHADER);
	std::string VertexShaderCode;
	std::string FragmentShaderCode;
	GLint result = GL_FALSE;
	int infologlength;

#pragma region vertex
	std::ifstream VertexShaderStream(vertex_file_path, std::ios::in);
	if (VertexShaderStream.is_open()) {
		std::string line = "";
		while (getline(VertexShaderStream, line)) {
			VertexShaderCode += "\n" + line;
		}
		VertexShaderStream.close();
	}
	else {
		printf("[load vert shader] impossible to open %s.", vertex_file_path);
		getchar();
		return 0;
	}
#pragma endregion vertex

#pragma region fragment
	std::ifstream FragmentShaderStream(fragment_file_path, std::ios::in);
	if (FragmentShaderStream.is_open()) {
		std::string line = "";
		while (getline(FragmentShaderStream, line)) {
			FragmentShaderCode += "\n" + line;
		}
		FragmentShaderStream.close();
	}
	else {
		printf("[load frag shader] impossible to open %s.", vertex_file_path);
		getchar();
		return 0;
	}
#pragma endregion fragment

#pragma region compile
	//vertex
	printf("compiling shader: %s\n", vertex_file_path);
	char const * VertexSourcePointer = VertexShaderCode.c_str();
	glShaderSource(VertexShaderID, 1, &VertexSourcePointer, NULL);
	glCompileShader(VertexShaderID);
	glGetShaderiv(VertexShaderID, GL_COMPILE_STATUS, &result);
	glGetShaderiv(infologlength, GL_INFO_LOG_LENGTH, &infologlength);
	if (infologlength > 0) {
		std::vector<char> VertexShaderErrorMessage(infologlength + 1);
		glGetShaderInfoLog(VertexShaderID, infologlength, NULL, &VertexShaderErrorMessage[0]);
		printf("[ERROR] - %s\n", &VertexShaderErrorMessage[0]);
	}

	//frag
	printf("compiling shader: %s\n", fragment_file_path);
	char const *FragmentSourcePointer = FragmentShaderCode.c_str();
	glShaderSource(FragmentShaderID, 1, &FragmentSourcePointer, NULL);
	glCompileShader(FragmentShaderID);
	glGetShaderiv(FragmentShaderID, GL_COMPILE_STATUS, &result);
	glGetShaderiv(infologlength, GL_INFO_LOG_LENGTH, &infologlength);
	if (infologlength > 0) {
		std::vector<char> FragmentShaderErrorMessage(infologlength + 1);
		glGetShaderInfoLog(FragmentShaderID, infologlength, NULL, &FragmentShaderErrorMessage[0]);
		printf("[ERROR] - %s\n", &FragmentShaderErrorMessage[0]);
	}
#pragma endregion compile

#pragma region linking
	printf("linking program\n");
	GLuint ProgramID = glCreateProgram();
	glAttachShader(ProgramID, VertexShaderID);
	glAttachShader(ProgramID, FragmentShaderID);
	glLinkProgram(ProgramID);

	glGetProgramiv(ProgramID, GL_LINK_STATUS, &result);
	glGetProgramiv(ProgramID, GL_INFO_LOG_LENGTH, &infologlength);
	if (infologlength > 0) {
		std::vector<char> ProgramErrorMessage(infologlength + 1);
		glGetProgramInfoLog(ProgramID, infologlength, NULL, &ProgramErrorMessage[0]);
		printf("[ERROR] - %s\n", &ProgramErrorMessage[0]);
	}

	glDetachShader(ProgramID, VertexShaderID);
	glDetachShader(ProgramID, FragmentShaderID);
	glDeleteShader(VertexShaderID);
	glDeleteShader(FragmentShaderID);
#pragma endregion linking

	return ProgramID;
}

int initGLFW() {
	if (!glfwInit()) {
		fprintf(stderr, "Failed to initialize GLFW");
		return -1;
	}

	glfwWindowHint(GLFW_SAMPLES, 4);
	glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
	glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
	glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
	glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);//GLFW_OPENGL_CORE_PROFILE);
	return 0;
}

int initWindow(GLFWwindow*& window) {
	window = glfwCreateWindow(WINDOW_WT, WINDOW_HT, "Ogl", NULL, NULL);

	if (window == NULL) {
		fprintf(stderr, "failed to open glfw window");
		return -1;
	}
	glfwMakeContextCurrent(window);
	glewExperimental = true;
	if (glewInit() != GLEW_OK) {
		fprintf(stderr, "failed to initialize glew");
		return -2;
	}

	glfwSetInputMode(window, GLFW_STICKY_KEYS, GL_TRUE);
	glEnable(GL_DEPTH_TEST);
	glDepthFunc(GL_LESS);
	glEnable(GL_CULL_FACE);
	glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
}

void tickScene(std::vector<GameObjects::GameObject*> & sceneObjects) {
	for (GameObjects::GameObject *object : sceneObjects) {
		object->Tick();
	}
}

void drawScene(std::vector<GameObjects::GameObject*> & sceneObjects, GLuint & programID, GameObjects::Light & sun, GameObjects::Camera & camera) {
	glm::mat4 Projection = glm::perspective(glm::radians(camera.fov), (float)4 / (float)3, 0.1f, 100.0f);
	glm::mat4 View = glm::lookAt(
		camera.position,
		camera.position + camera.direction(),
		glm::vec3(0, 1, 0)
	);
	
	for (GameObjects::GameObject *object : sceneObjects) {
		
		if (GameObjects::MeshObject * mobject = dynamic_cast<GameObjects::MeshObject *> (object)) {
			mobject->mesh.PreDraw();
			try {
				GLuint M_MatrixHandle = glGetUniformLocation(programID, "ModelMatrix");
				GLuint V_MatrixHandle = glGetUniformLocation(programID, "ViewMatrix");
				GLuint P_MatrixHandle = glGetUniformLocation(programID, "ProjectionMatrix");
				GLuint MVP_MatrixHandle = glGetUniformLocation(programID, "MVP");
				GLuint MV3x3_MatrixHandle = glGetUniformLocation(programID, "MV3x3");
				GLuint LightColor_Handle = glGetUniformLocation(programID, "LightColor");
				GLuint LightPosition_Handle = glGetUniformLocation(programID, "LightPosition_worldspace");
				GLuint textureSamplerHandle = glGetUniformLocation(programID, "textureSampler");
				GLuint normalMapTextureSamplerHandle = glGetUniformLocation(programID, "normalMapSampler");
				
				glm::mat4 Model = object->MakeModelMatrix();
				glm::mat4 MVP = Projection * View * Model;
				glm::mat3 MV3x3 = glm::mat3(View * Model);
				glUniform1i(textureSamplerHandle, 0);
				glUniform1i(normalMapTextureSamplerHandle, 1);
				glUniformMatrix4fv(MVP_MatrixHandle, 1, GL_FALSE, &MVP[0][0]);
				glUniformMatrix3fv(MV3x3_MatrixHandle, 1, GL_FALSE, &MV3x3[0][0]);
				glUniformMatrix4fv(M_MatrixHandle, 1, GL_FALSE, &Model[0][0]);
				glUniformMatrix4fv(V_MatrixHandle, 1, GL_FALSE, &View[0][0]);
				glUniformMatrix4fv(P_MatrixHandle, 1, GL_FALSE, &Projection[0][0]);

				glUniform3fv(LightColor_Handle, 1, &sun.color[0]);
				glUniform3fv(LightPosition_Handle, 1, &sun.position[0]);
				mobject->Draw();
				
			}
			catch (std::exception ex) {
				std::cout << ex.what() <<std::endl;
			}
			
		}
		
	}
	
	
}



int main() {
	if (initGLFW() < 0) {
		return -1;
	}

	#pragma region window init
	GLFWwindow* window;
	if (initWindow(window) < 0) {
		std::cin.get();
		return -2;
	}
	#pragma endregion
	
	Meshes::Mesh m2 = OBJ::ObjLoader::Load("./Models/tile.obj");
	
	std::vector<GameObjects::GameObject*> sceneObjects;
	GLuint programID = LoadShaders("./Vertex.glsl", "./Fragment.glsl");
	GLuint Texture = loadBMP_custom("./Textures/crate.bmp");
	GLuint NormalMap = loadBMP_custom("./Textures/crate_normal.bmp");

	m2.textureID = Texture;
	m2.normalMapTextureID = NormalMap;

	glm::mat4 Projection = glm::perspective(glm::radians(45.0f), (float)4 / (float)3, 0.1f, 100.0f);
	glm::mat4 View = glm::lookAt(
		glm::vec3(0, 1, 0),
		glm::vec3(0, 0, 0),
		glm::vec3(0, 1, 0)
	);

	#pragma region objects
	GameObjects::MeshObject tile(
		glm::vec3(0, 0, -5),
		glm::vec4(0, 1, 0, 0.75f),
		glm::vec3(1, 1, 1),
		m2
	);
	//tile.deltaRotation = glm::vec4(0,0,1,0.005f);
	sceneObjects.push_back(&tile);

	GameObjects::Light sun(
		glm::vec3(-10,10,0),
		glm::vec3(1,1,1)
		);
	sceneObjects.push_back(&sun);

	
	#pragma endregion


	long ticks = 0;
	float horizontalAngle = 3.14f;
	float verticalAngle = 0.0f;
	float initialFOV = 45.0f;
	float speed = 3.0f;
	float mouseSpeed = 0.005f;
	//camera
	GameObjects::Camera camera(
		glm::vec3(0, 1, 0),
		glm::vec4(0, 0, -1, 0)
	);
	sceneObjects.push_back(&camera);
	camera.fov = initialFOV;

	GameObjects::Camera suncam(
		glm::vec3(0, 1, 0),
		glm::vec4(0, 0, -1, 0)
	);
	suncam.fov = initialFOV;
	

	double lastFrame = glfwGetTime(), currentFrame;
	float deltatime;
	do {

		currentFrame = glfwGetTime();
		deltatime = (float)(currentFrame - lastFrame);
		lastFrame = glfwGetTime();

		//controls
		if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
			camera.position += glm::vec3(-1, 0, 0) * deltatime * speed;
		}
		if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
			camera.position += glm::vec3(1, 0, 0) * deltatime * speed;
		}
		if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) {
			camera.position += glm::vec3(0, 0, -1) * deltatime * speed;
		}
		if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) {
			camera.position += glm::vec3(0, 0, 1) * deltatime * speed;
		}
		if (glfwGetKey(window, GLFW_KEY_Q) == GLFW_PRESS) {
			camera.position += glm::vec3(0, -1, 0) * deltatime * speed;
		}
		if (glfwGetKey(window, GLFW_KEY_E) == GLFW_PRESS) {
			camera.position += glm::vec3(0, 1, 0) * deltatime * speed;
		}

		#pragma region update
		tickScene(sceneObjects);
		#pragma endregion


		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glUseProgram(programID); 
		

		#pragma region draw
		try {
			drawScene(sceneObjects, programID, sun, camera);

			GLenum err;
			while ((err = glGetError()) != GL_NO_ERROR) {
				std::cerr << "OpenGL error[tick " << ticks << "]: " << err << std::endl;
			}
		}
		catch (std::exception ex) {
			std::cout << ex.what() << std::endl;
		}
			
		#pragma endregion


		glfwSwapBuffers(window);
		glfwPollEvents();
		ticks++;
	} while (glfwGetKey(window, GLFW_KEY_ESCAPE) != GLFW_PRESS && glfwWindowShouldClose(window) == 0);
	

	for (GameObjects::GameObject *object : sceneObjects) {
		if (GameObjects::MeshObject * mobject = dynamic_cast<GameObjects::MeshObject *> (object)) {
			mobject->mesh.Dispose();
		}

	}

}

