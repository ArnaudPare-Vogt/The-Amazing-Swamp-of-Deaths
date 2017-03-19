#include "ShaderManager.h"

using namespace ninecore::shaders;

ShaderManager* ShaderManager::_instance = nullptr;

ShaderManager::ShaderManager()
{
	this->currentShaderProgramID = 0;
}

ShaderManager::~ShaderManager()
{
	if (nullptr != ShaderManager::_instance) {
		delete ShaderManager::_instance;
	}
}

void ShaderManager::dispose() {
	ShaderManager::_instance->~ShaderManager();
}

ShaderManager* ShaderManager::instance() {
	if (nullptr == ShaderManager::_instance) {
		ShaderManager::_instance = new ShaderManager();
	}
	return ShaderManager::_instance;
}

void ShaderManager::RegisterShaderProgram(std::string name, GLuint programid) {
	ShaderManager::instance()->shaderTable.insert(std::pair<std::string, GLuint>(name, programid));
}

int ShaderManager::CreateShaderProgram(GLuint& OUT_programid, const char* vertex_file_path, const char * fragment_file_path) {
	GLuint VertexShaderID = glCreateShader(GL_VERTEX_SHADER);
	GLuint FragmentShaderID = glCreateShader(GL_FRAGMENT_SHADER);
	std::string VertexShaderCode;
	std::string FragmentShaderCode;
	GLint result = GL_FALSE;
	int infologlength;
	
#pragma region compile
	//vertex
	printf("compiling shader: %s\n", vertex_file_path);
	std::string VertexSourcePointer;
	unsigned int vertex_size = 0;
	if (0 != this->ReadShaderFile(vertex_file_path, vertex_size, VertexSourcePointer)) {
		return -1;
	}
	const char * ConstVertexSourcePtr = VertexSourcePointer.c_str();
	glShaderSource(VertexShaderID, 1, &ConstVertexSourcePtr, NULL);
	glCompileShader(VertexShaderID);
	glGetShaderiv(VertexShaderID, GL_COMPILE_STATUS, &result);
	glGetShaderiv(infologlength, GL_INFO_LOG_LENGTH, &infologlength);
	if (GL_FALSE == result && infologlength > 0) {
		std::vector<char> VertexShaderErrorMessage(infologlength + 1);
		glGetShaderInfoLog(VertexShaderID, infologlength, NULL, &VertexShaderErrorMessage[0]);
		printf("[ERROR] - %s\n", &VertexShaderErrorMessage[0]);
		return -2;
	}

	//frag
	printf("compiling shader: %s\n", fragment_file_path);
	std::string FragmentSourcePointer;
	unsigned int frag_size = 0;
	if (0 != this->ReadShaderFile(fragment_file_path, frag_size, FragmentSourcePointer)) {
		return -1;
	}
	const char * ConstFragmentSourcePtr = FragmentSourcePointer.c_str();
	glShaderSource(FragmentShaderID, 1, &ConstFragmentSourcePtr, NULL);
	glCompileShader(FragmentShaderID);
	glGetShaderiv(FragmentShaderID, GL_COMPILE_STATUS, &result);
	glGetShaderiv(infologlength, GL_INFO_LOG_LENGTH, &infologlength);
	if (GL_FALSE == result && infologlength > 0) {
		std::vector<char> FragmentShaderErrorMessage(infologlength + 1);
		glGetShaderInfoLog(FragmentShaderID, infologlength, NULL, &FragmentShaderErrorMessage[0]);
		printf("[ERROR] - %s\n", &FragmentShaderErrorMessage[0]);
		return -2;
	}
#pragma endregion compile

#pragma region linking
	printf("linking program\n");
	GLuint ProgramID = glCreateProgram();
	glAttachShader(ProgramID, VertexShaderID);
	glAttachShader(ProgramID, FragmentShaderID);
	glLinkProgram(ProgramID);

	glGetProgramiv(ProgramID, GL_LINK_STATUS, &result);
	glGetProgramiv(ProgramID, GL_INFO_LOG_LENGTH, &infologlength);
	std::cout << result << std::endl;
	if (GL_FALSE == result && infologlength > 0) {
		std::vector<char> ProgramErrorMessage(infologlength + 1);
		glGetProgramInfoLog(ProgramID, infologlength, NULL, &ProgramErrorMessage[0]);
		printf("[ERROR] %s\n", &ProgramErrorMessage[0]);
		std::cout << FragmentSourcePointer << std::endl;
		return -3;
	}

	glDetachShader(ProgramID, VertexShaderID);
	glDetachShader(ProgramID, FragmentShaderID);
	glDeleteShader(VertexShaderID);
	glDeleteShader(FragmentShaderID);
#pragma endregion linking
	OUT_programid = ProgramID;
	return 0;
}

GLuint ShaderManager::ReadShaderFile(const char * file_path, unsigned int & OUT_size, std::string& OUT_file_contents) {
	std::ifstream ShaderStream(file_path, std::ios::in);
	std::string ShaderCode = "";
	if (ShaderStream.is_open()) {
		std::string line = "";
		while (getline(ShaderStream, line)) {
			ShaderCode += "\n" + line;
		}
		ShaderStream.close();
	}
	else {
		printf("[read shader file] impossible to open %s.", file_path);
		return -1;
	}
	OUT_file_contents = ShaderCode;
	OUT_size = ShaderCode.length();

	return 0;
}