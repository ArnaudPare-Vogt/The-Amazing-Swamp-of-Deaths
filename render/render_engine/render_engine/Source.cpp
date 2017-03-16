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
#include "Engine.h"

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




int main() {
	GLuint programID;
	ninecore::Engine* engine = new ninecore::Engine();

	if (0 != engine->init()) {
		goto Exit;
		return -1;
	}	
	if(0 != engine->shadermanager->CreateShaderProgram(programID, "./Vertex.glsl", "./Fragment.glsl")){
		goto Exit;
		return -1;
	}

	{
		engine->shadermanager->RegisterShaderProgram("main", programID);
		engine->shadermanager->currentShaderProgramID = programID;

		GLuint Texture = loadBMP_custom("./Textures/crate.bmp");
		GLuint NormalMap = loadBMP_custom("./Textures/crate_normal.bmp");
		Meshes::Mesh m2 = OBJ::ObjLoader::Load("./Models/tile.obj");

		m2.textureID = Texture;
		m2.normalMapTextureID = NormalMap;


#pragma region objects
		GameObjects::MeshObject tile(
			glm::vec3(0, 0, -5),
			glm::vec4(0, 1, 0, 0.75f),
			glm::vec3(1, 1, 1),
			m2
		);
		tile.deltaRotation = glm::vec4(0,1,0,0.005f);
		engine->sceneObjects.push_back(&tile);

		GameObjects::Light sun(
			glm::vec3(-10, 10, 0),
			glm::vec3(1, 1, 1)
		);
		engine->sceneObjects.push_back(&sun);
		engine->lights.push_back(&sun);
		engine->sun = &sun;


#pragma endregion


		float initialFOV = 45.0f;
		float mouseSpeed = 0.005f;
		//camera
		GameObjects::Camera camera(
			glm::vec3(0, 1, 0),
			glm::vec4(0, 0, -1, 0)
		);
		engine->sceneObjects.push_back(&camera);
		engine->cameras.push_back(&camera);
		engine->mainCamera = &camera;
		camera.fov = initialFOV;

		GameObjects::Camera suncam(
			glm::vec3(0, 1, 0),
			glm::vec4(0, 0, -1, 0)
		);
		suncam.fov = initialFOV;
		

		engine->start();

		engine->cleanup();
	}
	return 0;

Exit:
	engine->cleanup();
	std::cout << "END OF PROGRAM";
	std::cin.get();
	return -1;
}

