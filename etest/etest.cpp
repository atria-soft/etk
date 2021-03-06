/** @file
 * @author Edouard DUPIN
 * @copyright 2011, Edouard DUPIN, all right reserved
 * @license MPL v2.0 (see license file)
 */

#include <etest/etest.hpp>
#include <etest/debug.hpp>
#include <etk/Vector.hpp>
#include <elog/elog.hpp>
#include <echrono/Steady.hpp>
#include <echrono/Duration.hpp>
#include <etk/Allocator.hpp>

static int32_t nbTimeInit = 0;

static etk::Vector<etest::GenericTest*>& getListOfTest() {
	static etk::Vector<etest::GenericTest*> s_listOfData;
	return s_listOfData;
}
static etk::String filterGroup;
static etk::String filterTest;
static bool showAtTheEnd = false;


void etest::unInit() {
	if (nbTimeInit > 1) {
		nbTimeInit--;
		// not the time to un-init
		return;
	}
	nbTimeInit--;
	if (nbTimeInit < 0) {
		ETEST_ERROR("ETEST system un-init More un-init than init ...");
		nbTimeInit = 0;
		return;
	}
	ETEST_INFO("ETEST system un-init (BEGIN)");
	ETEST_INFO("ETEST system un-init (END)");
	elog::unInit();
}

static etk::Vector<etk::String> getListGroupSpecific(const etk::Vector<etest::GenericTest*>& _value) {
	etk::Vector<etk::String> listGroup;
	for (auto &it: _value) {
		if (etk::isIn(it->getTestGroup(), listGroup) == true) {
			continue;
		}
		listGroup.pushBack(it->getTestGroup());
	}
	return listGroup;
}

static etk::Vector<etk::String> getListGroup() {
	return getListGroupSpecific(getListOfTest());
}

static etk::Vector<etest::GenericTest*> getListFiltered() {
	if (filterGroup == "") {
		return getListOfTest();
	}
	etk::Vector<etest::GenericTest*> out;
	for (auto &it: getListOfTest()) {
		if (it->getTestGroup() != filterGroup) {
			continue;
		}
		if (filterTest == "") {
			out.pushBack(it);
			continue;
		}
		if (filterTest == it->getTestName()) {
			out.pushBack(it);
			continue;
		}
	}
	return out;
}

static void listAllTest() {
	ETEST_PRINT("We have " << getListOfTest().size() << " tests:");
	etk::Vector<etk::String> listGroup = getListGroup();
	for (auto &itGroup: listGroup) {
		ETEST_PRINT("Group: " << itGroup);
		for (auto &it: getListOfTest()) {
			if (it->getTestGroup() == itGroup) {
				ETEST_PRINT("     - " << itGroup << "." << it->getTestName());
			}
		}
	}
}

static void listAllTestError() {
	int32_t count = 0;
	for (auto &it: getListOfTest()) {
		if (it->getError() == true) {
			count++;
			break;
		}
	}
	if (count == 0) {
		return;
	}
	ETEST_PRINT("List all error test:");
	etk::Vector<etk::String> listGroup = getListGroup();
	for (auto &itGroup: listGroup) {
		for (auto &it: getListOfTest()) {
			if (it->getTestGroup() == itGroup) {
				if (it->getError() == true) {
					ETEST_PRINT("     - " << itGroup << "." << it->getTestName());
				}
			}
		}
	}
}

