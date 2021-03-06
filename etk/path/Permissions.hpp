/** @file
 * @author Edouard DUPIN
 * @copyright 2011, Edouard DUPIN, all right reserved
 * @license MPL v2.0 (see license file)
 */

#include <etk/types.hpp>

#pragma once

#include <etk/String.hpp>

namespace etk {
	namespace path {
		/**
		 * @brief File System Right management
		 */
		class Permissions {
			public:
				static Permissions genericFile;
				static Permissions genericFolder;
			private:
				uint16_t m_rights; //!< right manage in a bit field
			public:
				/**
				 * @brief Right contructor.
				 * @param[in] _newRight Right to set by default
				 */
				Permissions(uint16_t _newRight = 0);
				/**
				 * @brief Get Raw right
				 * @return the internal value.
				 */
				uint16_t getRightValue() const;
				/**
				 * @brief Copy asignement operator (operator=)
				 * @param[in] _obj Object to copy
				 * @return Local reference on the object
				 */
				Permissions& operator= (const Permissions &_obj);
				/**
				 * @brief Asignement operator (operator=)
				 * @param[in] _newVal Value to set on the right
				 * @return Local reference on the object
				 */
				Permissions& operator= (const uint32_t _newVal );
				/**
				 * @brief Clear right (set the value at 0 ==> cant not be read/write/execute
				 */
				void clear();
				/**
				 * @brief Get the "Read status" for the "User"
				 * @return true The file/folder/... is readable
				 * @return false The file/folder/... is NOT readable
				 */
				bool isUserReadable() const;
				/**
				 * @brief Get the "Write status" for the "User"
				 * @return true The file/folder/... is writable
				 * @return false The file/folder/... is NOT writable
				 */
				bool isUserWritable() const;
				/**
				 * @brief Get the "execute status" for the "User"
				 * @return true The file/folder/... is executable
				 * @return false The file/folder/... is NOT executable
				 */
				bool isUserRunable() const;
				/**
				 * @brief Set the "Read status" for the "User"
				 * @param[in] _newStatus New value to set on the file/folder/...
				 */
				void setUserReadable(bool _newStatus);
				/**
				 * @brief Set the "Write status" for the "User"
				 * @param[in] _newStatus New value to set on the file/folder/...
				 */
				void setUserWritable(bool _newStatus);
				/**
				 * @brief Set the "execute status" for the "User"
				 * @param[in] _newStatus New value to set on the file/folder/...
				 */
				void setUserRunable(bool _newStatus);
				/**
				 * @brief Get the "Read status" for the "Group"
				 * @return true The file/folder/... is readable
				 * @return false The file/folder/... is NOT readable
				 */
				bool isGroupReadable() const;
				/**
				 * @brief Get the "Write status" for the "Group"
				 * @return true The file/folder/... is writable
				 * @return false The file/folder/... is NOT writable
				 */
				bool isGroupWritable() const;
				/**
				 * @brief Get the "execute status" for the "Group"
				 * @return true The file/folder/... is executable
				 * @return false The file/folder/... is NOT executable
				 */
				bool isGroupRunable() const;
				/**
				 * @brief Set the "Read status" for the "Group"
				 * @param[in] _newStatus New value to set on the file/folder/...
				 */
				void setGroupReadable(bool _newStatus);
				/**
				 * @brief Set the "Write status" for the "Group"
				 * @param[in] _newStatus New value to set on the file/folder/...
				 */
				void setGroupWritable(bool _newStatus);
				/**
				 * @brief Set the "Execute status" for the "Group"
				 * @param[in] _newStatus New value to set on the file/folder/...
				 */
				void setGroupRunable(bool _newStatus);
				/**
				 * @brief Get the "Read status" for the "Other"
				 * @return true The file/folder/... is readable
				 * @return false The file/folder/... is NOT readable
				 */
				bool isOtherReadable() const;
				/**
				 * @brief Get the "Write status" for the "Other"
				 * @return true The file/folder/... is writable
				 * @return false The file/folder/... is NOT writable
				 */
				bool isOtherWritable() const;
				/**
				 * @brief Get the "execute status" for the "Other"
				 * @return true The file/folder/... is executable
				 * @return false The file/folder/... is NOT executable
				 */
				bool isOtherRunable() const;
				/**
				 * @brief Set the "Read status" for the "Other"
				 * @param[in] _newStatus New value to set on the file/folder/...
				 */
				void setOtherReadable(bool _newStatus);
				/**
				 * @brief Set the "Write status" for the "Other"
				 * @param[in] _newStatus New value to set on the file/folder/...
				 */
				void setOtherWritable(bool _newStatus);
				/**
				 * @brief Set the "Execute status" for the "Other"
				 * @param[in] _newStatus New value to set on the file/folder/...
				 */
				void setOtherRunable(bool _newStatus);
				/**
				 * @brief Get the write written in a string mode (like in linux rw-r-----)
				 * @return String with the right in string
				 */
				etk::String getRight() const;
		};
	}
	//! @not_in_doc
	etk::Stream& operator <<(etk::Stream &_os, const etk::path::Permissions &_obj);
}


