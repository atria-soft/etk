/**
 * @author Edouard DUPIN
 * 
 * @copyright 2011, Edouard DUPIN, all right reserved
 * 
 * @license BSD v3 (see license file)
 */

#include <etk/UString.h>
#include <etk/os/Memory.h>
#include <etk/unicode.h>

int32_t strlen(const uniChar_t * data)
{
	if (NULL == data) {
		return 0;
	}
	int32_t iii=0;
	while (*data != 0) {
		data++;
		iii++;
		if (iii > 0x7FFFFFF0) {
			return iii;
		}
	}
	return iii;
}

#undef __class__
#define __class__	"etk::UString"

etk::CCout& etk::operator <<(etk::CCout &os, const etk::UString &obj)
{
	etk::Vector<char> output_UTF8;
	unicode::convertUnicodeToUtf8(obj.m_data, output_UTF8);
	output_UTF8.PushBack('\0');
	os << &output_UTF8[0];
	return os;
}

etk::UString::~UString(void)
{
	//m_data.Clear();
	//m_dataUtf8.Clear();
}


etk::UString::UString(void)
{
	//TK_INFO("new etk::UString()");
	m_data.Clear();
	m_data.PushBack('\0');
}


etk::UString::UString(const char* inputData, int32_t len)
{
	m_data.Clear();
	m_data.PushBack('\0');
	Set(inputData, len);
}


etk::UString::UString(const uniChar_t* inputData, int32_t len)
{
	m_data.Clear();
	m_data.PushBack('\0');
	Set(inputData, len);
}
/*
etk::UString::UString(const uniChar_t inputData)
{
	m_data.Clear();
	m_data.PushBack('\0');
	Set(&inputData, 1);
}
*/

void etk::UString::Set(const char * inputData, int32_t len)
{
	if (NULL == inputData) {
		// nothing to add ... ==> clear all the data ...
		Clear();
		return;
	}
	// overwrite the len if needed : 
	if ((-1) == len) {
		len = strlen(inputData);
	}
	// convert the string
	etk::Vector<char> tmpChar;
	for (int32_t iii=0; iii<len; iii++) {
		tmpChar.PushBack(inputData[iii]);
	}
	// add it ...
	if (len != 0) {
		// remove the last '\0'
		m_data.PopBack();
		// copy the data ...
		unicode::convertUtf8ToUnicode(tmpChar, m_data);
		// add the last '\0'
		m_data.PushBack('\0');
	}
}

void etk::UString::Set(const uniChar_t * inputData, int32_t len)
{
	if (NULL == inputData) {
		// nothing to add ... ==> clear all the data ...
		Clear();
		return;
	}
	// overwrite the len if needed :
	if ((-1) == len) {
		len = strlen(inputData);
	}
	
	if (len != 0) {
		// remove the last '\0'
		m_data.PopBack();
		// copy the data ...
		m_data.PushBack(inputData, len);
		// add the last '\0'
		m_data.PushBack('\0');
	}
}


etk::UString::UString(const bool _inputData)
{
	m_data.Clear();
	if (true == _inputData) {
		m_data.PushBack('t');
		m_data.PushBack('r');
		m_data.PushBack('u');
		m_data.PushBack('e');
	} else {
		m_data.PushBack('f');
		m_data.PushBack('a');
		m_data.PushBack('l');
		m_data.PushBack('s');
		m_data.PushBack('e');
	}
	m_data.PushBack('\0');
}

etk::UString::UString(const char inputData)
{
	m_data.Clear();
	m_data.PushBack((uint32_t)inputData);
	m_data.PushBack('\0');
}


etk::UString::UString(int inputData, const char* mode)
{
	char tmpVal[256];
	// generate the UString : 
	sprintf(tmpVal, mode, inputData);
	// set the internal data : 
	m_data.Clear();
	m_data.PushBack('\0');
	Set(tmpVal);
}