void etest::init(int32_t _argc, const char** _argv) {
	if (nbTimeInit > 0) {
		nbTimeInit++;
		// already init
		return;
	}
	nbTimeInit++;
	elog::init(_argc, _argv, "etest");
	
	//etk::initDefaultFolder("ewolApplNoName");
	ETEST_INFO("ETEST system init (BEGIN) ");
	for (int32_t iii=0; iii<_argc ; ++iii) {
		etk::String data = _argv[iii];
		if (    data == "-h"
		     || data == "--help") {
			ETEST_PRINT("etest - help : ");
			if (_argc >= 1) {
				ETEST_PRINT("    " << _argv[0] << " [options]");
				ETEST_PRINT("        --etest-list          List all test names");
				ETEST_PRINT("        --etest-filter=XXX    filter group or test: XXX or WWW.yyy");
				ETEST_PRINT("        --etest-show          Display at the end the list of test that fail");
			}
			ETEST_PRINT("        -h/--help: this help");
		} else if (data == "--etest-list") {
			listAllTest();
			exit(0);
		} else if (data == "--etest-filter=") {
			ETEST_PRINT("Missing data in the filter list...");
			exit(0);
		} else if (data == "--etest-show") {
			ETEST_PRINT("Display all error test at the end ...");
			showAtTheEnd = true;
		} else if (data.startWith("--etest-filter=") == true) {
			etk::String filter = &data[15];
			ETEST_PRINT("        Filter: " << filter);
			etk::Vector<etk::String> tmp = filter.split(".");
			if (tmp.size() == 1) {
				// Filter only the groups
				filterGroup = filter;
				ETEST_VERBOSE("filter group:" << filterGroup);
			} else if (tmp.size() == 2) {
				filterGroup = tmp[0];
				filterTest = tmp[1];
				ETEST_VERBOSE("filter group:" << filterGroup << "  & test:" << filterTest);
			} else {
				ETEST_CRITICAL("Can not parse the argument : '" << data << "' ==> more than 1 '.'");
			}
		} else if (data.startWith("--etest") == true) {
			ETEST_ERROR("Can not parse the argument : '" << data << "'");
		}
	}
	ETEST_INFO("ETEST system init (END)");
}

etest::GenericTest::GenericTest(const char* _file,
                                uint32_t _line,
                                const char* _testGroup,
                                const char* _testName):
  m_file(_file),
  m_line(_line),
  m_testGroup(_testGroup),
  m_testName(_testName) {
	
}

const etk::String& etest::GenericTest::getFileName() const {
	return m_file;
}

uint32_t etest::GenericTest::getFileLine() const {
	return m_line;
}

const etk::String& etest::GenericTest::getTestGroup() const {
	return m_testGroup;
}

const etk::String& etest::GenericTest::getTestName() const {
	return m_testName;
}

bool etest::GenericTest::getError() const {
	return m_haveError;
}

uint32_t etest::GenericTest::getNumberCheck() const {
	return m_numberCheck;
}

uint32_t etest::GenericTest::getNumberCheckError() const {
	return m_numberCheckFail;
}

uint32_t etest::GenericTest::getNumberCheckNotImplemented() const {
	return m_numberCheckNotImplemented;
}

void etest::GenericTest::testNotImplemented(uint32_t _line) {
	ETEST_ERROR("Not implemented: " << m_file << ":" << _line << ":");
	m_haveError = true;
	m_numberCheckNotImplemented++;
}

void etest::GenericTest::testResult(bool _result,
                                    const etk::String& _test1Value,
                                    const etk::String& _test1,
                                    const etk::String& _test2Value,
                                    const etk::String& _test2,
                                    uint32_t _line) {
	if (_result == true) {
		return;
	}
	ETEST_ERROR("Detect an error: " << m_file << ":" << _line << ":");
	if (_test1 != _test1Value) {
		ETEST_ERROR("      have: " << _test1 << " = " << _test1Value);
	} else {
		ETEST_ERROR("      have: " << _test1);
	}
	if (_test2 != _test2Value) {
		ETEST_ERROR("    expect: " << _test2 << " = " << _test2Value);
	} else {
		ETEST_ERROR("    expect: " << _test2);
	}
	m_haveError = true;
	m_numberCheckFail++;
}

void etest::GenericTest::testCatchThrow(const etk::Exception& exeption, uint32_t _line) {
	ETEST_ERROR("Detect an error: " << m_file << ":" << _line << ": Catch etk::Exception");
	ETEST_ERROR("    what='" << exeption.what() << "'");
	ETEST_ERROR("    which='" << exeption.which() << "'");
	ETEST_ERROR("    file='" << exeption.file() << "'");
	ETEST_ERROR("    line=" << exeption.line());
	ETEST_ERROR("    function='" << exeption.function() << "'");
	
	m_haveError = true;
	m_numberCheckFail++;
}

