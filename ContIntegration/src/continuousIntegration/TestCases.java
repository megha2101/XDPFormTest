package continuousIntegration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.io.SVNRepository;



public class TestCases {
	
	//TESTCASE 1 : TO CHECK THE NUMERIC FIELD NOT RIGHT ALIGNED

 public HashMap numFieldAlignmentCheck(HashMap map, HashMap result, String fileName) {
	List<String> listName = new ArrayList<String>();
	List<String> listParentName = new ArrayList<String>();
	
	listName.add(fileName);
	listParentName.add("");
	listName.add("");
	listParentName.add("");
	Set<Map.Entry<String, String>> entrySetMainMap = map.entrySet();// Set for main Map gives DRAW,Field,Subform
	for (Entry entry : entrySetMainMap) {
		Object mainMapkey = entry.getKey();
		if (mainMapkey.equals("FIELD")) {
			HashMap keyValue = (HashMap) entry.getValue();
			Set<Map.Entry<String, String>> entrySetLongKey = keyValue.entrySet();// set of long key and it's value(HashMap)
			for (Entry entry2 : entrySetLongKey) {
				Object mainMapkey2 = entry2.getKey();
				HashMap keyValue2 = (HashMap) entry2.getValue();
				if (keyValue2.containsKey("name")&& keyValue2.containsKey("OBJECT_TYPE")) {	
				   if (keyValue2.get("OBJECT_TYPE").equals("numericEdit")) {
					   if (keyValue2.containsKey("para_vAlign")&& (!keyValue2.get(	"para_vAlign").equals("middle"))) {
							String parentValue = (String) keyValue2.get("PARENT_NAME");
							String name = (String) keyValue2.get("name");
							listName.add(name);
							listParentName.add(parentValue);
						} else if (keyValue2.containsKey("para_hAlign")&& (!keyValue2.get("para_hAlign").equals("right")) && (!keyValue2.get("name").toString().contains("_count"))) {
							String parentValue = (String) keyValue2.get("PARENT_NAME");
							String name = (String) keyValue2.get("name");					
							listName.add(name);
							listParentName.add(parentValue);
						} else {
							//String parentValue = (String) keyValue2.get("PARENT_NAME");
							//String name = (String) keyValue2.get("name");
						} 
				    }											
				}
			}
		}
	}

	listName.add("");
	listParentName.add("");
	listName.add("");
	listParentName.add("");
	result.put(listName, listParentName);
	return result;
}

//TESTCASE 2: TO CHECK THAT TEXTFIELD, DATE AND DROPDOWN ARE LEFT ALIGNED OR NOT

public HashMap textfieldAlignment(HashMap map, HashMap result, String fileName) {
	List<String> listName = new ArrayList<String>();
	List<String> listParentName = new ArrayList<String>();
	
	listName.add(fileName);
	listParentName.add("");
	listName.add("");
	listParentName.add("");
	Set<Map.Entry<String, String>> entrySetMainMap = map.entrySet();// Set for main Map gives DRAW,Field,Subform
	for (Entry entry : entrySetMainMap) {
		Object mainMapkey = entry.getKey();
		if (mainMapkey.equals("FIELD")) {
			HashMap keyValue = (HashMap) entry.getValue();
			Set<Map.Entry<String, String>> entrySetLongKey = keyValue.entrySet();// set of long key and it's value(HashMap)
			for (Entry entry2 : entrySetLongKey) {
				Object mainMapkey2 = entry2.getKey();
				HashMap keyValue2 = (HashMap) entry2.getValue();
				if (keyValue2.containsKey("name")&& keyValue2.containsKey("OBJECT_TYPE")) {
					if (keyValue2.get("OBJECT_TYPE").equals("textEdit")|| keyValue2.get("OBJECT_TYPE").equals("dateTimeEdit")|| keyValue2.get("OBJECT_TYPE").equals("choiceList")) {
						if (keyValue2.containsKey("para_vAlign")&& (!keyValue2.get("para_vAlign").equals("middle"))) {
							String parentValue = (String) keyValue2.get("PARENT_NAME");
							String name = (String) keyValue2.get("name");
							listName.add(name);
							listParentName.add(parentValue);
						} else if (keyValue2.containsKey("para_hAlign")&& (!keyValue2.get("para_hAlign").equals("left"))) {
							String parentValue = (String) keyValue2.get("PARENT_NAME");
							String name = (String) keyValue2.get("name");
							listName.add(name);
							listParentName.add(parentValue);
						} else {

						}
					}
				}
			}
		}
	}

	listName.add("");
	listParentName.add("");
	listName.add("");
	listParentName.add("");
	result.put(listName, listParentName);
	return result;
}

//TESTCASE 3: LIST ALL THE NUMERIC FIELDS CONTAIN POSITIVE INTEGER VALUES AND NOT ACCEPT DECIMAL VALUES
public HashMap intValuesNumericField(HashMap map, HashMap result, String fileName) {
	List<String> listName = new ArrayList<String>();
	List<String> listParentName = new ArrayList<String>();
	listName.add(fileName);
	listParentName.add("");
	listName.add("");
	listParentName.add("");
	Set<Map.Entry<String, String>> entrySetLongKey = map.entrySet();// Set for main Map gives long key of FIELDS only
	List list = new ArrayList();
	for (Entry entry2 : entrySetLongKey) {
		Object mainMapkey2 = entry2.getKey();
		HashMap keyValue2 = (HashMap) entry2.getValue();
		if (keyValue2.containsKey("name") && keyValue2.containsKey("OBJECT_TYPE")){
			if( keyValue2.get("OBJECT_TYPE").equals("numericEdit")){
				if (keyValue2.containsKey("event_script_exit")) {
					String scriptText = keyValue2.get("event_script_exit").toString();
					String finalScriptText = scriptText.replaceAll("\\s", "").trim();
					String match = "if(this.rawValue" + "<0){this.rawValue="+ "\"\"" + ";}else{this.rawValue" + "="+ "Math.round(this.rawValue);}";
					if (finalScriptText.contains(match)) {
						String parentValue = (String) keyValue2.get("PARENT_NAME");
						String name = (String) keyValue2.get("name");
						listName.add(name);
						listParentName.add(parentValue);
					}
				}	
			}
		}			
	}
	listName.add("");
	listParentName.add("");
	listName.add("");
	listParentName.add("");
	result.put(listName, listParentName);
	return result;
}
//TESTCASE 4 : TO LIST THE NARRATIVE BOX AND TEXTFIELD NOT CONTAINS TRIM() FUNCTIONALITY
public HashMap textFieldTRIMCheck(HashMap map, HashMap result, String fileName) {
	List<String> listName = new ArrayList<String>();
	List<String> listParentName = new ArrayList<String>();
	listName.add(fileName);
	listParentName.add("");
	listName.add("");
	listParentName.add("");
	Set<Map.Entry<String, String>> entrySetLongKey = map.entrySet();// Set for main Map gives long key of FIELDS only
	List list = new ArrayList();
	for (Entry entry2 : entrySetLongKey) {
		Object mainMapkey2 = entry2.getKey();
		HashMap keyValue2 = (HashMap) entry2.getValue();
		if (keyValue2.containsKey("name") && keyValue2.containsKey("OBJECT_TYPE")){
			if(keyValue2.get("OBJECT_TYPE").equals("textEdit")){
				if (keyValue2.containsKey("event_script_exit")) {
					String scriptText = keyValue2.get("event_script_exit").toString();
					String finalScriptText = scriptText.replaceAll("\\s", "").trim();
					String match = "$.rawValue=Ltrim($.rawValue);";
					if (!finalScriptText.contains(match)) {
						String parentValue = (String) keyValue2.get("PARENT_NAME");
						String name = (String) keyValue2.get("name");
						listName.add(name);
						listParentName.add(parentValue);
					}					
			     }	  
			    else if (!keyValue2.containsKey("event_script_exit")) {
					String parentValue = (String) keyValue2.get("PARENT_NAME");
					String name = (String) keyValue2.get("name");
					listName.add(name);
					listParentName.add(parentValue);
			    }
		    }
	   }
 }
	listName.add("");
	listParentName.add("");
	listName.add("");
	listParentName.add("");
	result.put(listName, listParentName);
	return result;
	
}

//TESTCASE 5 : To check the UNTITLED NAMES
public HashMap untitledName(HashMap map, HashMap result, String fileName) {
	List<String> listName = new ArrayList<String>();
	List<String> listParentName = new ArrayList<String>();
	listName.add(fileName);
	listParentName.add("");
	listName.add("");
	listParentName.add("");
	Set<Map.Entry<String, String>> entrySetMainMap = map.entrySet();// Set for main Map gives DRAW,Field,Subform
	for (Entry entry : entrySetMainMap) {
		    Object mainMapkey = entry.getKey();	
			HashMap keyValue = (HashMap) entry.getValue();
			Set<Map.Entry<String, String>> entrySetLongKey = keyValue.entrySet();// set of long key and it's value(HashMap)
			for (Entry entry2 : entrySetLongKey) {
				Object mainMapkey2 = entry2.getKey();
				HashMap keyValue2 = (HashMap) entry2.getValue();
				if (!keyValue2.containsKey("name")) {
					String parentValue = (String) keyValue2.get("PARENT_NAME");
					listName.add(mainMapkey2.toString());
					listParentName.add(parentValue);
				}
			}
		}
		
	
	listName.add("");
	listParentName.add("");
	listName.add("");
	listParentName.add("");
	result.put(listName, listParentName);
	return result;
}
//TESTCASE 6 : LIST ALL THE NUMERIC FIELDS THAT ACCEPTS DECIMAL VALUES MORE THAN 2 DIGITS
public HashMap decimalValuesPlaces(HashMap map, HashMap result, String fileName) {
	List<String> listName = new ArrayList<String>();
	List<String> listParentName = new ArrayList<String>();
	listName.add(fileName);
	listParentName.add("");
	listName.add("");
	listParentName.add("");
	Set<Map.Entry<String, String>> entrySetLongKey = map.entrySet();// Set for main Map gives long key of FIELDS only
	List list = new ArrayList();
	for (Entry entry2 : entrySetLongKey) {
		Object mainMapkey2 = entry2.getKey();
		HashMap keyValue2 = (HashMap) entry2.getValue();
		if (keyValue2.containsKey("name") && keyValue2.containsKey("OBJECT_TYPE")){
			if(keyValue2.get("OBJECT_TYPE").equals("numericEdit")){
				if (!keyValue2.containsKey("event_script_exit")&& !keyValue2.containsKey("access")) {
					String parentValue = (String) keyValue2.get("PARENT_NAME");
					String name = (String) keyValue2.get("name");
					listName.add(name);
					listParentName.add(parentValue);
				} else if (keyValue2.containsKey("event_script_exit")) {
					String scriptText = keyValue2.get("event_script_exit").toString();
					String finalScriptText = scriptText.replaceAll("\\s", "").trim();
					String match = "if(this.rawValue" + "<0){this.rawValue="+ "\"\"" + ";}else{this.rawValue" + "="+ "Math.round(this.rawValue*100)/100;}";
					String match2 = "if(this.rawValue" + "<0){this.rawValue="+ "\"\"" + ";}else{this.rawValue" + "="+ "Math.round(this.rawValue);}";
					if (!finalScriptText.contains(match)&& !finalScriptText.contains(match2)) {
						String parentValue = (String) keyValue2.get("PARENT_NAME");
						String name = (String) keyValue2.get("name");
						listName.add(name);
						listParentName.add(parentValue);
					}

				}
			}
		}
	}
	listName.add("");
	listParentName.add("");
	listName.add("");
	listParentName.add("");
	result.put(listName, listParentName);
	return result;

}

//TESTCASE 7 : LIST ALL THE NUMERIC FIELDS ACCEPTS NEGATIVE VALUES
public HashMap negativeValuesAccept(HashMap map, HashMap result, String fileName) {
	List<String> listName = new ArrayList<String>();
	List<String> listParentName = new ArrayList<String>();
	listName.add(fileName);
	listParentName.add("");
	listName.add("");
	listParentName.add("");
	Set<Map.Entry<String, String>> entrySetLongKey = map.entrySet();// Set for main Map gives long key of FIELDS only
	List list = new ArrayList();
	for (Entry entry2 : entrySetLongKey) {
		Object mainMapkey2 = entry2.getKey();
		HashMap keyValue2 = (HashMap) entry2.getValue();
		if (keyValue2.containsKey("name") && keyValue2.containsKey("OBJECT_TYPE")){
			if(keyValue2.get("OBJECT_TYPE").equals("numericEdit")){
				if (!keyValue2.containsKey("event_script_exit")&& !keyValue2.containsKey("access")) {
					String parentValue = (String) keyValue2.get("PARENT_NAME");
					String name = (String) keyValue2.get("name");
					listName.add(name);
					listParentName.add(parentValue);
				} else if (keyValue2.containsKey("event_script_exit")) {
					String scriptText = keyValue2.get("event_script_exit").toString().trim();
					String finalScriptText = scriptText.replaceAll("\\s", "").trim();
					String match2 = "if(this.rawValue<0){this.rawValue="+ "\"\"" + ";}";
					if (!finalScriptText.contains(match2)) {
						String parentValue = (String) keyValue2.get("PARENT_NAME");
						String name = (String) keyValue2.get("name");
						listName.add(name);
						listParentName.add(parentValue);
					}
				}
			}
		}
	}
	listName.add("");
	listParentName.add("");
	listName.add("");
	listParentName.add("");
	result.put(listName, listParentName);
	return result;

}

//TESTCASE 8: TO CHECK THAT SUBFORM PAGE BREAK WITH CONTENT
public HashMap checkForSubformBreak(HashMap map, HashMap result, String fileName) {
	List<String> listName = new ArrayList<String>();
	List<String> listParentName = new ArrayList<String>();
	listName.add(fileName);
	listParentName.add("");
	listName.add("");
	listParentName.add("");
	Set<Map.Entry<String, String>> entrySetLongKey = map.entrySet();// Set for main Map gives long key of FIELDS only
	List list = new ArrayList();
	for (Entry entry2 : entrySetLongKey) {
		Object mainMapkey2 = entry2.getKey();
		HashMap keyValue2 = (HashMap) entry2.getValue();
		if (keyValue2.containsKey("name")&& keyValue2.containsKey("PAGE_BREAK")) {
			if (keyValue2.get("PAGE_BREAK").equals(0)) {
				String parentValue = (String) keyValue2.get("PARENT_NAME");
				String name = (String) keyValue2.get("name");
				listName.add(name);
				listParentName.add(parentValue);
			}
		}
	}
	listName.add("");
	listParentName.add("");
	listName.add("");
	listParentName.add("");
	result.put(listName, listParentName);
	return result;
}

//TESTCASE 9 :To check the req_ and opt_ at the starting of the radio groups, date time, drop down, text edit and numericEdit without readOnly.
public HashMap checkForReqOpt(HashMap map, HashMap result, String fileName) {
	List<String> listName = new ArrayList<String>();
	List<String> listParentName = new ArrayList<String>();
	listName.add(fileName);
	listParentName.add("");
	listName.add("");
	listParentName.add("");
	Set<Map.Entry<String, String>> entrySetLongKey = map.entrySet();//Set of main Map give long DRAW, FIELD, SUbFORM key
	List list = new ArrayList();
	for (Entry entry2 : entrySetLongKey) {
		Object mainMapkey2 = entry2.getKey();
		HashMap keyValue2 = (HashMap) entry2.getValue();
		if (keyValue2.containsKey("name") && keyValue2.containsKey("OBJECT_TYPE")){
			if(keyValue2.get("OBJECT_TYPE").equals("checkButton")|| keyValue2.get("OBJECT_TYPE").equals("dateTimeEdit")|| keyValue2.get("OBJECT_TYPE").equals("choiceList")){
				String name = keyValue2.get("name").toString();
				if (!name.startsWith("req", 0) && !name.startsWith("opt", 0)) {
					String parentValue = (String) keyValue2.get("PARENT_NAME");
					listName.add(name);
					listParentName.add(parentValue);
				}
			} else if (keyValue2.get("OBJECT_TYPE").equals("numericEdit")) {
				String name = keyValue2.get("name").toString();
				if (!name.startsWith("req", 0)&& !name.startsWith("opt", 0)) {
					String parentValue = (String) keyValue2.get("PARENT_NAME");
					listName.add(name);
					listParentName.add(parentValue);
					if (keyValue2.containsKey("access")&& keyValue2.get("access").equals("readOnly")) {
						listName.remove(name);
						listParentName.remove(parentValue);
					}
				}
			}
			else if ( keyValue2.get("OBJECT_TYPE").equals("textEdit")) {
				String name = keyValue2.get("name").toString();
				if (!name.startsWith("req", 0) && !name.startsWith("opt", 0)) {
					String parentValue = (String) keyValue2.get("PARENT_NAME");
					listName.add(name);
					listParentName.add(parentValue);
					if (keyValue2.containsKey("access")&& keyValue2.get("access").equals("readOnly")) {
						listName.remove(name);
						listParentName.remove(parentValue);
					}
				}
			}	
		}
	}
	listName.add("");
	listParentName.add("");
	listName.add("");
	listParentName.add("");
	result.put(listName, listParentName);
	return result;

}

//TESTCASE 10 : TO CHECK THE FORMAT OF THE DATE
public HashMap checkDateFormat(HashMap map, HashMap result, String fileName) {
	List<String> listName = new ArrayList<String>();
	List<String> listParentName = new ArrayList<String>();
	listName.add(fileName);
	listParentName.add("");
	listName.add("");
	listParentName.add("");
	Set<Map.Entry<String, String>> entrySetLongKey = map.entrySet();// Set for main Map gives long key of field only
	List list = new ArrayList();
	for (Entry entry2 : entrySetLongKey) {
		Object mainMapkey2 = entry2.getKey();
		HashMap keyValue2 = (HashMap) entry2.getValue();
		if (keyValue2.containsKey("OBJECT_TYPE")&& !keyValue2.containsKey("access")) {
			if (keyValue2.get("OBJECT_TYPE").equals("dateTimeEdit")) {
				if (keyValue2.containsKey("ui_picture")) {
					String uiPicValue = (String) keyValue2.get("ui_picture");
					if (!uiPicValue.equals("date{MM/DD/YYYY}")) {
						String parentValue = (String) keyValue2.get("PARENT_NAME");
						String name = (String) keyValue2.get("name");
						listName.add(name);
						listParentName.add(parentValue);
					}
				} else if (keyValue2.containsKey("format_picture")) {
					String formatPicValue = (String) keyValue2.get("format_picture");
					if (!formatPicValue.equals("date{MMM D, YYYY}")) {
						String parentValue = (String) keyValue2.get("PARENT_NAME");
						String name = (String) keyValue2.get("name");
						listName.add(name);
						listParentName.add(parentValue);
					}
				} else if (keyValue2.containsKey("validate_picture")) {
					String validatePicValue = (String) keyValue2.get("validate_picture");
					if (!validatePicValue.equals("date{MM/DD/YYYY}")) {
						String parentValue = (String) keyValue2.get("PARENT_NAME");
						String name = (String) keyValue2.get("name");
						listName.add(name);
						listParentName.add(parentValue);
					}
				} else {
					String parentValue = (String) keyValue2.get("PARENT_NAME");
					String name = (String) keyValue2.get("name");
					listName.add(name);
					listParentName.add(parentValue);
				}
			}
		}
	}
	listName.add("");
	listParentName.add("");
	listName.add("");
	listParentName.add("");
	result.put(listName, listParentName);
	return result;
}


//TESTCASE 11 : TO CHECK FOR THE REPEATED NAMES
public HashMap chkForRepeatedNames(HashMap map, HashMap result, String fileName) {
	List<String> listName = new ArrayList<String>();
	List<String> listParentName = new ArrayList<String>();
	List<String> allName = new ArrayList<String>();
	List<String> allNameParent = new ArrayList<String>();
	Set<String> store = new HashSet<>();
	listName.add(fileName);
	listParentName.add("");
	listName.add("");
	listParentName.add("");

	Set<Map.Entry<String, String>> entrySetMainMap = map.entrySet();// Set for main Map gives DRAW,Field,Subform
	for (Entry entry : entrySetMainMap) {
		Object mainMapkey = entry.getKey();
		HashMap keyValue = (HashMap) entry.getValue();
		Set<Map.Entry<String, String>> entrySetLongKey = keyValue.entrySet();//Set Of long key
		for (Entry entry2 : entrySetLongKey) {
			Object mainMapkey2 = entry2.getKey();
			HashMap keyValue2 = (HashMap) entry2.getValue();
			String name = (String) keyValue2.get("name");
			String parentValue = (String) keyValue2.get("PARENT_NAME");
			if (keyValue2.containsKey("name")) {
				allName.add(name);
				allNameParent.add(parentValue);
			}
		}
	}
	for (String rptdName : allName) {
		if (store.add(rptdName) == false) {
			Set<Map.Entry<String, String>> entrySetMainMap1 = map.entrySet();// Set for main Map gives DRAW,Field,Subform
			for (Entry entry : entrySetMainMap) {
				Object mainMapkey = entry.getKey();
				HashMap keyValue = (HashMap) entry.getValue();
				Set<Map.Entry<String, String>> entrySetLongKey = keyValue.entrySet();// Set of long key
				for (Entry entry2 : entrySetLongKey) {
					Object mainMapkey2 = entry2.getKey();
					HashMap keyValue2 = (HashMap) entry2.getValue();
					String name = (String) keyValue2.get("name");
					String parentValue = (String) keyValue2.get("PARENT_NAME");
					if (name == rptdName) {
						listName.add(rptdName);
						listParentName.add(parentValue);
					}
				}
			}
		}
	}

	listName.add("");
	listParentName.add("");
	listName.add("");
	listParentName.add("");
	result.put(listName, listParentName);
	return result;

 }

//TESTCASE 12: AUTO SAVE FUNCTIONALITY CODE EXIST OR NOT
public HashMap autoSave(HashMap map, HashMap result, String fileName) {
	List<String> listName = new ArrayList<String>();
	List<String> listParentName = new ArrayList<String>();
	listName.add(fileName);
	listParentName.add("");
	listName.add("");
	listParentName.add("");
	Set<Map.Entry<String, String>> entrySetLongKey = map.entrySet();// Set for main Map gives long key of FIELDS only
	List list = new ArrayList();
	for (Entry entry2 : entrySetLongKey) {
		Object mainMapkey2 = entry2.getKey();
		HashMap keyValue2 = (HashMap) entry2.getValue();
		if (keyValue2.containsKey("variables_script")&& keyValue2.containsKey("script_name")) {
			if (keyValue2.get("script_name").equals("Messaging")) {
				String parentValue = "Auto save functionality exists ";
				String name = "Exists";
				listName.add(name);
				listParentName.add(parentValue);
			}
		}else{
			String parentValue = "Auto save functionality not exists";
			String name = "Not Exists";
			listName.add(name);
			listParentName.add(parentValue);
		}
	}
	listName.add("");
	listParentName.add("");
	listName.add("");
	listParentName.add("");
	result.put(listName, listParentName);
	return result;
}

//TESTCASE 13: FORMS READY EVENT SCRIPT EXIST OR NOT
public HashMap 	formsReadyEvent(HashMap map, HashMap result, String fileName) {
	List<String> listName = new ArrayList<String>();
	List<String> listParentName = new ArrayList<String>();
	listName.add(fileName);
	listParentName.add("");
	listName.add("");
	listParentName.add("");
	Set<Map.Entry<String, String>> entrySetLongKey = map.entrySet();// Set for main Map gives long key of FIELDS only
	List list = new ArrayList();
	for (Entry entry2 : entrySetLongKey) {
		Object mainMapkey2 = entry2.getKey();
		HashMap keyValue2 = (HashMap) entry2.getValue();
		if (keyValue2.containsKey("activity")&& keyValue2.containsKey("name")) {
			if (keyValue2.get("name").equals("event__form_ready") && keyValue2.get("activity").equals("ready") ) {
				String parentValue = "Forms ready event script exists ";
				String name = "Exists ";
				listName.add(name);
				listParentName.add(parentValue);
			}
		}else{
			String parentValue = "Forms ready event script not exists";
			String name = "Not Exists";
			listName.add(name);
			listParentName.add(parentValue);
		}
	}
	listName.add("");
	listParentName.add("");
	listName.add("");
	listParentName.add("");
	result.put(listName, listParentName);
	return result;
}
//TESTCASE 14: Same XML of XDP exists or not 
	public HashMap sameXML( HashMap result, String fileName, String xmlFile, SVNRepository repository ) {
		List<String> listName = new ArrayList<String>();
		List<String> listParentName = new ArrayList<String>();
		listName.add(fileName);
		listParentName.add("");
		listName.add("");
		listParentName.add("");
		SVNNodeKind nodeKind;
			try {
				nodeKind = repository.checkPath(xmlFile ,-1 );
				 if ( nodeKind == SVNNodeKind.FILE ) {
			          System.out.println( xmlFile +" exists " );
			          listName.add("XML exists");
			  		  listParentName.add(xmlFile + " exists");
			    }else{
			    	listName.add("XML not exists");
					listParentName.add(xmlFile + " not exists");
			    	System.out.println("not exists");
			    }
			} catch (SVNException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	  	    
	
		listName.add("");
		listParentName.add("");
		listName.add("");
		listParentName.add("");
		result.put(listName, listParentName);
		return result;
	}


}
