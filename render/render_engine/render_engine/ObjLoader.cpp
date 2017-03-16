#include "ObjLoader.h"

using namespace OBJ;

ObjLoader::ObjLoader()
{
}

const std::string ObjLoader::VERTEX_LINE = "v";
const std::string ObjLoader::NORMAL_LINE = "vn";
const std::string ObjLoader::UV_LINE = "vt";
const std::string ObjLoader::FACE_LINE = "f";
const std::string ObjLoader::MTL_LIB_LINE = "mtllib";
const std::string ObjLoader::USEMTL_LINE = "usemtl";
const char ObjLoader::FACE_SEPARATOR = '/';

ObjLoader::~ObjLoader()
{
}
