/** Basic module interface.
 *
 * @author Edouard DUPIN */

open module org.atriasoft.etk {
	exports org.atriasoft.etk;
	exports org.atriasoft.etk.math;
	exports org.atriasoft.etk.util;
	
	requires transitive io.scenarium.logger;
}
