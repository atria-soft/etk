/** @file
 * @author Edouard DUPIN
 * @copyright 2011, Edouard DUPIN, all right reserved
 * @license MPL v2.0 (see license file)
 */


#include <etk/types.hpp>
#include <etk/os/FSNode.hpp>
#include <unistd.h>
#include <cstdlib>
#include <etk/tool.hpp>
#include <etk/debug.hpp>
#include <etk/Map.hpp>
#include <ethread/Mutex.hpp>
#ifdef __TARGET_OS__Windows
	#include <tchar.h>
	#include <iostream>
	#include <windows.h>
#endif
extern "C" {
	// file browsing ...
	#include <dirent.h>
	#include <sys/stat.h>
	#include <cerrno>
}

#ifdef HAVE_ZIP_DATA
#	include <etk/archive/Archive.hpp>
#endif


#ifdef __TARGET_OS__Windows
	// For ctime 
	#include <time.h>
#endif

#define TK_DBG_MODE TK_VERBOSE
//#define TK_DBG_MODE TK_DEBUG


#ifdef __TARGET_OS__Windows
	static etk::Vector<etk::String> getListDrive() {
		etk::Vector<etk::String> out;
		int dr_type=99;
		char dr_avail[4096];
		char *temp=dr_avail;
		/* 1st we fill the buffer */
		GetLogicalDriveStrings(4096,dr_avail);
		while(*temp != 0) {
			dr_type=GetDriveType(temp);
			etk::String driveName = etk::replace(etk::String(temp), '\\', '/');
			switch(dr_type) {
				case 0: // Unknown
					TK_WARNING("'" << driveName << "' : Unknown Drive type");
					break;
				case 1: // Invalid
					TK_WARNING("'" << driveName << "' : Drive is invalid");
					break;
				case 2: // Removable Drive
					TK_WARNING("'" << driveName << "' : Removable Drive type");
					out.pushBack(driveName);
					break;
				case 3: // Fixed
					TK_WARNING("'" << driveName << "' : Hard Disk (Fixed) Drive type");
					out.pushBack(driveName);
					break;
				case 4: // Remote
					TK_WARNING("'" << driveName << "' : Remote (Network) Drive type");
					out.pushBack(driveName);
					break;
				case 5: // CDROM
					TK_WARNING("'" << driveName << "' : CD-Rom/DVD-Rom Drive type");
					out.pushBack(driveName);
					break;
				case 6: // RamDrive
					TK_WARNING("'" << driveName << "' : Ram Drive type");
					out.pushBack(driveName);
					break;
			}
			temp += lstrlen(temp) + 1; // incriment the buffer
		}
		return out;
	}
#else
	static etk::Vector<etk::String> getListDrive() {
		etk::Vector<etk::String> out;
		return out;
	}
#endif

