#pragma once
#define _CRT_SECURE_NO_DEPRECATE

#include <stdio.h>
#include <stdlib.h>
#include <string>
#include <vector>
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
#include "ShaderManager.h"
#define WINDOW_WT 1024
#define WINDOW_HT 768


namespace ninecore {
	class Engine
	{
	protected:
		long ticks;
		double lastFrameTime;
		double currentFrameTime;
		float deltaTime;
		GLFWwindow* window;
		

	public:
		std::vector<GameObjects::GameObject*> sceneObjects;
	    std::vector<GameObjects::Camera*> cameras;
	    std::vector<GameObjects::Light*> lights;
		shaders::ShaderManager* shadermanager;
		GameObjects::Camera* mainCamera;
		GameObjects::Light* sun;
		Engine();
		int init();
		void drawScene(std::vector<GameObjects::GameObject*> &, GLuint &);
		void tickScene(std::vector<GameObjects::GameObject*> & );
		int initWindow(GLFWwindow*&);
		int initGLFW();
		void start();
		void cleanup();


		~Engine();
	};
};

