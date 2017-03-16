#pragma once
#include <vector>
#include <iostream>
#include <GL\glew.h>
#include <GL\GL.h>
#include <GL\freeglut.h>
#include <GLFW\glfw3.h>
#include <glm\glm.hpp>
#include <glm\gtc\matrix_transform.hpp>
#include <glm\gtx\transform.hpp>

namespace Meshes {
	class Mesh {
	public:
		GLuint vertexArrayID;
		GLuint elementBufferID;
		GLuint vertexBufferID;
		GLuint colorBufferID;
		GLuint uvBufferID;
		GLuint textureID;
		GLuint normalBufferID;
		GLuint normalMapTextureID;
		GLuint tangentsBufferID;
		GLuint bitangentsBufferID;
		std::vector<GLushort> indices_data;
		std::vector<GLfloat> vertex_data;
		std::vector<GLfloat> color_data;
		std::vector<GLfloat> uv_data;
		std::vector<GLfloat> normal_data;
		std::vector<GLfloat> bitangents_data;
		std::vector<GLfloat> tangents_data;


		Mesh() {}
		Mesh(std::vector<GLfloat> & _vertex_data, std::vector<GLfloat> & _color_data);
		Mesh(std::vector<GLfloat> & _vertex_data, std::vector<GLfloat> & _color_data, std::vector<GLfloat> & _uv_data);
		~Mesh() {}
		void ComputeTangents(Mesh & mesh);

		void FillBuffers() {

			std::cout << "vertex: " << vertex_data.size() << std::endl;
			std::cout << "normals: " << normal_data.size() << std::endl;
			std::cout << "tangents: " << tangents_data.size() << std::endl;
			std::cout << "bitangents: " << bitangents_data.size() << std::endl;
			std::cout << "UV: " << uv_data.size() << " (2/3 vertex = " << vertex_data.size() * 2 / 3 << ")" << std::endl;
			glBindVertexArray(vertexArrayID);
			glBindBuffer(GL_ARRAY_BUFFER, vertexBufferID);
			glBufferData(GL_ARRAY_BUFFER, sizeof(GLfloat) * vertex_data.size(), &vertex_data[0], GL_STATIC_DRAW);
			glBindBuffer(GL_ARRAY_BUFFER, colorBufferID);
			glBufferData(GL_ARRAY_BUFFER, sizeof(GLfloat) * color_data.size(), &color_data[0], GL_STATIC_DRAW);
			glBindBuffer(GL_ARRAY_BUFFER, uvBufferID);
			glBufferData(GL_ARRAY_BUFFER, sizeof(GLfloat) * uv_data.size(), &uv_data[0], GL_STATIC_DRAW);
			glBindBuffer(GL_ARRAY_BUFFER, normalBufferID);
			glBufferData(GL_ARRAY_BUFFER, sizeof(GLfloat) * normal_data.size(), &normal_data[0], GL_STATIC_DRAW);
			glBindBuffer(GL_ARRAY_BUFFER, tangentsBufferID);
			glBufferData(GL_ARRAY_BUFFER, sizeof(GLfloat) * tangents_data.size(), &tangents_data[0], GL_STATIC_DRAW);
			glBindBuffer(GL_ARRAY_BUFFER, bitangentsBufferID);
			glBufferData(GL_ARRAY_BUFFER, sizeof(GLfloat) * bitangents_data.size(), &bitangents_data[0], GL_STATIC_DRAW);
			/*glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, elementBufferID);
			glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(GLushort) * indices_data.size(), &indices_data[0], GL_STATIC_DRAW);*/			

		}

		void Bind() {
			glBindVertexArray(vertexArrayID);
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, elementBufferID);
			glBindBuffer(GL_ARRAY_BUFFER, vertexBufferID);
			glBindBuffer(GL_ARRAY_BUFFER, colorBufferID);
			glBindBuffer(GL_ARRAY_BUFFER, uvBufferID);
			glBindBuffer(GL_ARRAY_BUFFER, normalBufferID);
			glBindBuffer(GL_ARRAY_BUFFER, tangentsBufferID);
			glBindBuffer(GL_ARRAY_BUFFER, bitangentsBufferID);		
		}

