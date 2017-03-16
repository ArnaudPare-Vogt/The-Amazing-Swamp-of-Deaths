#version 330 core

layout(location = 0) in vec3 vertexPosition_modelspace;
layout(location = 1) in vec3 vertexColor;
layout(location = 2) in vec2 vertexUV;
layout(location = 3) in vec3 vertexNormal;
layout(location = 4) in vec3 vertexTangent;
layout(location = 5) in vec3 vertexBitangent;

uniform mat4 ModelMatrix;
uniform mat4 ViewMatrix;
uniform mat4 ProjectionMatrix;
uniform mat4 MVP;
uniform mat3 MV3x3;
uniform vec3 LightPosition_worldspace;
uniform vec3 LightColor;
uniform vec3 CameraPosition_worldspace;

out vec3 fragmentColor;
out vec2 UV;
out vec3 normal;
out vec3 posworldspace;
out vec4 lightColor;
out vec3 Normal_tangentspace;
out vec3 EyeDirection_tangentspace;
out vec3 LightDirection_tangentspace;


void main(){
	gl_Position = MVP * vec4(vertexPosition_modelspace,1);
	posworldspace = (ModelMatrix * vec4(vertexPosition_modelspace, 1)).xyz;

	//diffuse and specular lighting
		vec3 vertexPosition_camspace = (ViewMatrix * ModelMatrix * vec4(vertexPosition_modelspace, 1)).xyz;
		vec3 EyeDirection_camspace = CameraPosition_worldspace - vertexPosition_camspace;
	
		vec3 lightPosition_camspace = (ViewMatrix * vec4(LightPosition_worldspace,1)).xyz;
		vec3 LightDirection_camspace = lightPosition_camspace + EyeDirection_camspace;

	//end diffuse and specular lighting
	
	//normal mapping
		vec3 vertexNormal_camspace = MV3x3 * normalize(vertexNormal);
		vec3 vertexTangent_camspace = MV3x3 * normalize(vertexTangent);
		vec3 vertexBitangent_camspace = MV3x3 * normalize(vertexBitangent);
		mat3 TBN = transpose(mat3(
			vertexTangent_camspace,
			vertexBitangent_camspace,
			vertexNormal_camspace
		));
		LightDirection_tangentspace = TBN * LightDirection_camspace;
		EyeDirection_tangentspace = TBN * EyeDirection_camspace;

	//end normal mapping
	
	lightColor = vec4(LightColor,1);
	fragmentColor = vertexColor;
	UV = vertexUV;
	normal = vertexNormal;

}
