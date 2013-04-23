#!/usr/bin/python
import lutinModule
import lutinTools

def Create(target):
	# module name is 'edn' and type binary.
	myModule = lutinModule.module(__file__, 'etk', 'LIBRARY')
	# add the file to compile:
	
	
	myModule.AddSrcFile([
		'etk/Debug.cpp',
		'etk/DebugInternal.cpp',
		'etk/unicode.cpp',
		'etk/unicodeTable.cpp',
		'etk/Char.cpp',
		'etk/UString.cpp',
		'etk/Stream.cpp',
		'etk/RegExp.cpp',
		'etk/tool.cpp',
		'etk/Noise.cpp'])
	
	
	myModule.AddSrcFile([
		'etk/math/Matrix4.cpp',
		'etk/math/Vector2D.cpp',
		'etk/math/Vector3D.cpp'])
	
	
	myModule.AddSrcFile([
		'etk/os/FSNode.cpp',
		'etk/os/FSNodeRight.cpp',
		'etk/os/Memory.cpp'])
	
	if target.name=="Windows":
		myModule.AddSrcFile('etk/os/Mutex.Windows.cpp')
		myModule.AddSrcFile('etk/os/Semaphore.Windows.cpp')
	else:
		myModule.AddSrcFile('etk/os/Mutex.Generic.cpp')
		myModule.AddSrcFile('etk/os/Semaphore.Generic.cpp')
	
	# name of the dependency
	myModule.AddModuleDepend('linearmath')
	if target.name=="Android":
		myModule.AddModuleDepend('zip')
	
	myModule.CompileFlags_CC([
		'-Wno-write-strings',
		'-Wall'])
	
	if target.buildMode == "release":
		# TODO : The other way is to remove this ...
		# TODO : Fore release mode : the etk folder are absolutly not at the same position in the tree ...
		myModule.CompileFlags_CC("-DMODE_RELEASE")
	
	
	myModule.AddExportPath(lutinTools.GetCurrentPath(__file__))
	
	
	# add the currrent module at the 
	return myModule