		void UnBind() {
			glBindVertexArray(0);
		}

		void PreDraw() {
			Bind();
			//vertices
			glEnableVertexAttribArray(0);
			glBindBuffer(GL_ARRAY_BUFFER, vertexBufferID);
			glVertexAttribPointer(
				0,
				3,
				GL_FLOAT,
				GL_FALSE,
				0,
				nullptr
			);
			//colors
			glEnableVertexAttribArray(1);
			glBindBuffer(GL_ARRAY_BUFFER, colorBufferID);
			glVertexAttribPointer(
				1,
				3,
				GL_FLOAT,
				GL_FALSE,
				0,
				(void*)0
			);
			//UV
			glEnableVertexAttribArray(2);
			glBindBuffer(GL_ARRAY_BUFFER, uvBufferID);
			glVertexAttribPointer(
				2,
				2,
				GL_FLOAT,
				GL_FALSE,
				0,
				(void*)0
			);
			//UV
			glEnableVertexAttribArray(3);
			glBindBuffer(GL_ARRAY_BUFFER, normalBufferID);
			glVertexAttribPointer(
				3,
				3,
				GL_FLOAT,
				GL_FALSE,
				0,
				(void*)0
			);
			//tangents
			glEnableVertexAttribArray(4);
			glBindBuffer(GL_ARRAY_BUFFER, tangentsBufferID);
			glVertexAttribPointer(
				4,
				3,
				GL_FLOAT,
				GL_FALSE,
				0,
				(void*)0
			);
			//bitangents
			glEnableVertexAttribArray(5);
			glBindBuffer(GL_ARRAY_BUFFER, bitangentsBufferID);
			glVertexAttribPointer(
				5,
				3,
				GL_FLOAT,
				GL_FALSE,
				0,
				(void*)0
			);
			//textures
			glActiveTexture(GL_TEXTURE0);
			glBindTexture(GL_TEXTURE_2D, textureID);
			glActiveTexture(GL_TEXTURE1);
			glBindTexture(GL_TEXTURE_2D, normalMapTextureID);
		}

		void PostDraw() {
			glDisableVertexAttribArray(0); //vertex
			glDisableVertexAttribArray(1); //color
			glDisableVertexAttribArray(2); //uv
			glDisableVertexAttribArray(3); //normals
			glDisableVertexAttribArray(4); //tangents
			glDisableVertexAttribArray(5); //bitangents
		}

		void Draw() {
			/*glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, elementBufferID);
			glDrawElements(
				GL_TRIANGLES,
				indices_data.size(),
				GL_UNSIGNED_SHORT,
				nullptr
			);*/

			glDrawArrays(GL_TRIANGLES, 0, vertex_data.size());
			PostDraw();

		}
		
		void Init() {
			glGenVertexArrays(1, &vertexArrayID);
			glBindVertexArray(vertexArrayID);
			glGenBuffers(1, &elementBufferID);
			glGenBuffers(1, &vertexBufferID);
			glGenBuffers(1, &colorBufferID);
			glGenBuffers(1, &uvBufferID);
			glGenBuffers(1, &normalBufferID);
			glGenBuffers(1, &tangentsBufferID);
			glGenBuffers(1, &bitangentsBufferID);
			ComputeTangents(*this);
			FillBuffers();
			UnBind();

			
		}
		void Dispose() {
			glDeleteVertexArrays(1, &vertexArrayID);
			glDeleteBuffers(1, &vertexBufferID);
			glDeleteBuffers(1, &elementBufferID);
			glDeleteBuffers(1, &normalBufferID);
			glDeleteBuffers(1, &colorBufferID);
			glDeleteBuffers(1, &uvBufferID);
			glDeleteBuffers(1, &tangentsBufferID);
			glDeleteBuffers(1, &bitangentsBufferID);

		}

	};

	class Material {
	#pragma region members
	public:
		std::string name;
		glm::vec3 diffuse;
		glm::vec3 ambient;
		glm::vec3 specular;
		float specular_exponent;
		float dissolved;
		float transparent;
		GLuint ambient_map;
		GLuint diffuse_map;
		GLuint specular_map;
		GLuint specular_exponent_map;




	#pragma endregion
	public:
		Material();
		~Material();

	};
}

