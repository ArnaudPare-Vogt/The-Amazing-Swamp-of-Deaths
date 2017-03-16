#version 330 core

in vec3 fragmentColor;
in vec2 UV;
in vec3 normal;

in vec3 posworldspace;
in vec4 lightColor;
in vec3 Normal_tangentspace;
in vec3 EyeDirection_tangentspace;
in vec3 LightDirection_tangentspace;

out vec4 color;

uniform sampler2D textureSampler;
uniform sampler2D normalMapSampler;

vec4 specColor = vec4(1,1,1,1);
int specStrength = 10;

vec3 lerp(vec4 a, vec4 b, float s)
{
    return vec3(a + (b - a) * s);       
}

void main(){
	//normal mapping
		vec3 Normal_tangentspace = normalize((2.0* texture(normalMapSampler, UV).rgb) -1);

	//end normal mapping

	//diffuse and specular
		vec3 nNormal_camspace = normalize(Normal_tangentspace);
		vec3 Lightdir_camspace = normalize(LightDirection_tangentspace);
		vec3 EyeDir = normalize(EyeDirection_tangentspace);
		vec3 reflectLight_Eye = reflect(-Lightdir_camspace, nNormal_camspace);
	//end diffuse and specular

	float reflectionExposure = clamp( dot(EyeDir,reflectLight_Eye), 0, 1 );
	float exposure = clamp(dot(nNormal_camspace, Lightdir_camspace), 0, 1);

	vec4 diffuse = texture(textureSampler, UV).rgba;
	vec4 ambient = vec4(0.3,0.3,0.3, 1) * diffuse;

	color = ambient
		 + diffuse * lightColor * exposure
		 + specColor * lightColor * pow(reflectionExposure, specStrength);
}