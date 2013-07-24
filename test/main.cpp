/**
 * @author Edouard DUPIN
 * 
 * @copyright 2011, Edouard DUPIN, all right reserved
 * 
 * @license BSD v3 (see license file)
 */

#include <etk/Debug.h>
#include <etk/Vector.h>
#include <etk/UString.h>
#include <etk/Hach.h>
#include <etk/os/FSNode.h>
#include <etk/DebugInternal.h>
#include <etk/archive/Archive.h>

#undef __class__
#define __class__	"etktest"

void testVector(void)
{
	
}

void testUniChar(void)
{
	
}

void testUString(void)
{
	
	for(int32_t iii=0; iii<64; iii++) {
		int64_t kkk=((int64_t)1)<<iii;
		etk::UString plop(kkk, etk::UString::printModeBinary);
		TK_DEBUG(" test : " << plop);
	}
	for(int32_t iii=0; iii<64; iii++) {
		int64_t kkk=((int64_t)1)<<iii;
		etk::UString plop(kkk, etk::UString::printModeOctal);
		TK_DEBUG(" test : " << plop);
	}
	
	for(int32_t iii=0; iii<64; iii++) {
		int64_t kkk=((int64_t)1)<<iii;
		etk::UString plop(kkk, etk::UString::printModeDecimal);
		TK_DEBUG(" test : " << plop);
		int64_t resTest = plop.ToInt32();
		TK_DEBUG(" test : " << resTest);
	}
	
	for(int32_t iii=0; iii<64; iii++) {
		int64_t kkk=((int64_t)1)<<iii;
		etk::UString plop(kkk, etk::UString::printModeHexadecimal);
		TK_DEBUG(" test : " << plop);
	}
}

void testHash(void)
{
	TK_INFO("==> Start test of Hach table");
	etk::Hash<etk::UString> testData;
	testData.Add("TEST", "testData");
	testData.Add("TEST", "testData333");
	testData.Add("TEST2", "22222222222222222");
	testData.Add("TEST4", "4444444444444444444");
	testData.Add("TEST3", "3333333333");
	testData.Add("TEST1", "11111111111");
	testData.Add("TEST55", "555555555555555((((5555");
	TK_INFO(" count =" << testData.Size());
	for (int32_t iii=0; iii< testData.Size(); iii++) {
		TK_INFO(" id=" << iii << " key='" << testData.GetKey(iii) << "' val='" << testData.GetValue(iii) << "'");
	}
	TK_INFO(" direct acces at the key  key='TEST4' val='" << testData["TEST4"] << "'");
	TK_INFO("==> End test of Hach table");
}

void testFSNode(void)
{
	TK_INFO("==> Start test of FSNode");
	etk::UString fileName("USERDATA:myFileTest.txt");
	etk::FSNode myNodeTest1(fileName);
	TK_INFO("********************************************");
	TK_INFO("** Filename=\"" << fileName << "\"");
	TK_INFO("********************************************");
	TK_INFO("      GetNameFolder()      =\"" <<myNodeTest1.GetNameFolder() << "\"");
	TK_INFO("      GetName()            =\"" <<myNodeTest1.GetName() << "\"");
	TK_INFO("      GetNameFile()        =\"" <<myNodeTest1.GetNameFile() << "\"");
	TK_INFO("      GetRelativeFolder()  =\"" <<myNodeTest1.GetRelativeFolder() << "\"");
	TK_INFO("      exist                =" <<myNodeTest1.Exist());
	if (true==myNodeTest1.Exist()) {
		TK_ERROR(" ==> remove the file ==> bad for the test");
	} else {
		TK_INFO("      Display time when file does not exist :");
		TK_INFO("          TimeCreatedString()  =\"" <<myNodeTest1.TimeCreatedString() << "\"");
		TK_INFO("          TimeModifiedString() =\"" <<myNodeTest1.TimeModifiedString() << "\"");
		TK_INFO("          TimeAccessedString() =\"" <<myNodeTest1.TimeAccessedString() << "\"");
	}
	myNodeTest1.Touch();
	if (false==myNodeTest1.Exist()) {
		TK_ERROR(" ==> Error, can not create the file ....");
	} else {
		TK_INFO("      Display time when file does exist :");
		TK_INFO("          TimeCreatedString()  =\"" <<myNodeTest1.TimeCreatedString() << "\"");
		TK_INFO("          TimeModifiedString() =\"" <<myNodeTest1.TimeModifiedString() << "\"");
		TK_INFO("          TimeAccessedString() =\"" <<myNodeTest1.TimeAccessedString() << "\"");
	}
	// Try remove the file : 
	myNodeTest1.Remove();
	if (true==myNodeTest1.Exist()) {
		TK_ERROR(" ==> The file might be removed ==> but it is not the case ...");
	} else {
		TK_INFO(" ==> The file is removed");
	}
	TK_INFO("********************************************");
	
	
	TK_INFO("==> Stop test of FSNode");
}


void testArchive(void)
{
	TK_INFO("==> Start test of archive");
	etk::Archive* tmpArchive = etk::Archive::Load("testzip.zip");
	tmpArchive->Display();
	
	TK_INFO("==> End test of archive");
}

/*
void testDimension(void)
{
	TK_INFO("==> test of Dimension (START)");
	
	ewol::Dimension myDimention(vec2(5,5), ewol::Dimension::Centimeter);
	TK_INFO("    set dimension at : " << myDimention);
	TK_INFO("    set dimension at : " << myDimention.GetCentimeter() << " cm");
	TK_INFO("    set dimension at : " << myDimention.GetMillimeter() << " mm");
	TK_INFO("    set dimension at : " << myDimention.GetKilometer() << " km");
	TK_INFO("    set dimension at : " << myDimention.GetMeter() << " m");
	TK_INFO("    set dimension at : " << myDimention.GetInch() << " Inch");
	TK_INFO("    set dimension at : " << myDimention.GetFoot() << " ft");
	TK_INFO("    set dimension at : " << myDimention.GetPourcent() << " %");
	TK_INFO("    set dimension at : " << myDimention.GetPixel() << " px");
	
	TK_INFO("==> test of Dimension (STOP)");
	exit(0);
}
*/
int main(int argc, const char *argv[])
{
	// the only one init for etk:
	GeneralDebugSetLevel(etk::LOG_LEVEL_VERBOSE);
	//testVector();
	//testUniChar();
	//testUString();
	testHash();
	//testFSNode();
	//testDimension();
	testArchive();
	return 0;
}
