/**
 * @author Edouard DUPIN
 * @copyright 2011, Edouard DUPIN, all right reserved
 * @license MPL-2 (see license file)
 */
#pragma once

#include <etk/types.hpp>

namespace etk {
	class String;
	/**
	 * @brief string class ...
	 */
	class Stream {
		private:
			// remove dependency of etk::String and vector
			etk::String* m_data;
		public:
			Stream(size_t _basicSize=0);
			~Stream();
			Stream& operator<< (const char* _data);
			Stream& operator<< (bool _data);
			Stream& operator<< (int8_t _data);
			Stream& operator<< (int16_t _data);
			Stream& operator<< (int32_t _data);
			Stream& operator<< (int64_t _data);
			Stream& operator<< (uint8_t _data);
			Stream& operator<< (uint16_t _data);
			Stream& operator<< (uint32_t _data);
			Stream& operator<< (uint64_t _data);
			#if    defined(__TARGET_OS__MacOs) \
			    || defined(__TARGET_OS__IOs)
				Stream& operator<< (size_t _data);
			#endif
			Stream& operator<< (float _data);
			Stream& operator<< (double _data);
			Stream& operator<< (etk::NullPtr _data);
			const char* c_str() const;
			const etk::String& str() const;
			const size_t size() const;
	};
	
}