void etest::GenericTest::testCatchThrow(uint32_t _line) {
	ETEST_ERROR("Detect an error: " << m_file << ":" << _line << ": Catch Unknow exception");
	m_haveError = true;
	m_numberCheckFail++;
}

void etest::GenericTest::clearLocal() {
	m_haveError = false;
	m_numberCheck = 0;
	m_numberCheckFail = 0;
	m_numberCheckNotImplemented = 0;
}
etest::GenericTest* etest::g_currentTest = null;

int32_t etest::runAllTest() {
	int32_t errorCount = 0;
	etk::Vector<etest::GenericTest*> runList = getListFiltered();
	etk::Vector<etk::String> listGroup = getListGroupSpecific(runList);
	ETEST_PRINT("[==========] Running " << runList.size() << " tests from " << listGroup.size() << " test group.");
	echrono::Steady tic = echrono::Steady::now();
	uint32_t nbTotalCheck = 0;
	uint32_t nbTotalCheckFail = 0;
	uint32_t nbTotalCheckNotImplemented = 0;
	for (auto &itGroup: listGroup) {
		int32_t count = 0;
		for (auto &it: getListOfTest()) {
			if (it->getTestGroup() == itGroup) {
				count++;
			}
		}
		ETEST_PRINT("[++++++++++] " << count << " test from " << itGroup << ":");
		uint32_t nbCheck = 0;
		uint32_t nbCheckFail = 0;
		uint32_t nbCheckNotImplemented = 0;
		echrono::Steady ticGroup = echrono::Steady::now();
		for (auto &it: runList) {
			if (it->getTestGroup() != itGroup) {
				continue;
			}
			bool localFail = false;
			#if ETK_MEMORY_CHECKER > 0
				uint64_t* memorySnapShoot = etk::memory::createSnapshoot();
			#endif
			{
				ETEST_PRINT("[ RUN      ] " << itGroup << "." << it->getTestName());
				it->clearLocal();
				g_currentTest = it;
				echrono::Steady ticTest = echrono::Steady::now();
				try {
					it->addCheck();
					it->run();
				} catch ( etk::Exception e ) {
					
					it->testCatchThrow(e, __LINE__);
				} catch ( ... ) {
					it->testCatchThrow(__LINE__);
				}
				echrono::Steady tocTest = echrono::Steady::now();
				g_currentTest = null;
				if (it->getError() == true) {
					ETEST_PRINT("[     FAIL ] " << itGroup << "." << it->getTestName() << " (" << (tocTest - ticTest) << ") " << it->getNumberCheckError() << " fails");
					errorCount++;
					localFail = true;
				} else {
					ETEST_PRINT("[       OK ] " << itGroup << "." << it->getTestName() << " (" << (tocTest - ticTest) << ")");
				}
				nbCheck += it->getNumberCheck();
				nbCheckFail += it->getNumberCheckError();
				nbCheckNotImplemented += it->getNumberCheckNotImplemented();
			}
			#if ETK_MEMORY_CHECKER > 0
				ETEST_DEBUG("[    MEM   ] CHECK memory properties");
				bool ret = etk::memory::checkSnapshoot(memorySnapShoot);
				etk::memory::clearSnapshoot(memorySnapShoot);
				memorySnapShoot = null;
				ETEST_DEBUG("[    MEM   ] CHECK memory properties (done)");
				nbCheck++;
				if (ret == false) {
					if (localFail == false) {
						errorCount++;
					}
					ETEST_PRINT("[     FAIL ] " << itGroup << "." << it->getTestName() << " ==> in memory LEAK test");
					nbCheckFail++;
				}
			#endif
		}
		echrono::Steady tocGroup = echrono::Steady::now();
		etk::String notImplemented = "";
		if (nbCheckNotImplemented != 0) {
			notImplemented = " / " + etk::toString(nbCheckNotImplemented) + " Not Implemented";
		}
		ETEST_PRINT("[++++++++++] " << count << " test [" << nbCheck << " check / " << nbCheckFail << " fails " << notImplemented << "] from " << itGroup << " (" << (tocGroup - ticGroup) << ")");
		nbTotalCheck += nbCheck;
		nbTotalCheckFail += nbCheckFail;
		nbTotalCheckNotImplemented += nbCheckNotImplemented;
	}
	echrono::Steady toc = echrono::Steady::now();
	etk::String notImplementedFull = "";
	if (nbTotalCheckNotImplemented != 0) {
		notImplementedFull = " / " + etk::toString(nbTotalCheckNotImplemented) + " Not Implemented";
	}
	ETEST_PRINT("[==========] All done [" << nbTotalCheck << " check / " << nbTotalCheckFail << " fails" << notImplementedFull << "] in " << (toc - tic));
	if (errorCount != 0) {
		ETEST_PRINT("[== FAIL ==] Have " << errorCount << " test fail ");
	}
	ETK_MEM_SHOW_LOG();
	if (showAtTheEnd == true) {
		listAllTestError();
	}
	return -errorCount;
}

