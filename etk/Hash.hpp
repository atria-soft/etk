/** @file
 * @author Edouard DUPIN
 * @copyright 2011, Edouard DUPIN, all right reserved
 * @license MPL v2.0 (see license file)
 */

#include <etk/types.hpp>

#pragma once
#include <etk/String.hpp>

namespace etk {
	/**
	 * @brief Internal data of the [class[etk::hash]] class, it contain
	 * the name and the value of the hash vector.
	 * @internal
	 */
	template<class MY_TYPE> class HashData {
		public:
			etk::String m_key; //!< name of the current hash
			MY_TYPE m_value; //!< data of the current Hash
			/**
			 * @brief Constructor of the data for hash table.
			 * @param[in] _key name of the hash table.
			 * @param[in] _val Value of the hash element.
			 */
			HashData(const etk::String& _key, const MY_TYPE& _val) :
			  m_key(_key),
			  m_value(_val) {
				// nothing to do ...
			}
	};
	/**
	 * @brief Hash table template is a simple classical hash interface.
	 * A hash table is a equivalent of the dictionary in python, this is a
	 * simple interface between a name and a value:
	 *   - "name" : 19
	 *   - "name 2" : 99
	 * 
	 * @note The name is unique and the value is what you want
	 * 
	 * @todo check if something else exist in the generic library. (not the etk::Map and the std::unordered_map
	 * 
	 * @note The index are all time available since they are created. The order is the the one created
	 * 
	 * A simple example of use:
	 * @code{.cpp}
	 * // Create a integer hash table
	 * Hash<int> myValue;
	 * // add some element (note add and set is the same function)
	 * myValue.add("example", 98837);
	 * myValue.add("plop", 88);
	 * // Display an element:
	 * printf("my value is : %d", myValue["example"]);
	 * // Change value of an element:
	 * myValue.set("example", 99);
	 * // Remove an element:
	 * myValue.remove("plop");
	 * //Clean all the table:
	 * myValue.clear();
	 * @endcode
	 */
	template<class MY_TYPE> class Hash {
		private:
			etk::Vector<HashData<MY_TYPE>* > m_data; //!< Data of the hash ==> the Hash table is composed of pointer, this permit to have high speed when resize the vector ...
		public:
			/**
			 * @brief Constructor of the Hash table.
			 * @param[in] _count Number of basic element in the table.
			 */
			Hash(int32_t _count = 0) :
			  m_data(_count) {
				// nothing to do
			}
			/**
			 * @brief Destructor of the Hash table (clear all element in the table)
			 */
			~Hash() {
				clear();
			}
			/**
			 * @brief Remove all entry in the Hash table.
			 * @note It does not delete pointer if your value is a pointer type...
			 */
			void clear() {
				for (auto &it : m_data) {
					if (it != nullptr) {
						delete(it);
						it=nullptr;
					}
				}
				m_data.clear();
			}
			/**
			 * @brief Get a current element ID in the Hash table
			 * @param[in] _key Name of the hash requested
			 * @return Id of the element in the table or -1 of it does not existed
			 */
			int64_t getId(const etk::String& _key) const {
				for (size_t iii=0; iii<m_data.size(); iii++) {
					if (m_data[iii] != nullptr) {
						//TK_INFO("Compare key : '" << m_data[iii]->m_key << "' with '" << _key << "'" );
						if (m_data[iii]->m_key == _key) {
							return iii;
						}
					}
				}
				//TK_ERROR(" ==> not fund key '" << _key << "'" );
				return -1;
			}
			/**
			 * @brief Check if an element exist or not
			 * @param[in] _name Name of the hash requested
			 * @return true if the element exist
			 */
			bool exist(const etk::String& _name) const {
				int64_t elementId = getId(_name);
				//TK_INFO(" Exist ? '" << _name << "' id=" << elementId );
				if (elementId<0) {
					//TK_INFO("     ==> return false" );
					return false;
				}
				//TK_INFO("     ==> return true" );
				return true;
			}
			/**
			 * @brief Get a current element in the hash table, with his name.
			 * @param[in] _key Name of the hash requested
			 * @return Reference on the Element
			 */
			MY_TYPE& get(const etk::String& _key) const {
				static MY_TYPE g_error;
				int64_t elementId = getId(_key);
				if (elementId<0) {
					//TK_ERROR("try to access at an inexistent hash element : " << _key);
					return g_error;
				}
				return m_data[elementId]->m_value;
			}
			/**
			 * @brief Get an copy Element an a special position
			 * @param[in] _key Name of the hash requested
			 * @return An reference on the copy of selected element
			 */
			MY_TYPE& operator[] (const etk::String& _key) {
				return get(_key);
			}
			/**
			 * @brief Get an copy Element an a special position
			 * @param[in] _key Name of the hash requested
			 * @return An reference on the copy of selected element
			 */
			const MY_TYPE& operator[] (const etk::String& _key) const {
				return get(_key);
			}
			/**
			 * @brief Add an element OR set an element value
			 * @note add and set is the same function.
			 * @param[in] _key Name of the value to set in the hash table.
			 * @param[in] _value Value to set in the hash table.
			 */
			void add(const etk::String& _key, const MY_TYPE& _value) {
				int64_t elementId = getId(_key);
				if (elementId <0) {
					HashData<MY_TYPE>* tmp = new HashData<MY_TYPE>(_key, _value);
					if (tmp == nullptr) {
						//TK_ERROR("allocation error in Hash table : '" << _key << "'");
						return;
					}
					m_data.pushBack(tmp);
					return;
				}
				m_data[elementId]->m_value = _value;
			}
			/**
			 * @brief Set an element value
			 * @note add and set is the same function.
			 * @param[in] _key Name of the value to set in the hash table.
			 * @param[in] _value Value to set in the hash table.
			 */
			void set(const etk::String& _key, const MY_TYPE& _value) {
				add(_key, _value);
			}
			/**
			 * @brief Remove an element in the hash table.
			 * @param[in] _key Name of the element to remove.
			 */
			void remove(const etk::String& _key) {
				int64_t elementId = getId(_key);
				if (elementId <0) {
					//nothing to do ==> not existed
					return;
				}
				delete(m_data[elementId]);
				m_data[elementId] = nullptr;
				m_data.erase(m_data.begin()+elementId);
			}
			/**
			 * @brief Get the number of element in the hash table
			 * @return number of elements
			 */
			size_t size() const {
				return m_data.size();
			}
			/**
			 * @brief get an element with his id.
			 * @param[in] _pos Position on the element in the hash table.
			 * @return requested element at this position.
			 * @note this is a dangerous use of the hash table. Maybe you will use a simple vector.
			 */
			MY_TYPE& operator[] (size_t _pos) {
				return getValue(_pos);
			}
			/**
			 * @brief get an element with his id.
			 * @param[in] _pos Position on the element in the hash table.
			 * @return requested element at this position.
			 * @note this is a dangerous use of the hash table. Maybe you will use a simple vector.
			 */
			const MY_TYPE& operator[] (size_t _pos) const {
				return getValue(_pos);
			}
			/**
			 * @brief Get the name of the element at a specific position.
			 * @param[in] _pos Position of the element in the hash table.
			 * @return name of the element (key).
			 */
			const etk::String& getKey(size_t _pos) const {
				// NOTE :Do not change log level, this generate error only in debug mode
				#if DEBUG_LEVEL > 2
					if(_pos>m_data.size()){
						//TK_CRITICAL("Access to an inexistent data in hash : " << _pos << "/ " << m_data.size());
					}
				#endif
				return m_data[_pos]->m_key;
			}
			/**
			 * @brief Get all the element name (keys).
			 * @return a vector of all name (key).
			 */
			etk::Vector<etk::String> getKeys() const {
				etk::Vector<etk::String> keys;
				for (auto &it : m_data) {
					if (it != nullptr) {
						keys.pushBack(it->m_key);
					}
				}
				return keys;
			}
			/**
			 * @brief Get a value of the hash table at a specific position.
			 * @param[in] _pos of the element in the hash table.
			 * @return Value available at this position.
			 */
			const MY_TYPE& getValue(size_t _pos) const {
				// NOTE :Do not change log level, this generate error only in debug mode
				#if DEBUG_LEVEL > 2
					if(_pos>m_data.size()){
						//TK_CRITICAL("Access to an inexistent data in hash : " << _pos << "/ " << m_data.size());
					}
				#endif
				return m_data[_pos]->m_value;
			}
			/**
			 * @copydoc getValue (size_t)
			 */
			MY_TYPE& getValue(size_t _pos) {
				// NOTE :Do not change log level, this generate error only in debug mode
				#if DEBUG_LEVEL > 2
					if(_pos>m_data.size()){
						//TK_CRITICAL("Access to an inexistent data in hash : " << _pos << "/ " << m_data.size());
					}
				#endif
				return m_data[_pos]->m_value;
			}
	};
}