etk::UString::UString(unsigned int inputData, const char* mode)
{
	char tmpVal[256];
	// generate the UString : 
	sprintf(tmpVal, mode, inputData);
	// set the internal data : 
	m_data.Clear();
	m_data.PushBack('\0');
	Set(tmpVal);
}

etk::UString::UString(const float inputData)
{
	char tmpVal[256];
	// generate the UString : 
	sprintf(tmpVal, "%f", inputData);
	// set the internal data : 
	m_data.Clear();
	m_data.PushBack('\0');
	Set(tmpVal);
}

etk::UString::UString(const double inputData)
{
	char tmpVal[256];
	// generate the UString : 
	sprintf(tmpVal, "%lf", inputData);
	// set the internal data : 
	m_data.Clear();
	m_data.PushBack('\0');
	Set(tmpVal);
}

etk::UString::UString(const etk::UString &etkS)
{
	//etk_INFO("Constructeur de recopie");
	m_data = etkS.m_data;
}


const etk::UString& etk::UString::operator= (const etk::UString &etkS )
{
	//TK_INFO("OPERATOR de recopie");
	if( this != &etkS ) {
		m_data = etkS.m_data;
	}
	return *this;
}


const etk::UString& etk::UString::operator= (etk::Vector<char> inputData)
{
	etk::Vector<uniChar_t> output_Unicode;
	unicode::convertUtf8ToUnicode(inputData, output_Unicode);
	*this = output_Unicode;
	return *this;
}

const etk::UString& etk::UString::operator= (etk::Vector<int8_t> inputData)
{
	etk::Vector<uniChar_t> output_Unicode;
	unicode::convertUtf8ToUnicode(inputData, output_Unicode);
	*this = output_Unicode;
	return *this;
}


const etk::UString& etk::UString::operator= (etk::Vector<uniChar_t> inputData)
{
	m_data = inputData;
	if (m_data.Size()>0) {
		if (m_data[m_data.Size()-1] != '\0') {
			m_data.PushBack('\0');
		}
	}
	//TK_DEBUG("m_dataLen="<<m_dataLen << " m_dataLenUTF8="<<m_dataLenUTF8 << " description=" << m_data);
	return *this;
}


uniChar_t changeOrder(uniChar_t elemA)
{
	if (elemA >= 'A' && elemA <= 'Z') {
		return (elemA - (uniChar_t)'A')*2 + 'A';
	}
	if (elemA >= 'a' && elemA <= 'z') {
		return (elemA - (uniChar_t)'a')*2 + 'A' + 1;
	}
	if (elemA >= ':' && elemA <= '@') {
		return elemA + 52;
	}
	if (elemA >= '[' && elemA <= '`') {
		return elemA +26;
	}
	return elemA;
}


bool etk::UString::operator> (const etk::UString& etkS) const
{
	if( this != &etkS ) {
		for (int32_t iii=0; iii < m_data.Size() && iii < etkS.m_data.Size(); iii++) {
			//TK_DEBUG("    compare : '" << (char)m_data[iii] << "'>'" << (char)etkS.m_data[iii] << "' ==> " << changeOrder(m_data[iii]) << ">" << changeOrder(etkS.m_data[iii]) << "");
			uniChar_t elemA = changeOrder(m_data[iii]);
			uniChar_t elemB = changeOrder(etkS.m_data[iii]);
			if (elemA != elemB) {
				if (elemA > elemB) {
					return true;
				}
				return false;
			}
		}
		if (m_data.Size() > etkS.m_data.Size()) {
			return true;
		}
	}
	return false;
}

bool etk::UString::operator>= (const etk::UString& etkS) const
{
	if( this != &etkS ) {
		for (int32_t iii=0; iii < m_data.Size() && iii < etkS.m_data.Size(); iii++) {
			uniChar_t elemA = changeOrder(m_data[iii]);
			uniChar_t elemB = changeOrder(etkS.m_data[iii]);
			if (elemA != elemB) {
				if (elemA > elemB) {
					return true;
				}
				return false;
			}
		}
		if (m_data.Size() >= etkS.m_data.Size()) {
			return true;
		}
	}
	return false;
}

