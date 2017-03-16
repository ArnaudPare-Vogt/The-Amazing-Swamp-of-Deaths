// render_engine_test.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include "engine.h"


int main()
{
	render_engine::engine* engine = new render_engine::engine();
	int res = engine->MainLoop();
    return res;
}

