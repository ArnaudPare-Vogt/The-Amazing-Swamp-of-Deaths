#include "Meshes.h"
#include <glm\glm.hpp>
#include <glm\gtc\matrix_transform.hpp>
#include <glm\gtx\transform.hpp>
#include <iostream>

using namespace Meshes;

#pragma region Mesh
Mesh::Mesh(std::vector<GLfloat> & _vertex_data, std::vector<GLfloat> & _color_data) :
	vertex_data(_vertex_data),
	color_data(_color_data) {

	Init();
}
Mesh::Mesh(std::vector<GLfloat> & _vertex_data, std::vector<GLfloat> & _color_data, std::vector<GLfloat> & _uv_data) :
	vertex_data(_vertex_data),
	color_data(_color_data),
	uv_data(_uv_data) {

	Init();
}

void Mesh::ComputeTangents(Meshes::Mesh & mesh) {
	for (int i = 0; i< mesh.vertex_data.size()/3 && i < mesh.uv_data.size()/2 ; i += 3) { 
		// Shortcuts for vertices
		glm::vec3 & v0 = glm::vec3(mesh.vertex_data[i * 3 + 0],		  mesh.vertex_data[i * 3 + 1],		 mesh.vertex_data[i * 3 + 2]);
		glm::vec3 & v1 = glm::vec3(mesh.vertex_data[(i + 1) * 3 + 0], mesh.vertex_data[(i + 1) * 3 + 1], mesh.vertex_data[(i + 1) * 3 + 2]);
		glm::vec3 & v2 = glm::vec3(mesh.vertex_data[(i + 2) * 3 + 0], mesh.vertex_data[(i + 2) * 3 + 1], mesh.vertex_data[(i + 2) * 3 + 2]);
		// Shortcuts for UVs 
		glm::vec2 & uv0 = glm::vec2(mesh.uv_data[i * 2 + 0],	   mesh.uv_data[i * 2 + 1]);
		glm::vec2 & uv1 = glm::vec2(mesh.uv_data[(i + 1) * 2 + 0], mesh.uv_data[(i + 1) * 2 + 1]);
		glm::vec2 & uv2 = glm::vec2(mesh.uv_data[(i + 2) * 2 + 0], mesh.uv_data[(i + 2) * 2 + 1]);
		// Edges of the triangle : postion delta
		glm::vec3 deltaPos1 = v1 - v0;
		glm::vec3 deltaPos2 = v2 - v0;
		// UV delta
		glm::vec2 deltaUV1 = uv1 - uv0;
		glm::vec2 deltaUV2 = uv2 - uv0;

		//calculate tangent and bitangent
		float r = 1.0f / (deltaUV1.x * deltaUV2.y - deltaUV1.y * deltaUV2.x);
		glm::vec3 tangent = (deltaPos1 * deltaUV2.y - deltaPos2 * deltaUV1.y)*r;
		glm::vec3 bitangent = (deltaPos2 * deltaUV1.x - deltaPos1 * deltaUV2.x)*r;
		float nx = (mesh.normal_data[i * 3 + 0] + mesh.normal_data[(i + 1) * 3 + 0] + mesh.normal_data[(i + 2) * 3 + 0]) / 3.f;
		float ny = (mesh.normal_data[i * 3 + 1] + mesh.normal_data[(i + 1) * 3 + 1] + mesh.normal_data[(i + 2) * 3 + 1]) / 3.f;
		float nz = (mesh.normal_data[i * 3 + 2] + mesh.normal_data[(i + 1) * 3 + 2] + mesh.normal_data[(i + 2) * 3 + 2]) / 3.f;
		glm::vec3 n = glm::vec3(nx, ny, nz);
		tangent = glm::normalize(tangent - n * glm::dot(n, tangent));
		bitangent = glm::cross(n, tangent);//glm::normalize(bitangent - n * glm::dot(n, bitangent));

		// Set the same tangent for all three vertices of the triangle.
		mesh.tangents_data.push_back(tangent.x);
		mesh.tangents_data.push_back(tangent.y);
		mesh.tangents_data.push_back(tangent.z);
		mesh.tangents_data.push_back(tangent.x);
		mesh.tangents_data.push_back(tangent.y);
		mesh.tangents_data.push_back(tangent.z);
		mesh.tangents_data.push_back(tangent.x);
		mesh.tangents_data.push_back(tangent.y);
		mesh.tangents_data.push_back(tangent.z);
		// Same thing for binormals
		mesh.bitangents_data.push_back(bitangent.x);
		mesh.bitangents_data.push_back(bitangent.y);
		mesh.bitangents_data.push_back(bitangent.z);
		mesh.bitangents_data.push_back(bitangent.x);
		mesh.bitangents_data.push_back(bitangent.y);
		mesh.bitangents_data.push_back(bitangent.z);
		mesh.bitangents_data.push_back(bitangent.x);
		mesh.bitangents_data.push_back(bitangent.y);
		mesh.bitangents_data.push_back(bitangent.z);
	}
}


#pragma endregion
