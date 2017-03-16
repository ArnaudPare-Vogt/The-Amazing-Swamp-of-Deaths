#include <algorithm>
#include "GameObject.h"


using namespace GameObjects;

#pragma region GameObject
GameObject::GameObject(glm::vec3 _position, glm::vec4 _rotation, glm::vec3 _scale) :
	position(_position), 
	rotation(_rotation),
	scale(_scale){
	
}
GameObject::~GameObject(void )
{

}

void GameObject::Tick() {
	position += deltaPosition;
	rotation += deltaRotation;
	scale += deltaScale;
}

#pragma endregion

#pragma region MeshObject
MeshObject::MeshObject( glm::vec3 _position, glm::vec4 _rotation, glm::vec3 _scale, Meshes::Mesh & _mesh)
	: GameObject(_position, _rotation, _scale), mesh(_mesh) {
	
}
MeshObject::~MeshObject() {

}

void MeshObject::Draw() {
	this->mesh.Draw();
}
#pragma endregion

#pragma region Light
Light::Light(glm::vec3 _position, glm::vec3 _color)
	: GameObject( _position, glm::vec4(0,0,0,0), glm::vec3(1,1,1) ), color(_color){

}
#pragma endregion

#pragma region Camera
Camera::Camera(glm::vec3 _position, glm::vec4 _rotation)
	:GameObject(_position, rotation, glm::vec3(1,1,1)) {

}

glm::vec3 Camera::direction() {
	return glm::vec3(0,0,-1);
}
#pragma endregion
