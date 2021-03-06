/** @file
 * @author Edouard DUPIN
 * @copyright 2018, Edouard DUPIN, all right reserved
 * @license MPL v2.0 (see license file)
 */
#include <etk/uri/provider/provider.hpp>
#include <etk/uri/provider/ProviderFile.hpp>
#include <etk/Map.hpp>
#include <etk/io/File.hpp>
#include <etk/uri/uri.hpp>
#include <etk/debug.hpp>

namespace etk {
	namespace uri {
		namespace provider {
			etk::Map<etk::String, ememory::SharedPtr<etk::uri::provider::Interface>>& getProviders();
		}
	}
}

bool etk::uri::exist(const etk::Uri& _uri) {
	etk::String scheme = _uri.getScheme();
	if (scheme.empty() == true) {
		scheme = "FILE";
	}
	if (etk::uri::provider::getProviders().exist(scheme) == false) {
		return false;
	}
	return etk::uri::provider::getProviders()[scheme]->exist(_uri);
}

bool etk::uri::isDirectory(const etk::Uri& _uri) {
	etk::String scheme = _uri.getScheme();
	if (scheme.empty() == true) {
		scheme = "FILE";
	}
	if (etk::uri::provider::getProviders().exist(scheme) == false) {
		return false;
	}
	return etk::uri::provider::getProviders()[scheme]->isDirectory(_uri);
}

bool etk::uri::isFile(const etk::Uri& _uri) {
	etk::String scheme = _uri.getScheme();
	if (scheme.empty() == true) {
		scheme = "FILE";
	}
	if (etk::uri::provider::getProviders().exist(scheme) == false) {
		return false;
	}
	return etk::uri::provider::getProviders()[scheme]->isFile(_uri);
}

bool etk::uri::isSymLink(const etk::Uri& _uri) {
	etk::String scheme = _uri.getScheme();
	if (scheme.empty() == true) {
		scheme = "FILE";
	}
	if (etk::uri::provider::getProviders().exist(scheme) == false) {
		return false;
	}
	return etk::uri::provider::getProviders()[scheme]->isSymLink(_uri);
}

uint64_t etk::uri::fileSize(const etk::Uri& _uri) {
	etk::String scheme = _uri.getScheme();
	if (scheme.empty() == true) {
		scheme = "FILE";
	}
	if (etk::uri::provider::getProviders().exist(scheme) == false) {
		return 0;
	}
	return etk::uri::provider::getProviders()[scheme]->fileSize(_uri);
}

etk::Vector<etk::Uri> etk::uri::list(const etk::Uri& _uri) {
	etk::String scheme = _uri.getScheme();
	if (scheme.empty() == true) {
		scheme = "FILE";
	}
	if (etk::uri::provider::getProviders().exist(scheme) == false) {
		return etk::Vector<etk::Uri>();
	}
	return etk::uri::provider::getProviders()[scheme]->list(_uri);
}

etk::Vector<etk::Uri> etk::uri::listRecursive(const etk::Uri& _uri) {
	etk::String scheme = _uri.getScheme();
	if (scheme.empty() == true) {
		scheme = "FILE";
	}
	if (etk::uri::provider::getProviders().exist(scheme) == false) {
		return etk::Vector<etk::Uri>();
	}
	return etk::uri::provider::getProviders()[scheme]->listRecursive(_uri);
}
ememory::SharedPtr<etk::io::Interface> etk::uri::get(const etk::Uri& _uri) {
	TK_WARNING("uri get: " << _uri);
	etk::String scheme = _uri.getScheme();
	if (scheme.empty() == true) {
		scheme = "FILE";
	}
	if (etk::uri::provider::getProviders().exist(scheme) == false) {
		TK_ERROR("lklklk   " << scheme << "   " << _uri << "    " << etk::uri::provider::getProviders().getKeys());
		return null;
	}
	if (_uri.getPath().getString().size() == 0) {
		TK_WARNING("uri:" << _uri << " Have NO path");
	}
	return etk::uri::provider::getProviders()[scheme]->create(_uri);
}