bool etk::UString::operator< (const etk::UString& etkS) const
{
	if( this != &etkS ) {
		for (int32_t iii=0; iii < m_data.Size() && iii < etkS.m_data.Size(); iii++) {
			uniChar_t elemA = changeOrder(m_data[iii]);
			uniChar_t elemB = changeOrder(etkS.m_data[iii]);
			if (elemA != elemB) {
				if (elemA < elemB) {
					return true;
				}
				return false;
			}
		}
		if (m_data.Size() < etkS.m_data.Size()) {
			return true;
		}
	}
	return false;
}

bool etk::UString::operator<= (const etk::UString& etkS) const
{
	if( this != &etkS ) {
		for (int32_t iii=0; iii < m_data.Size() && iii < etkS.m_data.Size(); iii++) {
			uniChar_t elemA = changeOrder(m_data[iii]);
			uniChar_t elemB = changeOrder(etkS.m_data[iii]);
			if (elemA != elemB) {
				if (elemA < elemB) {
					return true;
				}
				return false;
			}
		}
		if (m_data.Size() <= etkS.m_data.Size()) {
			return true;
		}
	}
	return false;
}


bool etk::UString::operator== (const etk::UString& etkS) const
{
	if( this != &etkS ) {
		if (etkS.m_data.Size() != m_data.Size()) {
			//TK_DEBUG(" not the same size : " << etkS.m_data.Size() << "!=" << m_data.Size());
			return false;
		}
		for (int32_t iii= 0; iii<m_data.Size(); iii++) {
			//TK_DEBUG("     check : " << etkS.m_data[iii] << "!=" << m_data[iii]);
			if (etkS.m_data[iii]!= m_data[iii]){
				return false;
			}
		}
		return true;
	}
	return true;
}

bool etk::UString::CompareNoCase(const etk::UString& etkS) const
{
	if( this != &etkS ) {
		if (etkS.m_data.Size() != m_data.Size()) {
			//TK_DEBUG(" not the same size : " << etkS.m_data.Size() << "!=" << m_data.Size());
			return false;
		}
		for (int32_t iii= 0; iii<m_data.Size(); iii++) {
			//TK_DEBUG("     check : " << etkS.m_data[iii] << "!=" << m_data[iii]);
			uniChar_t in1 = etkS.m_data[iii];
			uniChar_t in2 = m_data[iii];
			if(    in1>=(uniChar_t)'A'
			    && in1<=(uniChar_t)'Z') {
				in1 += (uniChar_t)'a' - (uniChar_t)'A';
			}
			if(    in2>=(uniChar_t)'A'
			    && in2<=(uniChar_t)'Z') {
				in2 += (uniChar_t)'a' - (uniChar_t)'A';
			}
			if (in1!=in2){
				return false;
			}
		}
		return true;
	}
	return true;
}


bool etk::UString::operator!= (const etk::UString& etkS) const
{
	return !(*this == etkS);
}


const etk::UString& etk::UString::operator+= (const etk::UString &etkS)
{
	if (0 < etkS.Size()) {
		// remove the last '\0'
		m_data.PopBack();
		// copy the data ...
		m_data += etkS.m_data;
		// This previous include the \0 in case of the 2 UString are different...
		if( this == &etkS ) {
			// add the removed end UString
			m_data.PushBack('\0');
		}
	}
	return *this;
}


etk::UString etk::UString::operator+ (const etk::UString &etkS) const
{
	etk::UString temp;
	//TK_INFO("        UString(arg) : \"" << etkS.m_data << "\"");
	//TK_INFO("        UString(direct) : \"" << m_data << "\"");
	temp += *this;
	temp += etkS;
	return temp;
}


bool etk::UString::IsEmpty(void) const
{
	if(1 >= m_data.Size() ) {
		return true;
	} else {
		return false;
	}
}


int32_t etk::UString::Size(void) const
{
	if (m_data.Size() == 0) {
		return 0;
	} else {
		return m_data.Size() - 1;
	}
}


