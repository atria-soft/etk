#!/usr/bin/python
import realog.debug as debug
import lutin.tools as tools
import realog.debug as debug
import lutin.image as image
import os
import lutin.multiprocess as lutinMultiprocess


def get_type():
	return "LIBRARY_DYNAMIC"

def get_desc():
	return "Ewol Tool Kit"

def get_licence():
	return "MPL-2"

def get_compagny_type():
	return "org"

def get_compagny_name():
	return "atria-soft"

#def get_maintainer():
#	return "authors.txt"

#def get_version():
#	return "version.txt"

def configure(target, my_module):

	my_module.add_src_file([
	    'src/module-info.java',
	    'src/org/atriasoft/etk/Uri.java',
	    'src/org/atriasoft/etk/Distance.java',
	    'src/org/atriasoft/etk/ThreadAbstract.java',
	    'src/org/atriasoft/etk/ConfigFont.java',
	    'src/org/atriasoft/etk/math/Matrix3f.java',
	    'src/org/atriasoft/etk/math/Vector3f.java',
	    'src/org/atriasoft/etk/math/Constant.java',
	    'src/org/atriasoft/etk/math/Matrix2x3f.java',
	    'src/org/atriasoft/etk/math/Vector2b.java',
	    'src/org/atriasoft/etk/math/Quaternion.java',
	    'src/org/atriasoft/etk/math/Matrix4f.java',
	    'src/org/atriasoft/etk/math/Vector4f.java',
	    'src/org/atriasoft/etk/math/Vector2i.java',
	    'src/org/atriasoft/etk/math/Vector2f.java',
	    'src/org/atriasoft/etk/math/Vector3i.java',
	    'src/org/atriasoft/etk/math/Transform3D.java',
	    'src/org/atriasoft/etk/math/FMath.java',
	    'src/org/atriasoft/etk/util/Pair.java',
	    'src/org/atriasoft/etk/util/Dynamic.java',
	    'src/org/atriasoft/etk/util/ArraysTools.java',
	    'src/org/atriasoft/etk/internal/LOGGER.java',
	    'src/org/atriasoft/etk/theme/Theme.java',
	    'src/org/atriasoft/etk/Tools.java',
	    'src/org/atriasoft/etk/Configs.java',
	    'src/org/atriasoft/etk/Color.java',
	    'src/org/atriasoft/etk/Dimension1D.java',
	    'src/org/atriasoft/etk/Dimension.java',
	    ])
	my_module.add_path('src/', type='java')
	
	my_module.add_depend([
	    'io-scenarium-logger'
	    ])
	
	my_module.add_path([
	    'lib/spotbugs-annotations-4.2.2.jar'
	    ],
	    type='java',
	    export=True
	);
	my_module.add_flag('java', "RELEASE_15_PREVIEW");
	
	return True

