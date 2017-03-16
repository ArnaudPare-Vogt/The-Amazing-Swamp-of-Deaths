#pragma once
#include <vector>
#include <GL\glew.h>
#include <GL\GL.h>
#include <GL\freeglut.h>
#include <GLFW\glfw3.h>
#include <glm\glm.hpp>
#include <glm\gtc\matrix_transform.hpp>
#include <glm\gtx\transform.hpp>
#include "Meshes.h"

namespace GameObjects {
	

	class GameObject
	{
	public:
		glm::vec3 position;
		glm::vec4 rotation;	// x y z orientation + w is amount of rotation around xyz axis
		glm::vec3 scale;
		glm::vec3 deltaPosition;
		glm::vec4 deltaRotation;
		glm::vec3 deltaScale;

		GameObject(glm::vec3 _position, glm::vec4 _rotation, glm::vec3 _scale);
		virtual ~GameObject();

		void Tick();

		glm::mat4 MakeModelMatrix() {
			return glm::translate(position) * glm::rotate(-rotation.w, glm::vec3(rotation)) * glm::scale(scale);
		}

	};

	class MeshObject : public GameObject {
	public:
		Meshes::Mesh mesh;

		MeshObject( glm::vec3 _position, glm::vec4 _rotation, glm::vec3 _scale, Meshes::Mesh & _mesh);
		~MeshObject();
		void Draw();

	};

	class Light : public GameObject {
	public:
		float max_range;
		glm::vec3 color;

		Light(glm::vec3 _position, glm::vec3 _color);
		~Light() {}

	};

	class Camera : public GameObject{
	public:
		float fov;
		float speed;
		Camera(glm::vec3 _position, glm::vec4 _rotation);
		~Camera() {}
		glm::vec3 direction();
	};

};
