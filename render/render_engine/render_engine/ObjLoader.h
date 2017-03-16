#pragma once
#include <stdio.h>
#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <string>
#include <algorithm>
#include <glm\glm.hpp>
#include <glm\gtc\matrix_transform.hpp>
#include <glm\gtx\transform.hpp>

#include "GameObject.h"
#include "Meshes.h"


namespace OBJ {

	class ObjLoader {
	private:
		static const std::string VERTEX_LINE;		// v
		static const std::string FACE_LINE;			// f
		static const std::string NORMAL_LINE;		// vn
		static const std::string UV_LINE;			// vt
		static const char FACE_SEPARATOR;			// /
		static const std::string MTL_LIB_LINE;		// mtllib
		static const std::string USEMTL_LINE;		// usemtl

		ObjLoader();
		~ObjLoader();
	public:
		static Meshes::Mesh Load(std::string objFilePath) {
			Meshes::Mesh mesh;
			std::cout << "Loading OBJ file: " << objFilePath << std::endl;
			try {
				std::ifstream objFile(objFilePath);
				std::vector<glm::vec3> verticesPool;
				std::vector<glm::vec3> normalsPool;
				std::vector<glm::vec2> uvPool;
				std::vector<glm::vec3> facesIndex; // x:vertex, y:UV, z:normal
				std::string line;

				while (std::getline(objFile, line)) {
					std::stringstream linestream(line);
					std::string firstWord;
					linestream >> firstWord;
					if (ObjLoader::VERTEX_LINE == firstWord) {
						float x;
						float y;
						float z;
						linestream >> x;
						linestream >> y;
						linestream >> z;
						verticesPool.push_back(glm::vec3( x,y,z ));
					} else if (ObjLoader::NORMAL_LINE == firstWord) {
						float x;
						float y;
						float z;
						linestream >> x;
						linestream >> y;
						linestream >> z;
						normalsPool.push_back(glm::vec3(x, y, z));
					} else if (ObjLoader::UV_LINE == firstWord) {
						float u;
						float v;
						linestream >> u;
						linestream >> v;
						uvPool.push_back(glm::vec2(u,v));
					} else if (ObjLoader::FACE_LINE == firstWord) {
						std::string currentVert;
						for (int it = 0; it < 3; it++) {	//triangles so 3 vert
							linestream >> currentVert;		// gets the #/#/#
							std::string V;
							std::string U;
							std::string N;
							std::stringstream VUNStream(currentVert);
							std::getline(VUNStream, V, ObjLoader::FACE_SEPARATOR);	//store separate #'s
							std::getline(VUNStream, U, ObjLoader::FACE_SEPARATOR);
							std::getline(VUNStream, N, ObjLoader::FACE_SEPARATOR);
							
							facesIndex.push_back( glm::vec3(
								std::stoi(V) - 1,
								std::stoi(U) - 1,
								std::stoi(N) - 1
							) );

						}
					}

				}


				//make the mesh by giving it ordered vertices, uvs and normals using facesindex
				for (glm::vec3 face : facesIndex) {
					mesh.vertex_data.push_back(verticesPool[(int)face.x].x);
					mesh.vertex_data.push_back(verticesPool[(int)face.x].y);
					mesh.vertex_data.push_back(verticesPool[(int)face.x].z);

					mesh.uv_data.push_back(uvPool[(int)face.y].x);
					mesh.uv_data.push_back(uvPool[(int)face.y].y);

					mesh.normal_data.push_back(normalsPool[(int)face.z].x);
					mesh.normal_data.push_back(normalsPool[(int)face.z].y);
					mesh.normal_data.push_back(normalsPool[(int)face.z].z);
					
					//dummy colors
					mesh.color_data.push_back(1);
					mesh.color_data.push_back(1);
					mesh.color_data.push_back(1);
				}

				//dont forget to init the mesh
				mesh.Init();
				std::cout << "Done loading OBJ file: " << objFilePath << std::endl;
			}
			catch (std::exception exception) {
				std::cout << "Exception while loading " << objFilePath << ":" << std::endl << exception.what();
			}
			return mesh;
		}

	};

	class MaterialLoader {
	private:
		/**** LIST OF UNSUPPORTED MATERIAL FEATURES ****
		 *
		 * illumination
		 * STENCILS
		 * DECALS
		 * BUMP MAPS
		 * Texture Maps Options Parameters
		 * 
		************************************************/
		static const std::string NEWMTL_LINE;				// newmtl
		static const std::string AMBIENT_LINE;				// Ka
		static const std::string DIFFUSE_LINE;				// Kd
		static const std::string SPECULAR_LINE;				// Ks
		static const std::string SPECULAT_EXPONENT_LINE;	// Ns
		static const std::string DISSOLVED_LINE;			// d
		static const std::string TRANSPARENT_LINE;			// Tr
		static const std::string MAP_PREFIX;				// map_
		static const std::string DISPLACEMENT_MAP_LINE;		// disp

	public:
		static Meshes::Material Load(std::string materialFilePath) {
			Meshes::Material material;
			std::cout << "Loading Material file: " << materialFilePath << std::endl;
			try {
				std::ifstream objFile(materialFilePath);
				std::string line;

				while (std::getline(objFile, line)) {
					std::stringstream linestream(line);
					std::string firstWord;
					linestream >> firstWord;
					if (MaterialLoader::NEWMTL_LINE == firstWord) {
						linestream >> material.name;
					}

				}
				std::cout << "Done loading Meterial file: " << materialFilePath << std::endl;
			}
			catch (std::exception exception) {
				std::cout << "Exception while loading " << materialFilePath << ":" << std::endl << exception.what();
			}
			return material;
		}

		
	private:
		MaterialLoader() {}
		~MaterialLoader() {}
	};


	

}