etk::String etk::simplifyPath(etk::String _input) {
	// step 1 : for windows change \ in /:
	TK_DEBUG("Simplify(1) : '" << _input << "'");
	size_t currentPos = 0;
	if (_input.size() == 0) {
		return _input;
	}
	while(currentPos < _input.size()) {
		if (_input[currentPos] == '\\') {
			_input[currentPos] = '/';
		}
		currentPos++;
		continue;
	}
	// step 2 : remove all '//'
	TK_DBG_MODE("Simplify(2) : '" << _input << "'");
	currentPos = 0;
	if (_input.size() <= 1) {
		return _input;
	}
	while(currentPos < _input.size()-1) {
		if (    _input[currentPos] != '/'
		     || _input[currentPos+1] != '/') {
			currentPos++;
			continue;
		}
		_input.erase(currentPos, 1);
	}
	// step 3 : remove all '/./'
	TK_DBG_MODE("Simplify(3) : '" << _input << "'");
	currentPos = 0;
	if (_input.size() <= 1) {
		return _input;
	}
	while(    currentPos < _input.size()-2
	       && _input.size() > 2) {
		if (    _input[currentPos] != '/'
		     || _input[currentPos+1] != '.'
		     || _input[currentPos+2] != '/') {
			currentPos++;
			continue;
		}
		_input.erase(currentPos, 2);
	}
	if (end_with(_input, "/.") == true) {
		_input.erase(_input.size()-1, 1);
	}
	// step 4 remove xxx/..
	TK_DBG_MODE("Simplify(4) : '" << _input << "'");
	size_t lastSlashPos = etk::String::npos;
	currentPos = 0;
	while(    currentPos < _input.size()-2
	       && _input.size() > 2) {
		if (    _input[currentPos] != '/'
		     || _input[currentPos+1] != '.'
		     || _input[currentPos+2] != '.') {
			if (_input[currentPos] == '/') {
				lastSlashPos = currentPos;
			}
			currentPos++;
			continue;
		}
		if (lastSlashPos == etk::String::npos) {
			currentPos++;
			continue;
		}
		_input.erase(lastSlashPos, currentPos+2-lastSlashPos+1);
		TK_DEBUG("update : '" << _input << "'");
		lastSlashPos = etk::String::npos;
		currentPos = 0;
	}
	TK_DBG_MODE("Simplify(5) : '" << _input << "'");
	if (_input.size() == 0) {
		_input = "/";
	}
	#ifdef __TARGET_OS__Windows
		etk::String base;
		base += _input[0];
		base += ":";
		if (    _input == base + "/../"
		     || _input == base + "/.."
		     || _input == base + "/./"
		     || _input == base + "/."
		     || _input == "/") {
			_input = base + "/";
		}
	#else
		if (    _input == "/../"
		     || _input == "/.."
		     || _input == "/./"
		     || _input == "/.") {
			_input = "/";
		}
	#endif
	
	TK_DEBUG("Simplify(end) : '" << _input << "'");
	return _input;
}
static int32_t FSNODE_LOCAL_mkdir(const char* _path, mode_t _mode) {
	struct stat st;
	int32_t status = 0;
	if (stat(_path, &st) != 0) {
		/* Directory does not exist. EEXIST for race condition */
		#ifdef __TARGET_OS__Windows
		if(0!=mkdir(_path)
		#else
		if(0!=mkdir(_path, _mode)
		#endif
		    && errno != EEXIST) {
			status = -1;
		}
	} else if (!S_ISDIR(st.st_mode)) {
		errno = ENOTDIR;
		status = -1;
	}
	
	return(status);
}

static int32_t FSNODE_LOCAL_mkPath(const char* _path, mode_t _mode) {
	char *pp;
	char *sp;
	int status;
	char *copypath = strdup(_path);
	if (copypath == nullptr) {
		return -1;
	}
	status = 0;
	pp = copypath;
	while (status == 0 && (sp = strchr(pp, '/')) != 0) {
		if (sp != pp) {
			/* Neither root nor double slash in path */
			*sp = '\0';
			status = FSNODE_LOCAL_mkdir(copypath, _mode);
			*sp = '/';
		}
		pp = sp + 1;
	}
	if (status == 0) {
		status = FSNODE_LOCAL_mkdir(_path, _mode);
	}
	free(copypath);
	return (status);
}

static bool FSNODE_LOCAL_exist(const etk::String& _path) {
	struct stat st;
	int32_t status = 0;
	if (stat(_path.c_str(), &st) != 0) {
		return false;
	}
	return true;
}
static bool FSNODE_LOCAL_isDirectory(const etk::String& _path) {
	struct stat st;
	int32_t status = 0;
	if (stat(_path.c_str(), &st) != 0) {
		return false;
	} else if (!S_ISDIR(st.st_mode)) {
		return false;
	}
	return true;
}
static bool FSNODE_LOCAL_isFile(const etk::String& _path) {
	struct stat st;
	int32_t status = 0;
	if (stat(_path.c_str(), &st) != 0) {
		return false;
	} else if (!S_ISREG(st.st_mode)) {
		return false;
	}
	return true;
}
static etk::String FSNODE_LOCAL_join(const etk::String& _path1, const etk::String& _path2) {
	etk::String out = etk::replace(_path1, '\\', '/');
	if (out.size() == 0) {
		out = etk::replace(_path2, '\\', '/');;
		return out;
	}
	if (_path2.size() == 0) {
		return out;
	}
	if (out[out.size()-1] != '/') {
		out += "/";
	}
	out += etk::replace(_path2, '\\', '/');;
	return out;
}


static ethread::Mutex& getNodeMutex() {
	static ethread::Mutex g_nodeMutex;
	return g_nodeMutex;
}

// zip file of the apk file for Android ==> set to zip file apk access
static etk::String s_fileAPK = "";
static etk::String baseApplName = "ewolNoName";
static etk::String baseApplPath = "ewolNoName";
static etk::String baseRunPath = "/";
static etk::String baseRunPathInHome = "/";
#if defined(__TARGET_OS__Android)
	static etk::String baseFolderHome     = "/sdcard/";                 // home folder
	static etk::String baseFolderData     = "assets/";                  // program Data
	static etk::String baseFolderDataUser = "/sdcard/.tmp/userData/";   // Data specific user (local modification)
	static etk::String baseFolderCache    = "/sdcard/.tmp/cache/";      // Temporary data (can be removed the next time)
#elif defined(__TARGET_OS__Windows)
	static etk::String baseFolderHome     = "c:/test";                  // home folder
	static etk::String baseFolderData     = "c:/test/share/";           // program Data
	static etk::String baseFolderDataUser = "c:/test/userData/";        // Data specific user (local modification)
	static etk::String baseFolderCache    = "c:/Windows/Temp/ewol/";    // Temporary data (can be removed the next time)
#else
	static etk::String baseFolderHome     = "~";                  // home folder
	static etk::String baseFolderData     = "share/";             // program Data
	static etk::String baseFolderDataUser = "~/.tmp/userData/";   // Data specific user (local modification)
	static etk::String baseFolderCache    = "~/.tmp/cache/";      // Temporary data (can be removed the next time)
#endif

etk::String etk::FSNodeGetApplicationName() {
	return baseApplName;
}

etk::String etk::FSNodeGetApplicationPath() {
	return baseApplPath;
}

etk::String etk::FSNodeGetHomePath() {
	return baseFolderHome;
}

#ifdef HAVE_ZIP_DATA
	static etk::Archive* s_APKArchive = nullptr;
	static void loadAPK(const etk::String& _apkPath) {
		#ifdef __TARGET_OS__Android
			ethread::UniqueLock lock(getNodeMutex());
			TK_INFO("Loading APK '" << _apkPath << "'");
			s_APKArchive = etk::Archive::load(_apkPath);
			TK_ASSERT(s_APKArchive != nullptr, "Error loading APK ...  '" << _apkPath << "'");
		#else
			TK_INFO("Loading Intarnal data '" << _apkPath << "'");
			//s_APKArchive = etk::Archive::loadPackage(_apkPath);
			s_APKArchive = etk::Archive::load(_apkPath);
			/*
			FILE* tmp = fopen(_apkPath.c_str(), "r");
			if (tmp == nullptr) {
				TK_ERROR("File does not exist ...");
			} else {
				TK_ERROR("File open OK ...");
			}
			*/
			TK_ASSERT(s_APKArchive != nullptr, "Error loading PKG ...  '" << _apkPath << "'");
		#endif
		#ifdef DEBUG
			//Just for debug, print APK contents
			s_APKArchive->display();
		#endif
	}
	#ifdef __TARGET_OS__Windows
		static void loadAPKBin(const etk::String& _apkPath) {
			TK_ERROR("Loading Intarnal data '" << _apkPath << "'");
			s_APKArchive = etk::Archive::loadPackage(_apkPath);
			TK_ASSERT(s_APKArchive != nullptr, "Error loading PKG ...  '" << _apkPath << "'");
			#ifdef DEBUG
				//Just for debug, print APK contents
				s_APKArchive->display();
			#endif
		}
	#endif
#endif

// for specific device contraint : 
void etk::setBaseFolderData(const char* _folder, const char* _applName) {
	#ifdef __TARGET_OS__Android
		{
			ethread::UniqueLock lock(getNodeMutex());
			baseFolderData = "assets/";
			if (_applName != nullptr) {
				baseFolderData += _applName;
				baseFolderData += "/";
			}
			s_fileAPK = _folder;
		}
		TK_INFO("baseFolderData     : '" << baseFolderData << "'");
		loadAPK(s_fileAPK);
	#else
		TK_WARNING("Not Availlable Outside Android");
	#endif
}

void etk::setBaseFolderDataUser(const char* _folder) {
	ethread::UniqueLock lock(getNodeMutex());
	#ifdef __TARGET_OS__Android
		baseFolderDataUser = _folder;
		TK_INFO("baseFolderDataUser : '" << baseFolderDataUser << "'");
	#else
		TK_WARNING("Not Availlable Outside Android");
	#endif
}

void etk::setBaseFolderCache(const char* _folder) {
	ethread::UniqueLock lock(getNodeMutex());
	#ifdef __TARGET_OS__Android
		baseFolderCache = _folder;
		TK_INFO("baseFolderCache    : '" << baseFolderCache << "'");
	#else
		TK_WARNING("Not Availlable Outside Android");
	#endif
}

etk::String l_argZero = "";
void etk::setArgZero(const etk::String& _val) {
	ethread::UniqueLock lock(getNodeMutex());
	l_argZero = _val;
	// set defaiult application name ...
	etk::Vector<etk::String> elems = etk::split(_val, '/');
	etk::initDefaultFolder(elems[elems.size()-1].c_str());
}
/*
	On Unixes with /proc really straight and realiable way is to:
		readlink("/proc/self/exe", buf, bufsize) (Linux)
		readlink("/proc/curproc/file", buf, bufsize) (FreeBSD)
		readlink("/proc/self/path/a.out", buf, bufsize) (Solaris)
	On Unixes without /proc (i.e. if above fails):
		If argv[0] starts with "/" (absolute path) this is the path.
		Otherwise if argv[0] contains "/" (relative path) append it to cwd (assuming it hasn't been changed yet).
		Otherwise search directories in $PATH for executable argv[0].
	Afterwards it may be reasonable to check whether the executable isn't actually a symlink. If it is resolve it relative to the symlink directory.
	This step is not necessary in /proc method (at least for Linux). There the proc symlink points directly to executable.
	Note that it is up to the calling process to set argv[0] correctly. It is right most of the times however there are occasions when the calling process cannot be trusted (ex. setuid executable).
	On Windows: use GetModuleFileName(nullptr, buf, bufsize)
*/
etk::String getApplicationPath() {
	etk::String binaryName = "no-name";
	char binaryCompleatePath[FILENAME_MAX];
	memset(binaryCompleatePath, 0, FILENAME_MAX);
	#ifdef __TARGET_OS__Windows
		GetModuleFileName(nullptr, binaryCompleatePath, FILENAME_MAX);
		if (strlen(binaryCompleatePath) == 0) {
			TK_CRITICAL("Can not get the binary position in the tree ==> this is really bad ...");
		} else {
			binaryName = binaryCompleatePath;
		}
	#else
		// check it to prevent test mode in local folder ...
		// Generic Linux system
		readlink("/proc/self/exe", binaryCompleatePath, FILENAME_MAX);
		if(strlen(binaryCompleatePath) != 0) {
			binaryName = binaryCompleatePath;
			return binaryName;
		}
		// generic FreeBSD system
		memset(binaryCompleatePath, 0, FILENAME_MAX);
		readlink("/proc/curproc/file", binaryCompleatePath, FILENAME_MAX);
		if(strlen(binaryCompleatePath) != 0) {
			binaryName = binaryCompleatePath;
			return binaryName;
		}
		// generic Solaris system 
		memset(binaryCompleatePath, 0, FILENAME_MAX);
		readlink("/proc/self/path/a.out", binaryCompleatePath, FILENAME_MAX);
		if(strlen(binaryCompleatePath) != 0) {
			binaryName = binaryCompleatePath;
			return binaryName;
		}
		// now we are in a really bad case ...
		if (l_argZero.size() == 0) {
			TK_CRITICAL("Can not get the binary position in the tree ==> this is really bad ... arg 0 is as bad as other ...");
			return binaryName;
		}
		TK_VERBOSE("Parse arg0 = '" << l_argZero << "' start with '/' ???");
		if (l_argZero[0] == '/') {
			binaryName = l_argZero;
			return etk::simplifyPath(binaryName);
		}
		TK_VERBOSE("Parse arg0 = '" << l_argZero << "' try add PWD");
		char * basicPathPWD = getenv("PWD");
		if (nullptr != basicPathPWD) {
			etk::String testCompleatePath = basicPathPWD;
			testCompleatePath += "/";
			testCompleatePath += l_argZero;
			// check if the element existed : 
			TK_VERBOSE("test path: '" << testCompleatePath << "'");
			memset(binaryCompleatePath, 0, FILENAME_MAX);
			struct stat statProperty;
			if (-1 != stat(testCompleatePath.c_str(), &statProperty)) {
				//Normal case when the file does not exist ... ==> the it was in unknow mode ...
				binaryName = testCompleatePath;
				TK_VERBOSE("find real name = '" << binaryName << "'");
				return etk::simplifyPath(binaryName);
			}
		}
		//char * basicPathPATH = getenv("PATH");
		//if (nullptr != basicPathPWD) {
			// TODO : bad case ...
		//}
		// and now we will really in a bad mood ...
	#endif
	TK_INFO("Binary name : " << binaryName);
	return binaryName;
}

void etk::initDefaultFolder(const char* _applName) {
	baseApplName = _applName;
	char cCurrentPath[FILENAME_MAX];
	char * basicPath = getenv("HOME");
	if (nullptr == basicPath) {
		TK_WARNING("ERROR while trying to get the path of the home folder");
		#if defined(__TARGET_OS__Windows)
			baseFolderHome = "c:/";
		#elif defined(__TARGET_OS__Android)
			baseFolderHome = "/sdcard";
		#else
			baseFolderHome = "~";
		#endif
	} else {
		baseFolderHome = basicPath;
	}
	if (!getcwd(cCurrentPath, FILENAME_MAX)) {
		baseRunPath = ".";
		baseRunPathInHome = ".";
	} else {
		cCurrentPath[FILENAME_MAX - 1] = '\0';
		if (cCurrentPath[0] == '/') {
			baseRunPath = cCurrentPath;
		} else {
			baseRunPath = etk::String("/") + cCurrentPath;
		}
		if (start_with(baseRunPath, baseFolderHome) == true) {
			baseRunPathInHome = etk::String(baseRunPath, baseFolderHome.size());
		} else {
			baseRunPathInHome = baseRunPath;
		}
	}
	TK_DBG_MODE("Find Basic running PATH : '" << baseRunPath << "'");
	#if defined(__TARGET_OS__Web)
		loadAPK("data.zip");
		baseFolderData = "zz_generic_zz/";
	#endif
	#if    !defined(__TARGET_OS__Android) \
	    && !defined(__TARGET_OS__Web)
		etk::String binaryPath = getApplicationPath();
		binaryPath = etk::replace(binaryPath, '\\', '/');
		size_t pos = binaryPath.rfind('/');
		etk::String binaryName(binaryPath, pos);
		while(    binaryName.size() >= 2
		       && binaryName[1] == '/') {
			binaryName = etk::String(binaryName.begin()+1, binaryName.end());
		}
		binaryPath.erase(binaryPath.begin() + pos, binaryPath.end());
		baseApplPath = binaryPath;
		TK_INFO("Bianry name : '" << binaryPath << "' && '" << binaryName << "'" );
		#ifdef __TARGET_OS__Windows
			// check if we have a data path just near the .exe file
			if (    FSNODE_LOCAL_exist(FSNODE_LOCAL_join(binaryPath,"data")) == true
			     && FSNODE_LOCAL_isDirectory(FSNODE_LOCAL_join(binaryPath,"data")) == true) {
				TK_INFO("Find data in external 'data' path");
				baseFolderData  = binaryPath;
				baseFolderData += "/data/";
			}
			#ifdef HAVE_ZIP_DATA
				// check if we have a data.zip just near the .exe file
				else if (    FSNODE_LOCAL_exist(FSNODE_LOCAL_join(binaryPath,"data.zip")) == true
				     && FSNODE_LOCAL_isFile(FSNODE_LOCAL_join(binaryPath,"data.zip")) == true) {
					TK_INFO("Find data in external data.zip file");
					loadAPK(FSNODE_LOCAL_join(binaryPath,"data.zip"));
					baseFolderData = "";
				}
				// check if the application named .pkg.exe (this mean the data is inside the executable as a zip next the binary)
				else if (etk::end_with(binaryName, ".pkg.exe") == true) {
					TK_INFO("Find data in external internal exe package");
					loadAPKBin(FSNODE_LOCAL_join(binaryPath,binaryName));
					baseFolderData = "";
				}
			#endif
			baseFolderData += etk::String(binaryName.begin()+1, binaryName.end()-4);
			baseFolderData += "/";
			
			baseFolderDataUser  = binaryPath;
			baseFolderDataUser += "/user/";
			
			baseFolderCache  = binaryPath;
			baseFolderCache += "/tmp/";
		#else
			// if element is installed :
			baseFolderData  = "/usr/share";
			baseFolderData += binaryName;
			baseFolderData += "/";
			
			etk::String theoricInstalledName = "/usr/bin";
			theoricInstalledName += binaryName;
			TK_DEBUG(" position : '" << binaryPath << "' installed position : '" << theoricInstalledName << "'");
			if (binaryPath != theoricInstalledName) {
				// can also be in application package:
				etk::String endWith = binaryName + ".app/bin";
				if (etk::end_with(binaryPath, endWith) == true) {
					TK_INFO("Application istall in user standalone mode: '" << binaryPath << "'");
				} else {
					TK_INFO(" base path is not correct try to find it : (must only appear in test and not when installed) base name : '" << binaryPath << "'");
				}
				// remove bin/applName
				baseFolderData = binaryPath;
				#if defined(__TARGET_OS__MacOs)
					baseFolderData += "/../Resources/";
				#elif defined(__TARGET_OS__IOs)
					baseFolderData += "/share/";
				#else
					baseFolderData += "/../share/";
				#endif
				baseFolderData += binaryName;
				baseFolderData += "/";
				baseFolderData = simplifyPath(baseFolderData);
				#if    defined(__TARGET_OS__Linux) \
				    || defined(__TARGET_OS__buildroot)
					{
						struct stat statProperty;
						if (-1 == stat(baseFolderData.c_str(), &statProperty)) {
							//Normal case when the file does not exist ... ==> the it was in unknow mode ...
							TK_INFO("Path does not exit : '" << baseFolderData << "' ==> try tools data folder ...");
							baseFolderData += "/../../share/";
							baseFolderData += binaryName;
							baseFolderData += "/";
							baseFolderData = simplifyPath(baseFolderData);
						}
					}
				#endif
			}
			#if defined(__TARGET_OS__IOs)
				baseFolderDataUser  = binaryPath;
				baseFolderDataUser += "/../Documents/";
				baseFolderDataUser = simplifyPath(baseFolderDataUser);
			#else
				baseFolderDataUser  = baseFolderHome;
				baseFolderDataUser += "/.local/share/";
				baseFolderDataUser += binaryName;
				baseFolderDataUser += "/";
			#endif
			#if defined(__TARGET_OS__IOs)
				baseFolderCache  = binaryPath;
				baseFolderCache += "/../tmp/";
				baseFolderCache = simplifyPath(baseFolderCache);
			#else
				baseFolderCache  = "/tmp/";
				baseFolderCache += binaryName;
				baseFolderCache += "/";
			#endif
		#endif
	#endif
	TK_INFO("baseRunPath        : '" << baseRunPath << "'");
	TK_INFO("baseRunPathInHome  : ~|HOME: + '" << baseRunPathInHome << "'");
	TK_INFO("baseFolderHome     : '" << baseFolderHome << "'");
	TK_INFO("baseFolderData     : '" << baseFolderData << "'");
	TK_INFO("baseFolderDataUser : '" << baseFolderDataUser << "'");
	TK_INFO("baseFolderCache    : '" << baseFolderCache << "'");
}

etk::String etk::getUserHomeFolder() {
	return baseFolderHome;
}

etk::String etk::getUserRunFolder() {
	return baseRunPath;
}


#ifdef HAVE_ZIP_DATA
bool etk::FSNode::loadDataZip() {
	ethread::UniqueLock lock(getNodeMutex());
	if (s_APKArchive == nullptr) {
		return false;
	}
	if (m_zipContent != nullptr) {
		return true;
	}
	if (false == s_APKArchive->exist(m_systemFileName)) {
		return false;
	}
	m_zipContent = &s_APKArchive->getContent(m_systemFileName);
	if (m_zipContent != nullptr) {
		return true;
	}
	return false;
}
#endif


etk::FSNode::FSNode(const etk::String& _nodeName) :
  m_userFileName(""),
  m_type(etk::FSNType_unknow),
  m_typeNode(etk::typeNode_unknow),
  m_PointerFile(nullptr),
  m_timeCreate(0),
  m_timeModify(0),
  m_timeAccess(0)
#ifdef HAVE_ZIP_DATA
    , m_zipContent(nullptr),
    m_zipReadingOffset(-1)
#endif
{
	privateSetName(_nodeName);
}


etk::FSNode::~FSNode() {
	if(    nullptr != m_PointerFile
	#ifdef HAVE_ZIP_DATA
	    || nullptr != m_zipContent
	#endif
	  ) {
		TK_ERROR("Missing to close the file : \"" << *this << "\"");
		fileClose();
	}
}


void etk::FSNode::sortElementList(etk::Vector<etk::FSNode *>& _list) {
	etk::Vector<etk::FSNode *> tmpList = _list;
	_list.clear();
	for(size_t iii=0; iii<tmpList.size(); iii++) {
		if (nullptr != tmpList[iii]) {
			size_t findPos = 0;
			for(size_t jjj=0; jjj<_list.size(); jjj++) {
				//EWOL_DEBUG("compare : \""<<*tmpList[iii] << "\" and \"" << *m_listDirectory[jjj] << "\"");
				if (_list[jjj]!=nullptr) {
					// TODO : Do something better : this methode is a litthe hard!!!
					if (etk::toupper(tmpList[iii]->getNameFile()) > etk::toupper(_list[jjj]->getNameFile())) {
						findPos = jjj+1;
					}
				}
			}
			//EWOL_DEBUG("position="<<findPos);
			_list.insert(_list.begin() + findPos, tmpList[iii]);
		}
	}
}

void etk::FSNode::privateSetName(etk::String _newName) {
	if(    nullptr != m_PointerFile
	#ifdef HAVE_ZIP_DATA
	    || nullptr != m_zipContent
	#endif
	  ) {
		TK_ERROR("Missing to close the file : '" << *this << "'");
		fileClose();
	}
	// set right at nullptr ...
	m_rights = 0;
	
	m_libSearch = "";
	if (    _newName.size() > 0
	     && _newName[0] == '{') {
		// special case: Reference of searching in subLib folder ==> library use-case
		size_t firstPos = _newName.find('}');
		if (firstPos != etk::String::npos) {
			// we find a theme name : We extracted it :
			m_libSearch = etk::String(_newName, 1, firstPos-1);
			_newName = etk::String(_newName, firstPos+1);
		} else {
			TK_ERROR("start a path name with '{' without '}' : " << _newName);
			// remove in case the {
			_newName = etk::String(_newName, 1);
		}
	}
	
	#ifdef HAVE_ZIP_DATA
		m_zipContent = nullptr;
		m_zipReadingOffset = 0;
	#endif
	// Reset ALL DATA :
	m_userFileName = "";
	m_type = etk::FSNType_unknow;
	TK_DBG_MODE("1 : Set Name :              \"" << _newName << "\"");
	
	// generate destination name in case of the input error
	etk::String destFilename;
	if (_newName.size() == 0) {
		// if no name ==> go to the root Folder
		destFilename = "ROOT:";
	} else {
		destFilename = _newName;
	}
	
	bool isRootFolder = false;
	#ifdef __TARGET_OS__Windows
		for (char iii='a' ; iii<='z' ; iii++) {
			char tmpVal[10];
			char tmpValMaj[10];
			sprintf(tmpVal, "%c:/", iii);
			sprintf(tmpValMaj, "%c:/", iii+'A'-'a');
			if(    etk::start_with(destFilename, tmpVal) == true
			    || etk::start_with(destFilename, tmpValMaj) == true) {
				isRootFolder = true;
				break;
			}
		}
	#else
		isRootFolder = destFilename[0] == '/';
	#endif
	if(    start_with(destFilename, "ROOT:") == true
	    || start_with(destFilename, "root:") == true ) {
		TK_DBG_MODE("    ==> detect root 2 ");
		destFilename.erase(0, 5);
		m_type = etk::FSNType_direct;
		if(start_with(destFilename, "~") == true) {
			destFilename.erase(0, 1);
			m_type = etk::FSNType_home;
		}
	} else if(    start_with(destFilename, "DIRECT:") == true
	           || start_with(destFilename, "direct:") == true ) {
		TK_DBG_MODE("    ==> detect direct");
		destFilename.erase(0, 7);
		m_type = etk::FSNType_direct;
		if(start_with(destFilename, "~") == true) {
			destFilename.erase(0, 1);
			m_type = etk::FSNType_home;
		}
	} else if(    start_with(destFilename, "DATA:") == true
	           || start_with(destFilename, "data:") == true ) {
		TK_DBG_MODE("    ==> detect data");
		destFilename.erase(0, 5);
		m_type = etk::FSNType_data;
	} else if(    start_with(destFilename, "USERDATA:") == true
	           || start_with(destFilename, "userdata:") == true ) {
		TK_DBG_MODE("    ==> detect User-data");
		destFilename.erase(0, 9);
		m_type = etk::FSNType_userData;
	} else if(    start_with(destFilename, "CACHE:") == true
	           || start_with(destFilename, "cache:") == true ) {
		TK_DBG_MODE("    ==> detect Cache");
		destFilename.erase(0, 6);
		m_type = etk::FSNType_cache;
	} else if(    start_with(destFilename, "THEME:") == true
	           || start_with(destFilename, "theme:") == true ) {
		TK_DBG_MODE("    ==> detect theme");
		destFilename.erase(0, 6);
		m_type = etk::FSNType_theme;
	} else if(start_with(destFilename, "./") == true) {
		TK_DBG_MODE("    ==> detect relatif 1");
		destFilename.erase(0, 2);
		while (destFilename.size()>0 && destFilename[0] == '/') {
			destFilename.erase(0, 1);
		}
		m_type = etk::FSNType_relatif;
	} else if(    start_with(destFilename, "REL:") == true
	           || start_with(destFilename, "rel:") == true ) {
		TK_DBG_MODE("    ==> detect relatif 2");
		destFilename.erase(0, 4);
		while (destFilename.size()>0 && destFilename[0] == '/') {
			destFilename.erase(0, 1);
		}
		m_type = etk::FSNType_relatif;
	} else if(start_with(destFilename, baseRunPath) == true) {
		TK_DBG_MODE("    ==> detect relatif 3 (run path=" << baseRunPath << ")");
		destFilename.erase(0, baseRunPath.size());
		while (destFilename.size()>0 && destFilename[0] == '/') {
			destFilename.erase(0, 1);
		}
		m_type = etk::FSNType_relatif;
	} else if ((    baseRunPath != baseRunPathInHome
	             && (    start_with(destFilename, "~" + baseRunPathInHome) == true
	                  || start_with(destFilename, "HOME:" + baseRunPathInHome) == true
	                  || start_with(destFilename, "home:" + baseRunPathInHome) == true ) ) ) {
		TK_DBG_MODE("    ==> detect relatif 4");
		if (start_with(destFilename, "~" + baseRunPathInHome) == true) {
			destFilename.erase(0, 1);
		} else {
			destFilename.erase(0, 5);
		}
		destFilename.erase(0, baseRunPathInHome.size());
		while (destFilename.size()>0 && destFilename[0] == '/') {
			destFilename.erase(0, 1);
		}
		m_type = etk::FSNType_relatif;
	} else if(start_with(destFilename, "~")) {
		TK_DBG_MODE("    ==> detect home 2");
		destFilename.erase(0, 1);
		m_type = etk::FSNType_home;
	} else if(    start_with(destFilename, "HOME:") == true
	           || start_with(destFilename, "home:") == true ) {
		TK_DBG_MODE("    ==> detect home 3");
		destFilename.erase(0, 5);
		m_type = etk::FSNType_home;
		if(start_with(destFilename, "~") == true) {
			destFilename.erase(0, 1);
		}
	} else if (start_with(destFilename, baseFolderHome) == true) {
		TK_DBG_MODE("    ==> detect home");
		destFilename.erase(0, baseFolderHome.size());
		m_type = etk::FSNType_home;
	} else if(isRootFolder == true) {
		TK_DBG_MODE("    ==> detect root");
		#ifdef __TARGET_OS__Windows
			destFilename.erase(0, 3);
		#else
			destFilename.erase(0, 1);
		#endif
		m_type = etk::FSNType_direct;
	} else {
		TK_DBG_MODE("    ==> detect other");
		// nothing to remove
		//Other type is Relative : 
		m_type = etk::FSNType_relatif;
	}
	m_userFileName = destFilename;
	TK_DBG_MODE("3 : parse done :            [" << m_type << "]->\"" << m_userFileName << "\"");
	
	// Now we reduce the path with all un-needed ../ and other thinks ...
	// TODO : Do it whith link and the other sub thinks ...
	m_userFileName = simplifyPath(m_userFileName);
	TK_DBG_MODE("4 : Path simplification :   [" << m_type << "]->\"" << m_userFileName << "\"");
	
	// Now we generate the real FS path:
	generateFileSystemPath();
	TK_DBG_MODE("5 : file System Real name : \"" << m_systemFileName << "\"");
	
	// now we get all the right if the file existed:
	updateFileSystemProperty();
	TK_DBG_MODE("6 : type :                  [" << m_typeNode << "]  right :" << m_rights);
}

bool directCheckFile(etk::String _tmpFileNameDirect, bool _checkInAPKIfNeeded = false) {
	#ifdef HAVE_ZIP_DATA
	if (true == _checkInAPKIfNeeded) {
		if(    nullptr != s_APKArchive
		    && true == s_APKArchive->exist(_tmpFileNameDirect) ) {
			return true;
		}
		return false;
	}
	#endif
	// tmpStat Buffer :
	struct stat statProperty;
	if (-1 == stat(_tmpFileNameDirect.c_str(), &statProperty)) {
		return false;
	}
	return true;
}
// Now we generate the real FS path:
void etk::FSNode::generateFileSystemPath() {
	bool forceLibFolder(false);
	if (    m_libSearch.size() > 0
	     && m_libSearch[0] == '@') {
		forceLibFolder = true;
	}
	switch (m_type) {
		default:
		case etk::FSNType_unknow:
			m_systemFileName = baseFolderHome;
			break;
		case etk::FSNType_direct:
			m_systemFileName = "/";
			break;
		case etk::FSNType_relatif: {
				// Get the command came from the running of the program : 
				char cCurrentPath[FILENAME_MAX];
				if (!getcwd(cCurrentPath, FILENAME_MAX)) {
					TK_WARNING("Can not get the curent path");
					cCurrentPath[0] = '/';
					cCurrentPath[1] = '\0';
				}
				cCurrentPath[FILENAME_MAX - 1] = '\0';
				m_systemFileName = cCurrentPath;
				//m_systemFileName += "/";
			break;
		}
		case etk::FSNType_home:
			m_systemFileName = baseFolderHome;
			break;
		case etk::FSNType_data:
			{
				TK_DBG_MODE("DATA lib : '" << m_libSearch << "' => '" << m_userFileName << "' forceLib = " << forceLibFolder);
				// search the correct folder:
				if (forceLibFolder == false) {
					// check in the application folder.
					TK_DBG_MODE("DATA Search in application data:");
					m_systemFileName = simplifyPath(baseFolderData + "/" + m_userFileName);
					if (directCheckFile(m_systemFileName, true) == true) {
						return;
					}
					if (m_libSearch.size() == 0) {
						return;
					}
				}
				m_systemFileName = simplifyPath(baseFolderData + "/../" + m_libSearch + "/" + m_userFileName);
				return;
			}
			break;
		case etk::FSNType_userData:
			m_systemFileName = baseFolderDataUser;
			break;
		case etk::FSNType_cache:
			m_systemFileName = baseFolderCache;
			break;
		case etk::FSNType_theme:
		case etk::FSNType_themeData:
			{
				etk::String themeName("");
				etk::String basicName(m_userFileName);
				size_t firstPos = m_userFileName.find(':');
				if (firstPos != etk::String::npos) {
					// we find a theme name : We extracted it :
					themeName = etk::String(m_userFileName, 0, firstPos);
					basicName = etk::String(m_userFileName, firstPos+1);
				}
				TK_DBG_MODE(" THEME party : \"" << themeName << "\" => \"" << basicName << "\"");
				themeName = etk::theme::getName(themeName);
				etk::String themeNameDefault = etk::theme::getNameDefault(themeName);
				TK_DBG_MODE("      ==> theme Folder \"" << themeName << "\"");
				// search the correct folder : 
				if (themeName == "") {
					TK_WARNING("no theme name detected : set it to '" << themeNameDefault << "'");
				} else if (themeName != themeNameDefault) {
					if (forceLibFolder == false) {
						// Selected theme :
						// check in the user data :
						m_systemFileName = simplifyPath(baseFolderDataUser + "theme/" + themeName + "/" + basicName);
						if (directCheckFile(m_systemFileName) == true) {
							return;
						}
						// check in the Appl data :
						m_systemFileName = simplifyPath(baseFolderData + "theme/" + themeName + "/" + basicName);
						if (directCheckFile(m_systemFileName, true) == true) {
							m_type = etk::FSNType_themeData;
							return;
						}
					}
					if (m_libSearch.size() > 0) {
						// Selected theme :
						// check in the user data :
						m_systemFileName = simplifyPath(baseFolderDataUser + "/../" + m_libSearch + "/theme/" + themeName + "/" + basicName);
						if (directCheckFile(m_systemFileName) == true) {
							return;
						}
						// check in the Appl data :
						m_systemFileName = simplifyPath(baseFolderData + "/../" + m_libSearch + "/theme/" + themeName + "/" + basicName);
						if (directCheckFile(m_systemFileName, true) == true) {
							m_type = etk::FSNType_themeData;
							return;
						}
					}
				}
				// default theme :
				if (forceLibFolder == false) {
					// check in the user data :
					m_systemFileName = simplifyPath(baseFolderDataUser + "theme/" + themeNameDefault + "/" + basicName);
					if (true==directCheckFile(m_systemFileName)) {
						return;
					}
					// check in the Appl data : In every case we return this one ...
					m_systemFileName = simplifyPath(baseFolderData + "theme/" + themeNameDefault + "/" + basicName);
					if (true==directCheckFile(m_systemFileName, true)) {
						m_type = etk::FSNType_themeData;
						return;
					}
					if (m_libSearch.size() == 0) {
						m_type = etk::FSNType_unknow;
						return;
					}
				}
				// check in the user data :
				m_systemFileName = simplifyPath(baseFolderDataUser + "/../" + m_libSearch + "/theme/" + themeNameDefault + "/" + basicName);
				if (directCheckFile(m_systemFileName) == true) {
					return;
				}
				// check in the Appl data : In every case we return this one ...
				m_systemFileName = simplifyPath(baseFolderData + "/../" + m_libSearch + "/theme/" + themeNameDefault + "/" + basicName);
				if (directCheckFile(m_systemFileName, true) == true) {
					m_type = etk::FSNType_themeData;
					return;
				}
				m_type = etk::FSNType_unknow;
				return;
			}
			break;
	}
	if (m_userFileName != "") {
		if (    m_systemFileName.size()>0
		     && m_systemFileName[m_systemFileName.size()-1] != '/') {
			m_systemFileName += '/';
		}
		m_systemFileName += m_userFileName;
	}
}


// now we get all the right if the file existed:
void etk::FSNode::updateFileSystemProperty() {
	// clean general properties : 
	m_rights.clear();
	m_timeCreate = 0;
	m_timeModify = 0;
	m_timeAccess = 0;
	m_idOwner = 0;
	m_idGroup = 0;
	// File type is not knowns ...
	m_typeNode=typeNode_unknow;
	
	#ifdef HAVE_ZIP_DATA
	if(    m_type == etk::FSNType_data
	    || m_type == etk::FSNType_themeData) {
		// ----------------------------------------
		// = Check if it was a folder :           =
		// ----------------------------------------
		etk::String folderName = "/";
		if (etk::end_with(m_systemFileName, folderName) == true) {
			folderName = m_systemFileName;
		} else {
			folderName = m_systemFileName + "/";
		}
		// note : Zip does not support other think than file ...
		if (s_APKArchive == nullptr) {
			TK_ERROR("NOT Find the File in APK : '" << m_systemFileName << "'");
			return;
		}
		if (s_APKArchive->exist(m_systemFileName) == true) {
			m_typeNode=typeNode_file;
			m_rights.setUserReadable(true);
			TK_DBG_MODE("Find a File in APK : '" << m_systemFileName << "'");
			return;
		}
		// TODO : Set the time of the file (time program compilation)
		// TODO : Set the USER ID in the group and the user Id ...
		if (m_systemFileName[m_systemFileName.size()-1] == '/') {
			//Might be a folder ==> check if it existed in the start files ...
			for (int iii=0; iii<s_APKArchive->size(); iii++) {
				etk::String filename = s_APKArchive->getName(iii);
				if (start_with(filename, m_systemFileName) == true) {
					m_typeNode=etk::typeNode_folder;
					m_rights.setUserReadable(true);
					m_rights.setUserRunable(true);
					TK_DBG_MODE("Find a Folder in APK : '" << m_systemFileName << "'");
					return;
				}
			}
		}
		// special case of the user does not set tyhe / at the end of the path ...
		if (m_systemFileName[m_systemFileName.size()-1] != '/') {
			etk::String tmpName = m_systemFileName + '/';
			//Might be a folder ==> check if it existed in the start files ...
			for (int iii=0; iii<s_APKArchive->size(); iii++) {
				etk::String filename = s_APKArchive->getName(iii);
				if (start_with(filename, tmpName) == true) {
					m_typeNode = etk::typeNode_folder;
					m_rights.setUserReadable(true);
					m_rights.setUserRunable(true);
					TK_DBG_MODE("Find a Folder in APK : '" << m_systemFileName << "'");
					m_systemFileName += '/';
					if (m_userFileName[m_userFileName.size()-1] != '/') {
						m_userFileName += '/';
					}
					return;
				}
			}
		}
		TK_WARNING("File existed ??? in APK : '" << m_systemFileName << "'");
		return;
	}
	#endif
	// tmpStat Buffer :
	struct stat statProperty;
	if (-1 == stat(m_systemFileName.c_str(), &statProperty)) {
		//Normal case when the file does not exist ... ==> the it was in unknow mode ...
		return;
	}
	
	switch (statProperty.st_mode & S_IFMT) {
		case S_IFBLK:  m_typeNode=etk::typeNode_block;     break;
		case S_IFCHR:  m_typeNode=etk::typeNode_character; break;
		case S_IFDIR:  m_typeNode=etk::typeNode_folder;    break;
		case S_IFIFO:  m_typeNode=etk::typeNode_fifo;      break;
		#ifndef __TARGET_OS__Windows
		case S_IFLNK:  m_typeNode=etk::typeNode_link;      break;
		#endif
		case S_IFREG:  m_typeNode=etk::typeNode_file;      break;
		#ifndef __TARGET_OS__Windows
		case S_IFSOCK: m_typeNode=etk::typeNode_socket;    break;
		#endif
		default:       m_typeNode=etk::typeNode_unknow;    break;
	}
	// Right
	m_rights = statProperty.st_mode;
	m_idOwner = (int32_t) statProperty.st_uid;
	m_idGroup = (int32_t) statProperty.st_gid;
	m_timeCreate = statProperty.st_ctime;
	m_timeModify = statProperty.st_mtime;
	m_timeAccess = statProperty.st_atime;
	
	return;
}

bool etk::FSNode::setRight(etk::FSNodeRight _newRight) {
	// TODO : ...
	TK_ERROR("Can not set the new rights ...");
	return false;
}

void etk::FSNode::setName(const etk::String& _newName) {
	privateSetName(_newName);
}

etk::String etk::FSNode::getNameFolder() const {
	size_t lastPos = m_systemFileName.rfind('/');
	if (lastPos != etk::String::npos) {
		return etk::String(m_systemFileName, 0, lastPos);
	}
	return "";
}

etk::String etk::FSNode::getFileSystemName() const {
	return m_systemFileName;
}

etk::String etk::FSNode::getName() const {
	etk::String output("");
	if (m_libSearch.size() > 0) {
		output += "{";
		output += m_libSearch;
		output += "}";
	}
	switch (m_type) {
		default:
		case etk::FSNType_unknow:
			output += "HOME:";
			break;
		case etk::FSNType_direct:
			output += "/";
			break;
		case etk::FSNType_relatif:
			output += "REL:";
			break;
		case etk::FSNType_home:
			output += "~";
			break;
		case etk::FSNType_data:
			output += "DATA:";
			break;
		case etk::FSNType_userData:
			output += "USERDATA:";
			break;
		case etk::FSNType_cache:
			output += "CACHE:";
			break;
		case etk::FSNType_theme:
		case etk::FSNType_themeData:
			output += "THEME:";
			break;
	}
	output += m_userFileName;
	return output;
}

etk::String etk::FSNode::getNameFile() const {
	size_t lastPos = m_systemFileName.rfind('/');
	if (lastPos != etk::String::npos) {
		return etk::String(m_systemFileName, lastPos+1);
	}
	return "";
}

etk::String etk::FSNode::getRelativeFolder() const {
	etk::String tmppp = getName();
	TK_DBG_MODE("get REF folder : " << tmppp );
	switch (m_typeNode) {
		case etk::typeNode_unknow:
		case etk::typeNode_folder:
		case etk::typeNode_link:
		if (tmppp[tmppp.size()-1] == '/') {
				TK_DBG_MODE("     ==> : " << tmppp );
				return tmppp;
			} else {
				etk::String tmpVal = tmppp;
				tmpVal += "/";
				TK_DBG_MODE("     ==> : " << tmppp );
				return tmpVal;
			}
			break;
		case etk::typeNode_block:
		case etk::typeNode_character:
		case etk::typeNode_fifo:
		case etk::typeNode_file:
		case etk::typeNode_socket:
		default:
			break;
	}
	size_t lastPos = tmppp.rfind('/');
	if (lastPos != etk::String::npos) {
		TK_DBG_MODE("     ==> : " << etk::String(tmppp, 0, lastPos+1) );
		return etk::String(tmppp, 0, lastPos+1);
	}
	lastPos = tmppp.rfind(':');
	if (lastPos != etk::String::npos) {
		TK_DBG_MODE("     ==> : " << etk::String(tmppp, 0, lastPos+1) );
		return etk::String(tmppp, 0, lastPos+1);
	}
	TK_DBG_MODE("     ==> : ''" );
	return "";
}


bool etk::FSNode::touch() {
	TK_DEBUG("Touch FILE : " << getName());
	//just open in write an close ==> this will update the time
	if (fileOpenAppend() == false) {
		return false;
	}
	bool ret = fileClose();
	// update internal time and properties ...
	updateFileSystemProperty();
	return ret;
}

bool etk::FSNode::move(const etk::String& _path) {
	etk::FSNode tmpDst(_path);
	if (tmpDst.exist() == true) {
		tmpDst.remove();
	}
	TK_DEBUG("Move : \"" << getFileSystemName() << "\" ==> \"" << tmpDst.getFileSystemName() << "\"");
	// create path to be sure it exist ...
	TK_VERBOSE("create path: '" << tmpDst.getNameFolder() << "'");
	FSNODE_LOCAL_mkPath(tmpDst.getNameFolder().c_str() , 0755);
	int32_t res = rename(getFileSystemName().c_str(), tmpDst.getFileSystemName().c_str());
	if (res!=0) {
		TK_ERROR("Can not move the file: '" << getFileSystemName() << "' ==> '" << tmpDst.getFileSystemName() << "'");
		return false;
	} else {
		return true;
	}
}

bool etk::FSNode::remove() {
	if (getNodeType()==etk::typeNode_folder) {
		// remove the folder
		if( 0!=rmdir(m_systemFileName.c_str()) ) {
			if (ENOTEMPTY == errno) {
				TK_ERROR("The Directory is not empty...");
			}
			return false;
		}
	} else {
		if( 0!=unlink(m_systemFileName.c_str()) ) {
			return false;
		}
	}
	// update internal time and properties ...
	updateFileSystemProperty();
	return true;
}

uint64_t etk::FSNode::timeCreated() const {
	return m_timeCreate;
}

etk::String etk::FSNode::timeCreatedString() const {
	time_t tmpVal = (int32_t)m_timeCreate;
	etk::String tmpTime = ctime(&tmpVal);
	if (tmpTime[tmpTime.size()-1] == '\n') {
		tmpTime.erase(tmpTime.end()-1);
	}
	return tmpTime;
}

uint64_t etk::FSNode::timeModified() const {
	return m_timeModify;
}

etk::String etk::FSNode::timeModifiedString() const {
	time_t tmpVal = (int32_t)m_timeModify;
	etk::String tmpTime = ctime(&tmpVal);
	if (tmpTime[tmpTime.size()-1] == '\n') {
		tmpTime.erase(tmpTime.end()-1);
	}
	return tmpTime;
}

uint64_t etk::FSNode::timeAccessed() const {
	return m_timeAccess;
}

etk::String etk::FSNode::timeAccessedString() const {
	time_t tmpVal = (int32_t)m_timeAccess;
	etk::String tmpTime = ctime(&tmpVal);
	if (tmpTime[tmpTime.size()-1] == '\n') {
		tmpTime.erase(tmpTime.end()-1);
	}
	return tmpTime;
}
/*
	Operator :
*/
const etk::FSNode& etk::FSNode::operator=  (const etk::FSNode &_obj ) {
	// avoid copy to itself
	if( this == &_obj ) {
		return *this;
	}
	if(    nullptr != m_PointerFile
	#ifdef HAVE_ZIP_DATA
	    || nullptr != m_zipContent
	#endif
	   ) {
		TK_ERROR("Missing close the file : " << *this);
		fileClose();
		m_PointerFile = nullptr;
	}
	#ifdef HAVE_ZIP_DATA
		m_zipContent = nullptr;
		m_zipReadingOffset = 0;
	#endif
	etk::String tmppp = _obj.getName();
	privateSetName(tmppp);
	return *this;
}
bool etk::FSNode::operator== (const etk::FSNode& _obj ) const {
	if( this == &_obj ) {
		return true;
	}
	if(    _obj.m_userFileName == m_userFileName
	    && _obj.m_systemFileName == m_systemFileName
	    && _obj.m_type == m_type ) {
		return true;
	} else {
		return false;
	}
	return true;
}

bool etk::FSNode::operator!= (const etk::FSNode& _obj ) const {
	return !(*this == _obj);
}

etk::Stream& etk::operator <<(etk::Stream &_os, const etk::FSNode &_obj) {
	if (_obj.m_libSearch.size() != 0) {
		_os << "{" << _obj.m_libSearch << "}";
	}
	_os << "[" << _obj.m_type << "]->\"" << _obj.m_userFileName << "\"";
	return _os;
}

etk::Stream& etk::operator <<(etk::Stream &_os, const enum etk::FSNType &_obj) {
	switch (_obj)
	{
		case etk::FSNType_unknow:
			_os << "FSNType_unknow";
			break;
		case etk::FSNType_direct:
			_os << "FSNType_direct";
			break;
		case etk::FSNType_relatif:
			_os << "FSNType_relatif";
			break;
		case etk::FSNType_home:
			_os << "FSNType_home";
			break;
		case etk::FSNType_data:
			_os << "FSNType_data";
			break;
		case etk::FSNType_userData:
			_os << "FSNType_userData";
			break;
		case etk::FSNType_cache:
			_os << "FSNType_cache";
			break;
		case etk::FSNType_theme:
			_os << "FSNType_theme";
			break;
		case etk::FSNType_themeData:
			_os << "FSNType_theme(DATA)";
			break;
		default:
			_os << "FSN_TYPE_????";
			break;
	}
	return _os;
}

etk::Stream& etk::operator <<(etk::Stream &_os, const enum etk::typeNode &_obj) {
	switch (_obj) {
		case etk::typeNode_unknow:
			_os << "typeNode_unknow";
			break;
		case etk::typeNode_block:
			_os << "typeNode_block";
			break;
		case etk::typeNode_character:
			_os << "typeNode_character";
			break;
		case etk::typeNode_folder:
			_os << "typeNode_folder";
			break;
		case etk::typeNode_fifo:
			_os << "typeNode_fifo";
			break;
		case etk::typeNode_link:
			_os << "typeNode_link";
			break;
		case etk::typeNode_file:
			_os << "typeNode_file";
			break;
		case etk::typeNode_socket:
			_os << "typeNode_socket";
			break;
		default:
			_os << "typeNode_????";
			break;
	}
	return _os;
}

/*
	Folder specific :
*/
int64_t etk::FSNode::folderCount() {
	#ifdef __TARGET_OS__Windows
		/*
		if (m_systemFileName.size() == 0) {
			return getListDrive().size();
		}
		*/
	#endif
	int64_t counter=0;
	DIR *dir = nullptr;
	struct dirent *ent = nullptr;
	dir = opendir(m_systemFileName.c_str());
	if (dir != nullptr) {
		// for each element in the drectory...
		while ((ent = readdir(dir)) != nullptr) {
			etk::String tmpName(ent->d_name);
			if(    tmpName == "." 
			    || tmpName == ".." ) {
				// do nothing ...
				continue;
			}
			// just increment counter :
			counter++;
		}
		closedir(dir);
	} else {
		TK_ERROR("could not open directory : \"" << *this << "\"");
	}
	return counter;
}

etk::Vector<etk::FSNode *> etk::FSNode::folderGetSubList(bool _showHidenFile, bool _getFolderAndOther, bool _getFile, bool _temporaryFile) {
	etk::String filter=".*";
	if (_temporaryFile == true) {
		filter = ".*(~|\\.bck|\\.tmp)";
	}
	return etk::FSNode::folderGetSubList(_showHidenFile, _getFolderAndOther, _getFile, filter);
}

etk::Vector<etk::FSNode *> etk::FSNode::folderGetSubList(bool _showHidenFile, bool _getFolderAndOther, bool _getFile, const etk::String& _filter) {
	TK_TODO("implement filter ... ");
	etk::Vector<etk::FSNode*> tmpp;
	#ifdef __TARGET_OS__Windows
		/*
		if (m_systemFileName.size() == 0) {
			etk::Vector<etk::String> listDrive = getListDrive();
			for (auto &it : listDrive) {
				tmpp.pushBack(new etk::FSNode(it));
			}
			return tmpp;
		}
		*/
	#endif
	// regenerate the next list :
	etk::FSNode * tmpEmement = nullptr;
	if (m_typeNode != etk::typeNode_folder ) {
		return tmpp;
	}
	#ifdef HAVE_ZIP_DATA
	if(    m_type == etk::FSNType_data
	    || m_type == etk::FSNType_themeData) {
		etk::Vector<etk::String> listAdded;
		etk::String assetsName = baseFolderData;
		etk::String FolderName = getNameFolder();
		if (s_APKArchive==nullptr) {
			return tmpp;
		}
		if (FolderName[FolderName.size()-1] != '/') {
			FolderName += "/";
		}
		for (int iii=0; iii<s_APKArchive->size(); iii++) {
			etk::String filename = s_APKArchive->getName(iii);
			if (start_with(filename, FolderName) == true) {
				//TK_INFO("pppppp '" << filename  << "'");
				//TK_INFO("       '" << FolderName << "'");
				etk::String tmpString(filename, FolderName.size());
				size_t pos = tmpString.find('/');
				if (pos != etk::String::npos) {
					// a simple folder :
					tmpString = etk::String(tmpString, 0, pos);
				}
				//TK_INFO("plop '" << getName()  << "' '" << tmpString << "'");
				tmpString = getName() + tmpString;
				bool findIt = false;
				for (size_t jjj = 0; jjj < listAdded.size(); ++jjj) {
					if (listAdded[jjj] == tmpString) {
						findIt = true;
						break;
					}
				}
				if (findIt == false) {
					listAdded.pushBack(tmpString);
					tmpEmement = new etk::FSNode(tmpString);
					if (nullptr == tmpEmement) {
						TK_ERROR("allocation error ... of ewol::FSNode");
						continue;
					}
					TK_VERBOSE("find element : '" << tmpString << "' --> " << *tmpEmement);
					tmpp.pushBack(tmpEmement);
					tmpEmement = nullptr;
				}
			}
		}
		return tmpp;
	}
	#endif
	DIR *dir = nullptr;
	struct dirent *ent = nullptr;
	dir = opendir(m_systemFileName.c_str());
	if (dir != nullptr) {
		// for each element in the drectory...
		while ((ent = readdir(dir)) != nullptr) {
			etk::String tmpName(ent->d_name);
			TK_VERBOSE(" search in folder\"" << tmpName << "\"");
			if(    tmpName == "." 
			    || tmpName == ".." ) {
				// do nothing ...
				continue;
			}
			if(    false == start_with(tmpName, ".")
			    || true == _showHidenFile) {
				tmpEmement = new etk::FSNode(getRelativeFolder()+tmpName);
				if (nullptr == tmpEmement) {
					TK_ERROR("allocation error ... of ewol::FSNode");
					continue;
				}
				if(tmpEmement->getNodeType() == etk::typeNode_file) {
					if (true == _getFile) {
						tmpp.pushBack(tmpEmement);
					} else {
						delete(tmpEmement);
						tmpEmement = nullptr;
					}
				} else if (_getFolderAndOther) {
					tmpp.pushBack(tmpEmement);
				} else {
					delete(tmpEmement);
					tmpEmement = nullptr;
				}
			}
		}
		closedir(dir);
	} else {
		TK_ERROR("could not open directory : \"" << *this << "\"");
	}
	
	// reorder the files
	sortElementList(tmpp);
	
	return tmpp;
}

etk::Vector<etk::String> etk::FSNode::folderGetSub(bool _getFolder, bool _getFile, const etk::String& _filter) {
	TK_TODO("implement filter ... ");
	#ifdef __TARGET_OS__Windows
		/*
		if (m_systemFileName.size() == 0) {
			return getListDrive();
		}
		*/
	#endif
	etk::Vector<etk::String> out;
	// regenerate the next list :
	etk::FSNode * tmpEmement = nullptr;
	if (m_typeNode != etk::typeNode_folder ) {
		return out;
	}
	#ifdef HAVE_ZIP_DATA
	if(    m_type == etk::FSNType_data
	    || m_type == etk::FSNType_themeData) {
		etk::Vector<etk::String> listAdded;
		etk::String assetsName = baseFolderData;
		etk::String FolderName = getNameFolder();
		if (s_APKArchive == nullptr) {
			return out;
		}
		if (FolderName[FolderName.size()-1] != '/') {
			FolderName += "/";
		}
		for (int32_t iii=0; iii<s_APKArchive->size(); iii++) {
			etk::String filename = s_APKArchive->getName(iii);
			if (start_with(filename, FolderName) == true) {
				etk::String tmpString(filename, FolderName.size());
				size_t pos = tmpString.find('/');
				if (pos != etk::String::npos) {
					// a simple folder :
					tmpString = etk::String(tmpString, 0, pos+1);
				}
				tmpString = getName() + tmpString;
				bool findIt = false;
				for (size_t jjj = 0; jjj < listAdded.size(); ++jjj) {
					if (listAdded[jjj] == tmpString) {
						findIt = true;
						break;
					}
				}
				if (findIt == false) {
					listAdded.pushBack(tmpString);
					etk::FSNode tmpEmement(tmpString);
					TK_VERBOSE("find element : '" << tmpString << "' --> " << tmpEmement);
					out.pushBack(tmpEmement.getName());
				}
			}
		}
		std::sort(out.begin(), out.end());
		return out;
	}
	#endif
	DIR *dir = nullptr;
	struct dirent *ent = nullptr;
	dir = opendir(m_systemFileName.c_str());
	if (dir != nullptr) {
		// for each element in the drectory...
		while ((ent = readdir(dir)) != nullptr) {
			etk::String tmpName(ent->d_name);
			TK_VERBOSE(" search in folder\"" << tmpName << "\"");
			if(    tmpName == "." 
			    || tmpName == ".." ) {
				// do nothing ...
				continue;
			}
			etk::FSNode tmpEmement(getRelativeFolder()+tmpName);
			if(tmpEmement.getNodeType() == etk::typeNode_file) {
				if (_getFile == true) {
					out.pushBack(tmpEmement.getName());
				}
			} else if (_getFolder) {
				out.pushBack(tmpEmement.getName());
			}
		}
		closedir(dir);
	} else {
		TK_ERROR("could not open directory : \"" << *this << "\"");
	}
	// reorder the files
	etk::sort(out);
	return out;
}

etk::FSNode etk::FSNode::folderGetParent() {
	etk::FSNode tmpp;
	return tmpp;
}

void etk::FSNode::folderGetRecursiveFiles(etk::Vector<etk::String>& _output, bool _recursiveEnable) {
	#ifdef HAVE_ZIP_DATA
	if(    m_type == etk::FSNType_data
	    || m_type == etk::FSNType_themeData) {
		etk::String assetsName = baseFolderData;
		etk::String FolderName = getNameFolder();
		if (s_APKArchive==nullptr) {
			return;
		}
		for (int iii=0; iii<s_APKArchive->size(); iii++) {
			etk::String filename = s_APKArchive->getName(iii);
			if (start_with(filename, FolderName) == true) {
				etk::String tmpString;
				if(m_type == etk::FSNType_data) {
					tmpString = "DATA:";
				} else {
					tmpString = "THEME:";
				}
				if (start_with(filename, assetsName) == true) {
					filename.erase(0,assetsName.size());
				}
				tmpString += filename;
				_output.pushBack(tmpString);
			}
		}
		return;
	}
	#endif
	// regenerate the next list :
	etk::FSNode * tmpEmement;
	DIR *dir = nullptr;
	struct dirent *ent = nullptr;
	dir = opendir(m_systemFileName.c_str());
	//TK_DEBUG(" ** open Folder : " << m_systemFileName );
	if (dir != nullptr) {
		// for each element in the drectory...
		while ((ent = readdir(dir)) != nullptr) {
			etk::String tmpName(ent->d_name);
			if(    tmpName == "." 
			    || tmpName == ".." ) {
				// do nothing ...
				continue;
			}
			//TK_DEBUG(" find : " << ent->d_name << " ==> " << (GetRelativeFolder()+tmpName));
			tmpEmement = new etk::FSNode(getRelativeFolder()+tmpName);
			if (nullptr != tmpEmement) {
				if(tmpEmement->getNodeType() == etk::typeNode_file) {
					etk::String tmpVal = tmpEmement->getName();
					_output.pushBack(tmpVal);
				}
				if(tmpEmement->getNodeType() == etk::typeNode_folder) {
					if (true==_recursiveEnable) {
						tmpEmement->folderGetRecursiveFiles(_output, _recursiveEnable);
					}
				}
				delete(tmpEmement);
				tmpEmement = nullptr;
			} else {
				TK_ERROR("allocation error ... of ewol::FSNode");
				continue;
			}
		}
		closedir(dir);
	} else {
		TK_ERROR("could not open directory : \"" << *this << "\"");
	}
	return;
}

/*
	File Specific :
*/
bool etk::FSNode::fileHasExtention() {
	size_t lastPos = m_userFileName.rfind('.');
	if(    lastPos != etk::String::npos // Find a . at the fist position .jdlskjdfklj ==> hiden file
	    && m_userFileName.size() != lastPos ) { // Remove file ended with .
		return true;
	} else {
		return false;
	}
}

etk::String etk::FSNode::fileGetExtention() {
	size_t lastPos = m_userFileName.rfind('.');
	if(    lastPos != etk::String::npos // Find a . at the fist position .jdlskjdfklj ==> hiden file
	    && m_userFileName.size() != lastPos ) { // Remove file ended with .
		// Get the FileName
		return etk::String(m_userFileName, lastPos+1);
	}
	return "";
}

uint64_t etk::FSNode::fileSize() {
	if (m_typeNode != etk::typeNode_file) {
		TK_ERROR("Request size of a non file node : " << m_typeNode);
		return 0;
	}
	#ifdef HAVE_ZIP_DATA
	if(    m_type == etk::FSNType_data
	    || m_type == etk::FSNType_themeData) {
		if (loadDataZip() == true) {
			return m_zipContent->getTheoricSize();
		}
		return 0;
	}
	#endif
	// Note : this is a proper methode to get the file size for Big files ... otherwithe the size is limited at 2^31 bytes
	// tmpStat Buffer :
	struct stat statProperty;
	if (stat(m_systemFileName.c_str(), &statProperty) == -1) {
		//Normal case when the file does not exist ... ==> the it was in unknow mode ...
		TK_ERROR("mlkmlkmlkmlkmlkmlk");
		return 0;
	}
	TK_VERBOSE(" file size : " << (int64_t)statProperty.st_size << " bytes");
	if ((uint64_t)statProperty.st_size <= 0) {
		return 0;
	}
	return (uint64_t)statProperty.st_size;
}


bool etk::FSNode::fileOpenRead() {
	#ifdef HAVE_ZIP_DATA
	if(    m_type == etk::FSNType_data
	    || m_type == etk::FSNType_themeData) {
		if (loadDataZip() == false) {
			return false;
		}
		ethread::UniqueLock lock(getNodeMutex());
		s_APKArchive->open(m_systemFileName);
		return m_zipContent->getTheoricSize() == m_zipContent->size();
	}
	#endif
	if (m_PointerFile != nullptr) {
		TK_CRITICAL("File Already open : " << *this);
		return true;
	}
	TK_VERBOSE(" Read file : " << m_systemFileName);
	m_PointerFile = fopen(m_systemFileName.c_str(),"rb");
	if(m_PointerFile == nullptr) {
		TK_ERROR("Can not find the file " << *this );
		return false;
	}
	return true;
}
bool etk::FSNode::fileOpenWrite() {
	#ifdef HAVE_ZIP_DATA
	if(    etk::FSNType_data == m_type
	    || etk::FSNType_themeData == m_type) {
		return false;
	}
	#endif
	if (m_PointerFile != nullptr) {
		TK_CRITICAL("File Already open : " << *this);
		return true;
	}
	FSNODE_LOCAL_mkPath(getNameFolder().c_str() , 0744);
	TK_VERBOSE("Write file : " << m_systemFileName);
	m_PointerFile = fopen(m_systemFileName.c_str(),"wb");
	if(m_PointerFile == nullptr) {
		TK_ERROR("Can not find the file " << *this);
		return false;
	}
	return true;
}

bool etk::FSNode::fileOpenAppend() {
	#ifdef HAVE_ZIP_DATA
	if(    etk::FSNType_data == m_type
	    || etk::FSNType_themeData == m_type) {
		return false;
	}
	#endif
	if (m_PointerFile != nullptr) {
		TK_CRITICAL("File Already open : " << *this);
		return true;
	}
	FSNODE_LOCAL_mkPath(getNameFolder().c_str() , 0744);
	
	TK_VERBOSE("Append file : " << m_systemFileName);
	
	m_PointerFile = fopen(m_systemFileName.c_str(),"ab");
	if(m_PointerFile == nullptr) {
		TK_ERROR("Can not find the file " << *this);
		return false;
	}
	return true;
}

bool etk::FSNode::fileIsOpen() {
	#ifdef HAVE_ZIP_DATA
	if(    etk::FSNType_data == m_type
	    || etk::FSNType_themeData == m_type) {
		if (m_zipContent == nullptr) {
			return false;
		}
		return true;
	}
	#endif
	if (m_PointerFile == nullptr) {
		return false;
	}
	return true;
}
bool etk::FSNode::fileClose() {
	#ifdef HAVE_ZIP_DATA
	if(    etk::FSNType_data == m_type
	    || etk::FSNType_themeData == m_type) {
		if (m_zipContent == nullptr) {
			TK_CRITICAL("File Already closed : " << *this);
			return false;
		}
		s_APKArchive->close(m_systemFileName);
		m_zipContent = nullptr;
		m_zipReadingOffset = 0;
		return true;
	}
	#endif
	if (m_PointerFile == nullptr) {
		TK_CRITICAL("File Already closed : " << *this);
		return false;
	}
	fclose(m_PointerFile);
	m_PointerFile = nullptr;
	return true;
}

char* etk::FSNode::fileGets(char* _elementLine, int64_t _maxData) {
	memset(_elementLine, 0, _maxData);
	#ifdef HAVE_ZIP_DATA
	char * element = _elementLine;
	int64_t outSize = 0;
	if(    m_type == etk::FSNType_data
	    || m_type == etk::FSNType_themeData) {//char * tmpData = internalDataFiles[iii].data + m_readingOffset;
		if (m_zipContent == nullptr) {
			element[0] = '\0';
			return nullptr;
		}
		if (m_zipReadingOffset >= m_zipContent->size()) {
			element[0] = '\0';
			return nullptr;
		}
		while (((char*)m_zipContent->data())[m_zipReadingOffset] != '\0') {
			if(    ((char*)m_zipContent->data())[m_zipReadingOffset] == '\n'
			    || ((char*)m_zipContent->data())[m_zipReadingOffset] == '\r') {
				*element = ((char*)m_zipContent->data())[m_zipReadingOffset];
				element++;
				m_zipReadingOffset++;
				*element = '\0';
				return _elementLine;
			}
			*element = ((char*)m_zipContent->data())[m_zipReadingOffset];
			element++;
			m_zipReadingOffset++;
			if (m_zipReadingOffset>=m_zipContent->size()) {
				*element = '\0';
				return _elementLine;
			}
			// check maxData Size ...
			if (outSize>=_maxData-1) {
				*element = '\0';
				return _elementLine;
			}
			outSize++;
		}
		if (outSize == 0) {
			return nullptr;
		} else {
			// send last line
			return _elementLine;
		}
	}
	#endif
	return fgets(_elementLine, _maxData, m_PointerFile);
}

char etk::FSNode::fileGet() {
	char data='\0';
	if (fileRead(&data, 1, 1)!=1) {
		return '\0';
	}
	return data;
}

bool etk::FSNode::fileGets(etk::String& _output) {
	_output.clear();
	char tmp = fileGet();
	while (    tmp != '\0'
	        && tmp != '\n') {
		_output += tmp;
		tmp = fileGet();
	}
	if (tmp == '\0') {
		return false;
	}
	return true;
}

int64_t etk::FSNode::fileRead(void* _data, int64_t _blockSize, int64_t _nbBlock) {
	#ifdef HAVE_ZIP_DATA
	if(    m_type == etk::FSNType_data
	    || m_type == etk::FSNType_themeData) {
		if (m_zipContent == nullptr) {
			((char*)_data)[0] = '\0';
			return 0;
		}
		int32_t dataToRead = _blockSize * _nbBlock;
		if (dataToRead + m_zipReadingOffset > m_zipContent->size()) {
			_nbBlock = ((m_zipContent->size() - m_zipReadingOffset) / _blockSize);
			dataToRead = _blockSize * _nbBlock;
		}
		memcpy(_data, &((char*)m_zipContent->data())[m_zipReadingOffset], dataToRead);
		m_zipReadingOffset += dataToRead;
		return _nbBlock;
	}
	#endif
	if (m_PointerFile == nullptr) {
		TK_ERROR("Can not read in a file that is not open : " << *this);
		return 0;
	}
	return fread(_data, _blockSize, _nbBlock, m_PointerFile);
}

bool etk::FSNode::filePut(char _input) {
	if (fileWrite(&_input, 1, 1) == 1) {
		return true;
	}
	return false;
}

bool etk::FSNode::filePuts(const etk::String& _input) {
	if (fileWrite((void*)_input.c_str(), 1, _input.size()) == (int64_t)_input.size()) {
		return true;
	}
	return false;
}

int64_t etk::FSNode::fileWrite(const void * _data, int64_t _blockSize, int64_t _nbBlock) {
	#ifdef HAVE_ZIP_DATA
	if(    m_type == etk::FSNType_data
	    || m_type == etk::FSNType_themeData) {
		TK_CRITICAL("Can not write on data inside APK : " << *this);
		return 0;
	}
	#endif
	if (m_PointerFile == nullptr) {
		TK_ERROR("Can not write in a file that is not open : " << *this);
		return 0;
	}
	return fwrite(_data, _blockSize, _nbBlock, m_PointerFile);
}
/*
etk::FSNode& etk::FSNode::operator<< (const etk::Stream& _data) {
	fileWrite(_data.str().c_str(), 1, _data.str().size());
	return *this;
}
*/
etk::FSNode& etk::FSNode::operator<< (const etk::Stream& _data) {
	fileWrite(_data.c_str(), 1, _data.size());
	return *this;
}
etk::FSNode& etk::FSNode::operator<< (const etk::String& _data) {
	fileWrite(_data.c_str(), 1, _data.size());
	return *this;
}
etk::FSNode& etk::FSNode::operator<< (const char* _data) {
	fileWrite(_data, 1, strlen(_data));
	return *this;
}
etk::FSNode& etk::FSNode::operator<< (const int32_t _data) {
	etk::String sss = etk::toString(_data);
	fileWrite(sss.c_str(), 1, sss.size());
	return *this;
}
etk::FSNode& etk::FSNode::operator<< (const uint32_t _data) {
	etk::String sss = etk::toString(_data);
	fileWrite(sss.c_str(), 1, sss.size());
	return *this;
}
etk::FSNode& etk::FSNode::operator<< (const float _data) {
	etk::String sss = etk::toString(_data);
	fileWrite(sss.c_str(), 1, sss.size());
	return *this;
}

bool etk::FSNode::fileSeek(long int _offset, enum etk::seekNode _origin)
{
	#ifdef HAVE_ZIP_DATA
	if(    m_type == etk::FSNType_data
	    || m_type == etk::FSNType_themeData) {
		if (nullptr == m_zipContent) {
			return false;
		}
		int32_t positionEnd = 0;
		switch(_origin) {
			case etk::seekNode_end:
				positionEnd = m_zipContent->size();
				break;
			case etk::seekNode_current:
				positionEnd = m_zipReadingOffset;
				break;
			default:
				positionEnd = 0;
				break;
		}
		positionEnd += _offset;
		if (positionEnd < 0) {
			positionEnd = 0;
		} else if (positionEnd > m_zipContent->size()) {
			positionEnd = m_zipContent->size();
		}
		m_zipReadingOffset = positionEnd;
		return true;
	}
	#endif
	int originFS = 0;
	switch(_origin) {
		case etk::seekNode_end:
			originFS = SEEK_END;
			break;
		case etk::seekNode_current:
			originFS = SEEK_CUR;
			break;
		default:
			originFS = 0;
			break;
	}
	fseek(m_PointerFile, _offset, originFS);
	if(ferror(m_PointerFile)) {
		return false;
	} else {
		return true;
	}
}
int64_t etk::FSNode::fileTell() {
	#ifdef HAVE_ZIP_DATA
	if(    m_type == etk::FSNType_data
	    || m_type == etk::FSNType_themeData) {
		if (nullptr == m_zipContent) {
			return false;
		}
		return m_zipReadingOffset;
	}
	#endif
	return ftell(m_PointerFile);
	
}

void etk::FSNode::fileFlush() {
	#ifdef HAVE_ZIP_DATA
	if(    m_type == etk::FSNType_data
	    || m_type == etk::FSNType_themeData) {
		return;
	}
	#endif
	if (m_PointerFile != nullptr) {
		fflush(m_PointerFile);
	}
}


// TODO : Add an INIT to reset all allocated parameter :
static etk::Map<etk::String, etk::String> g_listTheme;

void etk::theme::setName(const etk::String& _refName, const etk::String& _folderName) {
	TK_WARNING("Change theme : '" << _refName << "' : '" << _folderName << "'");
	g_listTheme.set(_refName, _folderName);
}

etk::String etk::theme::getName(const etk::String& _refName) {
	auto it = g_listTheme.find(_refName);
	if (it != g_listTheme.end()) {
		return it->second;
	}
	return _refName;
}

// get the list of all the theme folder availlable in the user Home/appl
etk::Vector<etk::String> etk::theme::list() {
	etk::Vector<etk::String> keys;
	for (auto &it : g_listTheme) {
		keys.pushBack(it.first);
	}
	return keys;
}

static etk::Map<etk::String, etk::String> g_listThemeDefault;
void etk::theme::setNameDefault(const etk::String& _refName, const etk::String& _folderName) {
	auto it = g_listThemeDefault.find(_refName);
	if (it != g_listThemeDefault.end()) {
		it->second = _folderName;
		return;
	}
	g_listThemeDefault.set(_refName, _folderName);
}

etk::String etk::theme::getNameDefault(const etk::String& _refName) {
	auto it = g_listThemeDefault.find(_refName);
	if (it != g_listThemeDefault.end()) {
		return it->second;
	}
	return "default";
}



/* --------------------------------------------------------------------------
 * 
 *  Simple direct wrapper on the FileSystem node access :
 * 
 * -------------------------------------------------------------------------- */
uint64_t etk::FSNodeGetSize(const etk::String& _path) {
	etk::FSNode tmpNode(_path);
	if (tmpNode.exist() == false) {
		return 0;
	}
	return tmpNode.fileSize();
}

bool etk::FSNodeRemove(const etk::String& _path) {
	etk::FSNode tmpNode(_path);
	if (false==tmpNode.exist()) {
		return false;
	}
	return tmpNode.remove();
}

int64_t etk::FSNodeGetCount(const etk::String& _path) {
	etk::FSNode tmpNode(_path);
	if (false==tmpNode.exist()) {
		return -1;
	}
	return tmpNode.folderCount();
}

bool etk::FSNodeCreate(const etk::String& _path, etk::FSNodeRight _right, enum etk::typeNode _type) {
	// TODO :
	return false;
}

bool etk::FSNodeExist(const etk::String& _path) {
	etk::FSNode tmpNode(_path);
	return tmpNode.exist();
}

bool etk::FSNodeMove(const etk::String& _path1, const etk::String& _path2) {
	etk::FSNode tmpNode(_path1);
	if (tmpNode.exist() == false) {
		TK_WARNING("try to move an un-existant file '" << _path1 << "'");
		return false;
	}
	// no check error in every case
	(void)etk::FSNodeRemove(_path2);
	//move the node
	return tmpNode.move(_path2);
}

etk::FSNodeRight etk::FSNodeGetRight(const etk::String& _path) {
	etk::FSNode tmpNode(_path);
	return tmpNode.getRight();
}

enum etk::typeNode etk::FSNodeGetType(const etk::String& _path) {
	etk::FSNode tmpNode(_path);
	return tmpNode.getNodeType();
}

uint64_t etk::FSNodeGetTimeCreated(const etk::String& _path) {
	etk::FSNode tmpNode(_path);
	return tmpNode.timeCreated();
}

uint64_t etk::FSNodeGetTimeModified(const etk::String& _path) {
	etk::FSNode tmpNode(_path);
	return tmpNode.timeModified();
}

uint64_t etk::FSNodeGetTimeAccessed(const etk::String& _path) {
	etk::FSNode tmpNode(_path);
	return tmpNode.timeAccessed();
}

bool etk::FSNodeTouch(const etk::String& _path) {
	etk::FSNode tmpNode(_path);
	return tmpNode.touch();
}

bool etk::FSNodeEcho(const etk::String& _path, const etk::String& _dataTowrite) {
	etk::FSNode tmpNode(_path);
	if (tmpNode.exist() == false) {
		return false;
	}
	if (tmpNode.getNodeType() == typeNode_folder) {
		return false;
	}
	if (tmpNode.fileOpenWrite() == false) {
		return false;
	}
	// convert in UTF8 :
	if ((int64_t)_dataTowrite.size() != tmpNode.fileWrite((char*)_dataTowrite.c_str(), 1, _dataTowrite.size())) {
		tmpNode.fileClose();
		return false;
	}
	return tmpNode.fileClose();
}

bool etk::FSNodeEchoAdd(const etk::String& _path, const etk::String& _dataTowrite) {
	etk::FSNode tmpNode(_path);
	if (tmpNode.exist() == false) {
		return false;
	}
	if (tmpNode.getNodeType() == typeNode_folder) {
		return false;
	}
	if (tmpNode.fileOpenAppend() == false) {
		return false;
	}
	// convert in UTF8 :
	if ((int64_t)_dataTowrite.size() != tmpNode.fileWrite((char*)_dataTowrite.c_str(), 1, _dataTowrite.size())) {
		tmpNode.fileClose();
		return false;
	}
	return tmpNode.fileClose();
}

void etk::FSNodeHistory(const etk::String& _path, int32_t _historyCount) {
	// step 1 : Move the file to prevent writing error
	//Get the first oldest save :
	for (int32_t iii=_historyCount-1; iii>0 ; iii--) {
		if (etk::FSNodeExist(_path + "-" + etk::toString(iii)) == true) {
			etk::FSNodeMove(_path + "-" + etk::toString(iii), _path + "-" + etk::toString(iii+1));
		}
	}
	if (etk::FSNodeExist(_path) == true) {
		etk::FSNodeMove(_path, _path + "-1");
	}
}

etk::String etk::FSNodeReadAllData(const etk::String& _path) {
	etk::String output;
	etk::FSNode node(_path);
	if (node.fileOpenRead() == false) {
		TK_ERROR("can not open file : '" << node << "'");
		return "";
	}
	etk::String tmp;
	while (node.fileGets(tmp) == true) {
		output += tmp;
		output += "\n";
	}
	output += tmp;
	node.fileClose();
	return output;
}

void etk::FSNodeWriteAllData(const etk::String& _path, const etk::String& _data) {
	etk::FSNode node(_path);
	if (node.fileOpenWrite() == false) {
		TK_ERROR("can not open file : '" << node << "'");
		return;
	}
	node.fileWrite(&_data[0], sizeof(char), _data.size());
	node.fileClose();
}

etk::String etk::FSNodeGetRealName(const etk::String& _path) {
	etk::FSNode node(_path);
	return node.getFileSystemName();
}

etk::Vector<etk::String> etk::FSNodeExplodeMultiplePath(const etk::String& _path) {
	etk::Vector<etk::String> out;
	
	etk::String libSearch = "";
	etk::String newName = _path;
	if (    _path.size() > 0
	     && _path[0] == '{') {
		// special case: Reference of searching in subLib folder ==> library use-case
		size_t firstPos = _path.find('}');
		if (firstPos != etk::String::npos) {
			// we find a theme name : We extracted it :
			libSearch = etk::String(_path, 1, firstPos-1);
			newName = etk::String(_path, firstPos+1);
		} else {
			TK_ERROR("start a path name with '{' without '}' : " << _path);
			// remove in case the {
			newName = etk::String(_path, 1);
		}
	}
	if (libSearch.size() != 0) {
		if (libSearch[0] != '@') {
			out.pushBack(newName);
			out.pushBack(etk::String("{@") + libSearch + "}" + newName);
			return out;
		}
		out.pushBack(etk::String("{") + libSearch + "}" + newName);
		return out;
	}
	out.pushBack(newName);
	return out;
}

