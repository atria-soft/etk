/**
 * @author Edouard DUPIN
 * 
 * @copyright 2011, Edouard DUPIN, all right reserved
 * 
 * @license BSD v3 (see license file)
 */

#ifndef __ETK_ARCHIVE_ZIP_H__
#define __ETK_ARCHIVE_ZIP_H__

#include <etk/archive/Archive.h>
#include <minizip/unzip.h>

namespace etk
{
	namespace archive
	{
		class Zip : public etk::Archive
		{
			private:
				unzFile m_ctx; //!< mini zip context
				unz_global_info m_info; //!< global information of the Zip
			public:
				Zip(const etk::UString& _fileName);
				virtual ~Zip(void);
		};
	};
};

#endif

