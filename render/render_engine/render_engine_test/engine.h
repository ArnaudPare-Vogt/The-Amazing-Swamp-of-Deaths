#pragma once
#include <string>
#include <GL\glew.h>
#include <GLFW\glfw3.h>
#include <glm\glm.hpp>


namespace render_engine {

	class engine {
	private:
		const int ANTIALIAS_SAMPLES = 4;
		const int OGL_VERSION_MAJOR = 3;
		const int OGL_VERSION_MINOR = 3;
		const int WINDOW_WIDTH = 1080;
		const int WINDOW_HEIGHT = 980;
		const std::string WINDOW_TITLE = "The Amazing Swamp of Death";

		bool initialized;
		GLFWwindow* window = nullptr;

		int initglfw() {
			if (!glfwInit()) {
				return (int)ERRORS::GLFW_INIT_FAILELD;
			}
			return 0;
		}

		int setupWindow(GLFWwindow* window) {
			glfwWindowHint(GLFW_SAMPLES, engine::ANTIALIAS_SAMPLES);
			glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, engine::OGL_VERSION_MAJOR);
			glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, engine::OGL_VERSION_MINOR);
			glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
			glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

			window = glfwCreateWindow(engine::WINDOW_WIDTH, engine::WINDOW_HEIGHT,
				engine::WINDOW_TITLE.c_str(), glfwGetPrimaryMonitor(), nullptr);
			if (nullptr == window) {
				glfwTerminate();
				return (int)ERRORS::GLFW_CREATE_WINDOW_FAILED;
			}
			glfwMakeContextCurrent(window);
			glewExperimental = true;
			if (GLEW_OK != glewInit()) {
				return (int)ERRORS::GLEW_INIT_FAILED;
			}

			return 0;
		}

	public:
		enum class ERRORS : int {
			SUCCESS = 0,
			GLFW_INIT_FAILELD = -1,
			GLFW_CREATE_WINDOW_FAILED = -2,
			GLEW_INIT_FAILED = -3
		};

		/*
		* @description
		*		Initializes the render engine and its window.
		*
		* @return:
		*		An integer representing the status.  refer to
		*		engine::ERRORS for meanings.
		*/
		int Init() {
			this->initialized = false;
			int initglfwResult = initglfw();
			if ((int)ERRORS::SUCCESS != initglfwResult) {
				return initglfwResult;
			}

			int setupWindowResult = setupWindow(this->window);
			if (setupWindowResult < 0) {
				return setupWindowResult;
			}
			glfwSetInputMode(this->window, GLFW_STICKY_KEYS, GL_TRUE);
			this->initialized = true;
			return (int)ERRORS::SUCCESS;
		}

		/*
		* @description
		*		Main loop of the engine. this method will not return untill
		*		the main render loop has completed or has been interrupted.
		*
		*	@return
		*		An integer representing the satus.  refer to
		*		engine::ERRORS for meanings.
		*/
		int MainLoop() {
			if (!this->initialized) {
				int initResult = this->Init();
				if ((int)ERRORS::SUCCESS != initResult) {
					return initResult;
				}
			}
			do {

				glfwSwapBuffers(window);
				glfwPollEvents();
			} while (this->EngineShouldClose());

			return (int)ERRORS::SUCCESS;
		}

		/*
		* @description
		*		Whether or not the engine should close.
		*
		* @return
		*		Wheter or not the engine should close.
		*/
		bool EngineShouldClose() {
			return (GLFW_PRESS != glfwGetKey(this->window, GLFW_KEY_ESCAPE) && glfwWindowShouldClose(window) == 0);
		}

	};


	static bool Init() {
		return true;
	}

};