void etk::UString::Add(int32_t currentID, const char* inputData)
{
	etk::UString tmpString(inputData);
	Add(currentID, tmpString.pointer() );
}


void etk::UString::Add(int32_t currentID, const uniChar_t* inputData)
{
	// get the input lenght
	int32_t len = strlen(inputData);
	if (0 == len) {
		TK_WARNING("no data to add on the current UString");
		return;
	} else if (currentID < 0) {
		TK_WARNING("Curent ID(" << currentID << ") < 0   ==> Add at the start");
		currentID = 0;
	} else if (currentID > Size() ) {
		TK_ERROR("Curent ID(" << currentID << ") > maxSize ... (" << Size() << ")  ==> add at the end ...");
		m_data.PushBack(inputData, len);
		return;
	}
	m_data.Insert(currentID, inputData, len);
}


void etk::UString::Add(int32_t currentID, const uniChar_t inputData)
{
	uniChar_t data[2];
	data[0] = inputData;
	data[1] = 0;
	Add(currentID, data);
}


void etk::UString::Remove(int32_t currentID, int32_t len)
{
	if (0 >= len) {
		TK_ERROR("no data to remove on the current UString");
		return;
	}
	// TODO : check the size of the data
	m_data.EraseLen(currentID, len);
}


void etk::UString::Clear(void)
{
	m_data.Clear();
	m_data.PushBack('\0');
}


int32_t etk::UString::FindForward(const char element, int32_t startPos) const
{
	return FindForward((uniChar_t)element, startPos);
}


int32_t etk::UString::FindForward(const uniChar_t element, int32_t startPos) const
{
	if (startPos < 0) {
		startPos = 0;
	} else if (startPos >= Size() ) {
		return -1;
	}
	for (int32_t iii=startPos; iii< Size(); iii++) {
		if (m_data[iii] == element) {
			return iii;
		}
	}
	return -1;
}


int32_t etk::UString::FindBack(const char element, int32_t startPos) const
{
	return FindBack((uniChar_t)element, startPos);
}


int32_t etk::UString::FindBack(const uniChar_t element, int32_t startPos) const
{
	if (startPos < 0) {
		return -1;
	} else if (startPos >= Size() ) {
		startPos = Size();
	}
	for (int32_t iii=startPos; iii>=0; iii--) {
		if (m_data[iii] == element) {
			return iii;
		}
	}
	return -1;
}


etk::UString etk::UString::Extract(int32_t posStart, int32_t posEnd) const
{
	etk::UString out;
	if (posStart < 0) {
		posStart = 0;
	} else if (posStart >= Size() ) {
		return out;
	}
	if (posEnd < 0) {
		return out;
	} else if (posEnd >= Size() ) {
		posEnd = Size();
	}
	out.m_data = m_data.Extract(posStart, posEnd);
	out.m_data.PushBack('\0');
	return out;
}


etk::Vector<uniChar_t> etk::UString::GetVector(void)
{
	etk::Vector<uniChar_t> out = m_data;
	out.PopBack();
	return out;
}


bool etk::UString::StartWith(const etk::UString& data, bool caseSensitive) const
{
	if (data.Size() == 0) {
		return false;
	}
	if (data.Size() > Size()) {
		return false;
	}
	if (true == caseSensitive) {
		for (int32_t iii=0; iii<data.Size(); iii++) {
			if (data[iii] != m_data[iii]) {
				return false;
			}
		}
	} else {
		for (int32_t iii=0; iii<data.Size(); iii++) {
			if (data[iii] != m_data[iii]) {
				uniChar_t in1 = data[iii];
				uniChar_t in2 = m_data[iii];
				if (in1 <= (uniChar_t)'Z' && in1 >= (uniChar_t)'A') {
					in1 = in1 + (uniChar_t)'a' - (uniChar_t)'A';
				}
				if (in2 <= 'Z' && in2 >= 'A') {
					in2 = in2 + (uniChar_t)'a' - (uniChar_t)'A';
				}
				if (in1 != in2) {
					return false;
				}
			}
		}
	}
	return true;
}


