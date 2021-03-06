/**
 * @author Edouard DUPIN
 * @copyright 2011, Edouard DUPIN, all right reserved
 * @license MPL-2 (see license file)
 */
#include <etk/types.hpp>
#include <etk/io/Interface.hpp>


char etk::io::Interface::get() {
	char data='\0';
	if (read(&data, 1, 1)!=1) {
		return '\0';
	}
	return data;
}

bool etk::io::Interface::gets(etk::String& _output) {
	_output.clear();
	char tmp = get();
	while (    tmp != '\0'
	        && tmp != '\n') {
		_output += tmp;
		tmp = get();
	}
	if (    tmp == '\0'
	     && _output.isEmpty() == true) {
		return false;
	}
	return true;
}

bool etk::io::Interface::put(char _input) {
	if (write(&_input, 1, 1) == 1) {
		return true;
	}
	return false;
}

bool etk::io::Interface::puts(const etk::String& _input) {
	if (write((void*)_input.c_str(), 1, _input.size()) == (int64_t)_input.size()) {
		return true;
	}
	return false;
}

etk::io::Interface& etk::io::Interface::operator<< (const etk::Stream& _data) {
	write(_data.c_str(), 1, _data.size());
	return *this;
}
etk::io::Interface& etk::io::Interface::operator<< (const etk::String& _data) {
	write(_data.c_str(), 1, _data.size());
	return *this;
}
etk::io::Interface& etk::io::Interface::operator<< (const char* _data) {
	write(_data, 1, strlen(_data));
	return *this;
}
etk::io::Interface& etk::io::Interface::operator<< (const int32_t _data) {
	etk::String sss = etk::toString(_data);
	write(sss.c_str(), 1, sss.size());
	return *this;
}
etk::io::Interface& etk::io::Interface::operator<< (const uint32_t _data) {
	etk::String sss = etk::toString(_data);
	write(sss.c_str(), 1, sss.size());
	return *this;
}
etk::io::Interface& etk::io::Interface::operator<< (const float _data) {
	etk::String sss = etk::toString(_data);
	write(sss.c_str(), 1, sss.size());
	return *this;
}