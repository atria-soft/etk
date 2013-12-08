/**
 * @author Edouard DUPIN
 * 
 * @copyright 2011, Edouard DUPIN, all right reserved
 * 
 * @license BSD v3 (see license file)
 */

#ifndef __ETK_HACH_H__
#define __ETK_HACH_H__

#include <etk/types.h>
#include <etk/debug.h>
#include <vector>
#include <etk/UString.h>

#undef __class__
#define __class__ "etk::Hash"


namespace etk {
	template<class MY_TYPE> class HashData {
		public:
			std::string m_key; //!< name of the current hash
			MY_TYPE m_value; //!< data of the current Hash
			HashData(const std::string& _key, const MY_TYPE& _val) :
			  m_key(_key),
			  m_value(_val) {
				// nothing to do ...
			}
	};
	
	template<class MY_TYPE> class Hash {
		private:
			std::vector<HashData<MY_TYPE>* > m_data; //!< Data of the hash ==> the Hash table is composed of pointer, this permit to have high speed when resize the vestor ...
		public:
			Hash(int32_t _count=0) :
			  m_data(_count) {
				
			}
			~Hash(void) {
				clear();
			}
			/**
			 * @brief Remove all entry in the Hash table
			 */
			void clear(void) {
				for (size_t iii=0; iii<m_data.size(); iii++) {
					if (m_data[iii] != NULL) {
						delete(m_data[iii]);
						m_data[iii]=NULL;
					}
				}
				m_data.clear();
			}
			/**
			 * @brief Get a current element ID in the Hash table
			 * @param[in] _key Name of the hash requested
			 * @return Id of the element in the table or -1 of it does not existed
			 */
			int64_t getId(const std::string& _key) const {
				for (size_t iii=0; iii<m_data.size(); iii++) {
					if (m_data[iii] != NULL) {
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
			 *@brief Check if an element exist or not
			 * @param[in] _key Name of the hash requested
			 * @return true if the element exist
			 */
			bool exist(const std::string& _name) const {
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
			 * @brief Get a current element in the vector
			 * @param[in] _key Name of the hash requested
			 * @return Reference on the Element
			 */
			MY_TYPE& get(const std::string& _key) const {
				static MY_TYPE g_error;
				int64_t elementId = getId(_key);
				if (elementId<0) {
					TK_ERROR("try to acces at an unexistant hash element : " << _key);
					return g_error;
				}
				return m_data[elementId]->m_value;
			}
			/**
			 * @brief Get an copy Element an a special position
			 * @param[in] _key Name of the hash requested
			 * @return An reference on the copy of selected element
			 */
			MY_TYPE& operator[] (const std::string& _key) {
				return get(_key);
			}
			const MY_TYPE& operator[] (const std::string& _key) const {
				return get(_key);
			}
			
			void add(const std::string& _key, const MY_TYPE& _value) {
				int64_t elementId = getId(_key);
				if (elementId <0) {
					HashData<MY_TYPE>* tmp = new HashData<MY_TYPE>(_key, _value);
					if (NULL == tmp) {
						TK_ERROR("allocation error in Hash table : '" << _key << "'");
						return;
					}
					m_data.push_back(tmp);
					return;
				}
				m_data[elementId]->m_value = _value;
			}
			void set(const std::string& _key, const MY_TYPE& _value) {
				add(_key, _value);
			}
			void remove(const std::string& _key) {
				int64_t elementId = getId(_key);
				if (elementId <0) {
					//nothing to do ==> not existed
					return;
				}
				delete(m_data[elementId]);
				m_data[elementId] = NULL;
				m_data.remove(elementId);
			}
			/**
			 * @brief Get the number of element in the hash table
			 * @return number of elements
			 */
			int32_t size(void) const {
				return m_data.size();
			}
			MY_TYPE& operator[] (size_t _pos) {
				return getValue(_pos);
			}
			const MY_TYPE& operator[] (size_t _pos) const {
				return getValue(_pos);
			}
			const std::string& getKey(size_t _pos) const {
				// NOTE :Do not change log level, this generate error only in debug mode
				#if DEBUG_LEVEL > 2
					if(_pos>m_data.size()){
						TK_CRITICAL("Access to an unexistant data in hach : " << _pos << "/ " << m_data.size());
					}
				#endif
				return m_data[_pos]->m_key;
			}
			const MY_TYPE& getValue(size_t _pos) const {
				// NOTE :Do not change log level, this generate error only in debug mode
				#if DEBUG_LEVEL > 2
					if(_pos>m_data.size()){
						TK_CRITICAL("Access to an unexistant data in hach : " << _pos << "/ " << m_data.size());
					}
				#endif
				return m_data[_pos]->m_value;
			}
			MY_TYPE& getValue(size_t _pos) {
				// NOTE :Do not change log level, this generate error only in debug mode
				#if DEBUG_LEVEL > 2
					if(_pos>m_data.size()){
						TK_CRITICAL("Access to an unexistant data in hach : " << _pos << "/ " << m_data.size());
					}
				#endif
				return m_data[_pos]->m_value;
			}
	};
};

#undef __class__
#define __class__ NULL

#endif
