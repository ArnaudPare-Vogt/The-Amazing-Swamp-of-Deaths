#include "Engine.h"

using namespace ninecore;

Engine::Engine()
{
	this->ticks = 0;
	this->deltaTime = 0;
	this->currentFrameTime = 0;
	this->window = nullptr;
	this->mainCamera = nullptr;
	this->sun = nullptr;
	this->shadermanager = shaders::ShaderManager::instance();
}


Engine::~Engine()
{
}

int Engine::init() {
	if (initGLFW() < 0) {
		return -1;
	}
	if (initWindow(this->window) < 0) {
		return -2;
	}
}

int Engine::initGLFW() {
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

int Engine::initWindow(GLFWwindow*& window) {
	window = glfwCreateWindow(WINDOW_WT, WINDOW_HT, "ninecore", NULL, NULL);

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
	return 0;
}

void Engine::tickScene(std::vector<GameObjects::GameObject*> & sceneObjects) {
	for (GameObjects::GameObject *object : sceneObjects) {
		object->Tick();
	}
}

void Engine::drawScene(std::vector<GameObjects::GameObject*> & sceneObjects, GLuint & programID) {
	if (nullptr == this->mainCamera) {
		return;
	}
	glm::mat4 Projection = glm::perspective(glm::radians(this->mainCamera->fov), (float)4 / (float)3, 0.1f, 100.0f);
	glm::mat4 View = glm::lookAt(
		this->mainCamera->position,
		this->mainCamera->position + this->mainCamera->direction(),
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
				GLuint cameraPosition_Handle = glGetUniformLocation(programID, "CameraPosition_worldspace");

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

				glUniform3fv(LightColor_Handle, 1, &this->sun->color[0]);
				glUniform3fv(LightPosition_Handle, 1, &this->sun->position[0]);
				glUniform3fv(cameraPosition_Handle, 1, &this->mainCamera->position[0]);
				mobject->Draw();

			}
			catch (std::exception ex) {
				std::cout << ex.what() << std::endl;
			}

		}

	}


}

void Engine::start() {
	this->ticks = 0;
	this->deltaTime = 0;
	this->currentFrameTime = 0;
	this->lastFrameTime = glfwGetTime();
	
	do {
		currentFrameTime = glfwGetTime();
		this->deltaTime = (float)(this->currentFrameTime - this->lastFrameTime);
		lastFrameTime = glfwGetTime();

		//controls
		if (nullptr != this->mainCamera) {
			if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
				this->mainCamera->position += glm::vec3(-1, 0, 0) * this->deltaTime * this->mainCamera->speed;
			}
			if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
				this->mainCamera->position += glm::vec3(1, 0, 0) * this->deltaTime * this->mainCamera->speed;
			}
			if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) {
				this->mainCamera->position += glm::vec3(0, 0, -1) * this->deltaTime * this->mainCamera->speed;
			}
			if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) {
				this->mainCamera->position += glm::vec3(0, 0, 1) * this->deltaTime * this->mainCamera->speed;
			}
			if (glfwGetKey(window, GLFW_KEY_Q) == GLFW_PRESS) {
				this->mainCamera->position += glm::vec3(0, -1, 0) * this->deltaTime * this->mainCamera->speed;
			}
			if (glfwGetKey(window, GLFW_KEY_E) == GLFW_PRESS) {
				this->mainCamera->position += glm::vec3(0, 1, 0) * this->deltaTime * this->mainCamera->speed;
			}
			if (nullptr != this->sun) {
				this->sun->position = this->mainCamera->position;
			}
		}
		

		//update scene
		this->tickScene(sceneObjects);

		//render process
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glUseProgram(this->shadermanager->currentShaderProgramID);
		try {
			drawScene(sceneObjects, this->shadermanager->currentShaderProgramID);

			GLenum err;
			while ((err = glGetError()) != GL_NO_ERROR) {
				std::cerr << "OpenGL error[tick " << ticks << "]: " << err << std::endl;
			}
		}
		catch (std::exception ex) {
			std::cout << ex.what() << std::endl;
		}


		
		glfwSwapBuffers(window);
		glfwPollEvents();
		ticks++;
	} while (glfwGetKey(window, GLFW_KEY_ESCAPE) != GLFW_PRESS && glfwWindowShouldClose(window) == 0);
}

void Engine::cleanup() {
	for (GameObjects::GameObject *object : this->sceneObjects) {
		if (GameObjects::MeshObject * mobject = dynamic_cast<GameObjects::MeshObject *> (object)) {
			mobject->mesh.Dispose();
		}

	}
}