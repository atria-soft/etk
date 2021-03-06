/** @file
 * @author Edouard DUPIN
 * @copyright 2018, Edouard DUPIN, all right reserved
 * @license MPL v2.0 (see license file)
 */
#pragma once

#include <etk/types.hpp>
#include <etk/uri/provider/provider.hpp>

namespace etk {
	namespace theme {
		class ProviderTheme : public etk::uri::provider::Interface {
			protected:
				etk::Path m_offset;
				etk::Path m_offsetDefault;
			public:
				ProviderTheme();
				ProviderTheme(const etk::Path& _offset, const etk::Path& _offsetDefault);
			public:
				ememory::SharedPtr<etk::io::Interface> create(const etk::Uri& _uri) override;
				bool exist(const etk::Uri& _uri) override;
				bool isDirectory(const etk::Uri& _uri) override;
				bool isFile(const etk::Uri& _uri) override;
				bool isSymLink(const etk::Uri& _uri) override;
				etk::Vector<etk::Uri> list(const etk::Uri& _uri) override;
				etk::Vector<etk::Uri> listRecursive(const etk::Uri& _uri) override;
		};
	}
}