uint32_t etest::registerTest(etest::GenericTest* _element) {
	if (_element == null) {
		return -1;
	}
	getListOfTest().pushBack(_element);
	return getListOfTest().size()-1;
}
/*
  7h55'30 [P] elog | Log in file: '/tmp/elog_etk-test.log'
[==========] Running 13 tests from 4 test cases.
[----------] Global test environment set-up.
[----------] 1 test from TestEtkColor
[ RUN      ] TestEtkColor.RGBA8
[       OK ] TestEtkColor.RGBA8 (0 ms)
[----------] 1 test from TestEtkColor (0 ms total)
[----------] 1 test from TestEtkFSNode
[ RUN      ] TestEtkFSNode.checkType
[       OK ] TestEtkFSNode.checkType (1 ms)
[----------] 1 test from TestEtkFSNode (1 ms total)
[----------] 8 tests from TestEtkHash
[ RUN      ] TestEtkHash.Creation
[       OK ] TestEtkHash.Creation (0 ms)
[ RUN      ] TestEtkHash.AddElement
[       OK ] TestEtkHash.AddElement (0 ms)
[ RUN      ] TestEtkHash.OverWriteElement
[       OK ] TestEtkHash.OverWriteElement (0 ms)
[ RUN      ] TestEtkHash.RemoveElement
[       OK ] TestEtkHash.RemoveElement (0 ms)
[ RUN      ] TestEtkHash.ExistElement
[       OK ] TestEtkHash.ExistElement (0 ms)
[ RUN      ] TestEtkHash.clear
[       OK ] TestEtkHash.clear (0 ms)
[ RUN      ] TestEtkHash.getKey
[       OK ] TestEtkHash.getKey (0 ms)
[ RUN      ] TestEtkHash.getKeys
[       OK ] TestEtkHash.getKeys (0 ms)
[----------] 8 tests from TestEtkHash (0 ms total)
[----------] 3 tests from TestSTDSharedPtr
[ RUN      ] TestSTDSharedPtr.testBaseLocal
create Example [0]
Remove Example [0]
[       OK ] TestSTDSharedPtr.testBaseLocal (0 ms)
[ RUN      ] TestSTDSharedPtr.testBaseShared
create Example [1]
Remove Example [1]
[       OK ] TestSTDSharedPtr.testBaseShared (0 ms)
[ RUN      ] TestSTDSharedPtr.testBaseSharedDouble
create Example [2]
Remove Example [2]
[       OK ] TestSTDSharedPtr.testBaseSharedDouble (0 ms)
[----------] 3 tests from TestSTDSharedPtr (0 ms total)
[----------] Global test environment tear-down
[==========] 13 tests from 4 test cases ran. (2 ms total)
[  PASSED  ] 13 tests.
*/