bool etk::uri::canMove(const etk::Uri& _uri) {
	etk::String scheme = _uri.getScheme();
	if (scheme.empty() == true) {
		scheme = "FILE";
	}
	if (etk::uri::provider::getProviders().exist(scheme) == false) {
		return false;
	}
	return etk::uri::provider::getProviders()[scheme]->canMove();
}

bool etk::uri::move(const etk::Uri& _uriSource, const etk::Uri& _uriDestination) {
	etk::String scheme = _uriSource.getScheme();
	if (scheme.empty() == true) {
		scheme = "FILE";
	}
	etk::String scheme2 = _uriDestination.getScheme();
	if (scheme2.empty() == true) {
		scheme2 = "FILE";
	}
	if (scheme != scheme2) {
		TK_ERROR("Can not move 2 uri between 2 model of resource... " << _uriSource << " => " << _uriDestination);
		return false;
	}
	if (etk::uri::provider::getProviders().exist(scheme) == false) {
		return false;
	}
	if (etk::uri::provider::getProviders()[scheme]->canMove() == false) {
		return false;
	}
	return etk::uri::provider::getProviders()[scheme]->move(_uriSource, _uriDestination);
}

bool etk::uri::canCopy(const etk::Uri& _uri) {
	etk::String scheme = _uri.getScheme();
	if (scheme.empty() == true) {
		scheme = "FILE";
	}
	if (etk::uri::provider::getProviders().exist(scheme) == false) {
		return false;
	}
	return etk::uri::provider::getProviders()[scheme]->canCopy();
}

bool etk::uri::copy(const etk::Uri& _uriSource, const etk::Uri& _uriDestination) {
	etk::String scheme = _uriSource.getScheme();
	if (scheme.empty() == true) {
		scheme = "FILE";
	}
	etk::String scheme2 = _uriDestination.getScheme();
	if (scheme2.empty() == true) {
		scheme2 = "FILE";
	}
	if (scheme != scheme2) {
		TK_ERROR("Can not copy 2 uri between 2 model of resource... " << _uriSource << " => " << _uriDestination);
		return false;
	}
	if (etk::uri::provider::getProviders().exist(scheme) == false) {
		return false;
	}
	if (etk::uri::provider::getProviders()[scheme]->canCopy() == false) {
		return false;
	}
	return etk::uri::provider::getProviders()[scheme]->copy(_uriSource, _uriDestination);
}

bool etk::uri::canRemove(const etk::Uri& _uri) {
	etk::String scheme = _uri.getScheme();
	if (scheme.empty() == true) {
		scheme = "FILE";
	}
	if (etk::uri::provider::getProviders().exist(scheme) == false) {
		return false;
	}
	return etk::uri::provider::getProviders()[scheme]->canRemove();
}

bool etk::uri::remove(const etk::Uri& _uri) {
	etk::String scheme = _uri.getScheme();
	if (scheme.empty() == true) {
		scheme = "FILE";
	}
	if (etk::uri::provider::getProviders().exist(scheme) == false) {
		return false;
	}
	if (etk::uri::provider::getProviders()[scheme]->canRemove() == false) {
		return false;
	}
	return etk::uri::provider::getProviders()[scheme]->remove(_uri);
}

bool etk::uri::writeAll(const etk::Uri& _uri, const etk::String& _data) {
	auto fileIo = etk::uri::get(_uri);
	if (fileIo == null) {
		TK_ERROR("Can not create the uri: " << _uri);
		return false;
	}
	if (fileIo->open(etk::io::OpenMode::Write) == false) {
		TK_ERROR("Can not open (w) the file : " << _uri);
		return false;
	}
	fileIo->writeAll(_data);
	fileIo->close();
	return true;
}

bool etk::uri::readAll(const etk::Uri& _uri, etk::String& _data) {
	auto fileIo = etk::uri::get(_uri);
	if (fileIo == null) {
		TK_ERROR("Can not create the uri: " << _uri);
		return false;
	}
	if (fileIo->open(etk::io::OpenMode::Read) == false) {
		TK_ERROR("Can not open (w) the file : " << _uri);
		return false;
	}
	_data = fileIo->readAllString();
	fileIo->close();
	return true;
}