bool etk::UString::EndWith(const etk::UString& data, bool caseSensitive) const
{
	if (data.Size() == 0) {
		return false;
	}
	if (data.Size() > Size()) {
		return false;
	}
	if (true == caseSensitive) {
		for( int32_t iii=Size()-1, jjj=data.Size()-1;
		     iii>=0 && jjj>=0;
		     iii--, jjj--) {
			if (data[jjj] != m_data[iii]) {
				return false;
			}
		}
	} else {
		for( int32_t iii=Size()-1, jjj=data.Size()-1;
		     iii>=0 && jjj>=0;
		     iii--, jjj--) {
			if (data[jjj] != m_data[iii]) {
				uniChar_t in1 = data[jjj];
				uniChar_t in2 = m_data[iii];
				if (in1 <= (uniChar_t)'Z' && in1 >= (uniChar_t)'A') {
					in1 = in1 + (uniChar_t)'a' - (uniChar_t)'A';
				}
				if (in2 <= 'Z' && in2 >= 'A') {
					in2 = in2 + (uniChar_t)'a' - (uniChar_t)'A';
				}
				if (in1 != in2) {
					return false;
				}
			}
		}
	}
	return true;
}


etk::Char etk::UString::c_str(void) const
{
	etk::Char tmpVar;
	etk::Vector<char> tmpData;
	// UTF8 generation :
	tmpData.Clear();
	unicode::convertUnicodeToUtf8(m_data, tmpData);
	tmpVar.SetValue(tmpData);
	return tmpVar;
}

bool etk::UString::ToBool(void) const
{
	if(    true == CompareNoCase("true")
	    || *this == "1") {
		return true;
	}
	return false;
}
int64_t etk::UString::ToInt64(void) const
{
	int64_t ret=0;
	bool isOdd = false;
	for (int32_t iii=0; iii<m_data.Size(); iii++) {
		if(    iii==0
		    && (    m_data[iii] == '-'
		         || m_data[iii] == '+') ) {
			if(m_data[iii] == '-') {
				isOdd = true;
			}
		} else {
			if (m_data[iii]>='0' && m_data[iii]<='9') {
				int32_t val = m_data[iii] - '0';
				ret = ret*10 + val;
			} else {
				break;
			}
		}
	}
	if (isOdd == true) {
		ret *= -1;
	}
	return ret;
}

int32_t etk::UString::ToInt32(void) const
{
	int64_t parse = ToInt64();
	return etk_avg((int64_t)INT32_MIN, parse, (int64_t)INT32_MAX);
}
int16_t etk::UString::ToInt16(void) const
{
	int64_t parse = ToInt64();
	return etk_avg((int64_t)INT16_MIN, parse, (int64_t)INT16_MAX);
}
int8_t etk::UString::ToInt8(void) const
{
	int64_t parse = ToInt64();
	return etk_avg((int64_t)INT8_MIN, parse, (int64_t)INT8_MAX);
}

double etk::UString::ToDouble(void) const
{
	double ret=0;
	bool isOdd = false;
	int32_t dotPos = -1;
	for (int32_t iii=0; iii<m_data.Size(); iii++) {
		if(    iii==0
		    && (    m_data[iii] == '-'
		         || m_data[iii] == '+') ) {
			if(m_data[iii] == '-') {
				isOdd = true;
			}
		} else {
			if (dotPos == -1) {
				if (m_data[iii] == '.') {
					dotPos = 1;
					// jump at the next element
					continue;
				}
			}
			if (m_data[iii]>='0' && m_data[iii]<='9') {
				int32_t val = m_data[iii] - '0';
				double val2 = val;
				if (dotPos>=0) {
					ret += (val2*(((double)dotPos)*0.1));
					dotPos++;
				} else {
					ret = ret*10.0 + val2;
				}
			} else {
				break;
			}
		}
	}
	if (isOdd == true) {
		ret *= -1.0;
	}
	return ret;
}


float etk::UString::ToFloat(void) const
{
	return (float)ToDouble();
}
