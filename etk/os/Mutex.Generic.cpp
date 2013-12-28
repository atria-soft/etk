/**
 * @author Edouard DUPIN
 * 
 * @copyright 2011, Edouard DUPIN, all right reserved
 * 
 * @license BSD v3 (see license file)
 */

#include <etk/types.h>
#include <etk/os/Mutex.h>
#include <etk/debug.h>

etk::Mutex::Mutex(void)
{
	// create interface mutex :
	int ret = pthread_mutex_init(&m_mutex, NULL);
	TK_ASSERT(ret == 0, "Error creating Mutex ...");
}


etk::Mutex::~Mutex(void)
{
	// Remove mutex
	int ret = pthread_mutex_destroy(&m_mutex);
	TK_ASSERT(ret == 0, "Error destroying Mutex ...");
}


void etk::Mutex::lock(void)
{
	pthread_mutex_lock(&m_mutex);
}


bool etk::Mutex::tryLock(void)
{
	return pthread_mutex_trylock(&m_mutex) != 0;
}


void etk::Mutex::unLock(void)
{
	pthread_mutex_unlock(&m_mutex);
}

