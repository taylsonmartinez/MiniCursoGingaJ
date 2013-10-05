/**
 * 
 * TQTVD CONFIDENTIAL
 * __________________
 * 
 *  [2012] - [2013] TQTVD SOFTWARE LTDA 
 *  All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of TQTVD SOFTWARE LTDA and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to TQTVD SOFTWARE LTDA
 * and its suppliers and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from TQTVD SOFTWARE LTDA.
*/
package util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class FileProperties {
	
	
	public static String getProperty(String property, String propertyFile){
        try {         
            InputStream in = new FileInputStream(propertyFile);

            Properties prop = new Properties();
            prop.load(in);
            
            return prop.getProperty(property);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        return null;
	}
}
