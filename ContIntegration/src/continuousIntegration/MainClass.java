package continuousIntegration;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;



public class MainClass {
	public static void main(String[] args)throws ParserConfigurationException, SAXException, IOException, SVNException {
		
		Functions obj = new Functions();// make the object of class Functions to call it's functions
		TestCases tc = new TestCases(); // make the object of the class TestCase to call the test cases

        
		//http://www.usgbc.name/svn/drupal/leed/forms/Dummy_Folder
        String url = "svn://localhost/mysvn";
        String name = "Megha";
        String password = "12345";
        
        //initialization
        long startRevision = 0 ;
        long endRevision = -1;//HEAD (the latest) revision
        Collection logEntries = null;
        ArrayList files = new ArrayList();
        ArrayList commitXdpList = new ArrayList();
        
         List hashMapList = new ArrayList();
		 List hashMapList2 = new ArrayList();
		 List hashMapList3 = new ArrayList();
		 List hashMapList4 = new ArrayList();
		 List hashMapList5 = new ArrayList();
		 List hashMapList6 = new ArrayList();
		 List hashMapList7 = new ArrayList();
		 List hashMapList8 = new ArrayList();
		 List hashMapList9 = new ArrayList();
		 List hashMapList10 = new ArrayList();
		 List hashMapList11 = new ArrayList();
		 List hashMapList12 = new ArrayList();
		 List hashMapList13 = new ArrayList();
		 List hashMapList14 = new ArrayList();
		 List hashMapListFinal = new ArrayList();
        
        //Initializes the library (it must be done before ever using the library itself)
        //DAVRepositoryFactory.setup(); //For using over http:// and https://
		 SVNRepositoryFactoryImpl.setup(); //For using over svn:// and svn+xxx://

        if (args != null) {
            url = (args.length >= 1) ? args[0] : url;
            endRevision = (args.length >= 3) ? Long.parseLong(args[2]): endRevision;
            name = (args.length >= 4) ? args[3] : name;
            password = (args.length >= 5) ? args[4] : password;
        }

        SVNRepository repository = null;
        
        try {
            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
        } catch (SVNException svne) {
            //Perhaps a malformed URL is the cause of this exception.            
            System.err.println("error while creating an SVNRepository for the location '"+ url + "': " + svne.getMessage());
            System.exit(1);
        }
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
        repository.setAuthenticationManager(authManager);
        //Gets the latest revision number of the repository
        try {
            endRevision = repository.getLatestRevision();
           startRevision = endRevision;
        } catch (SVNException svne) {
            System.err.println("error while fetching the latest repository revision: " + svne.getMessage());
            System.exit(1);
        }
        System.out.println("end revision is :" + endRevision);
        try {
            logEntries = repository.log(new String[] {""}, null, startRevision,endRevision, true, true);
            System.out.println("logEntries are :" + logEntries);
            while(logEntries.isEmpty()){
            	endRevision = (endRevision-1);
            	startRevision = endRevision;
            	logEntries = repository.log(new String[] {""}, null, startRevision,endRevision, true, true);
            	
            }
            	
           /* if(logEntries.isEmpty()){
            	endRevision = (endRevision-2);
            	startRevision = endRevision;
            	logEntries = repository.log(new String[] {""}, null, startRevision,endRevision, true, true);
            	
            }*/
      
        } catch (SVNException svne) {
            System.out.println("error while collecting log information for '" + url + "': " + svne.getMessage());
            System.exit(1);
        } 
      //gets a next SVNLogEntry    	
        SVNLogEntry logEntry = (SVNLogEntry) logEntries.iterator().next();
        System.out.println("---------------------------------------------");
        System.out.println("revision: " + logEntry.getRevision());
        System.out.println("author: " + logEntry.getAuthor());
        System.out.println("date: " + logEntry.getDate());
        System.out.println("log message: " + logEntry.getMessage());
        // displaying all paths that were changed in that revision; cahnged path information is represented by SVNLogEntryPath.            
        if (logEntry.getChangedPaths().size() > 0) {
            System.out.println();
            System.out.println("changed paths:");               
             // keys are changed paths                 
            Set changedPathsSet = logEntry.getChangedPaths().keySet();
            for (Iterator changedPaths = changedPathsSet.iterator(); changedPaths.hasNext();) {                    
                 //obtains a next SVNLogEntryPath                     
                SVNLogEntryPath entryPath = (SVNLogEntryPath) logEntry.getChangedPaths().get(changedPaths.next());
                System.out.println(" " + entryPath.getType()+ "	"+ entryPath.getPath()+ ((entryPath.getCopyPath() != null) ? 
                		" (from "+ entryPath.getCopyPath() + " revision "+ entryPath.getCopyRevision() + ")" : ""));
                if(entryPath.getPath().endsWith("xdp")){
                	commitXdpList.add(entryPath.getPath());
                }
            }
          } 
        System.out.println("xdp files are :" + commitXdpList);
        obj.fileDelete();
        if(!commitXdpList.isEmpty()){
        	//Get Document Builder
  	      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
  	      DocumentBuilder builder = null;
	  	  for(Object file:commitXdpList){
	  	    	String filePath = (String)file;
	  	    	//String filePath = "http://www.usgbc.name/svn/drupal/leed/forms/Dummy_Foldercommon/id/idc1/idc1.xdp";
	  	    	System.out.println("filepath is ;"+filePath);
	  	    	String xmlPath = filePath.replace("xdp", "xml");
	  	    	System.out.println("xmlPath is :" +xmlPath);
	  	    	
	  	    		
	  	    	SVNProperties fileProperties = new SVNProperties();
		        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try {		            
	               repository.getFile(filePath, -1, fileProperties, baos);		            
		           //baos.writeTo(System.out);
		        } catch (SVNException svne) {
		            System.err.println("error while fetching the file contents and properties: " + svne.getMessage());
		            System.exit(1);
		        } 
				try {
					builder = factory.newDocumentBuilder();
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				}
		        InputSource is = new InputSource();
		        BufferedReader br =new BufferedReader(new InputStreamReader(new ByteArrayInputStream(baos.toByteArray())));
		        StringBuffer stringBuffer = new StringBuffer();
				String line = null;
				while ((line =br.readLine()) != null) {
					stringBuffer.append(line).append("\n");
				}
				is.setCharacterStream(new StringReader(stringBuffer.toString()));
				Document document = null;
				try {
					document = builder.parse(is);
				} catch (SAXException e) {

					e.printStackTrace();
				}
				// Normalize the XML Structure; It's just too important !!
				document.getDocumentElement().normalize();
				System.out.println("Document is created");
				HashMap<String, HashMap<String, Object>> mainHashFinal = new HashMap<String, HashMap<String, Object>>();
				HashMap<String, Object> mainHash = new HashMap<String, Object>();
				HashMap<String, Object> mainHashDraw = new HashMap<String, Object>();
				HashMap<String, Object> mainHashField = new HashMap<String, Object>();
				HashMap<String, Object> mainHashSubForm = new HashMap<String, Object>();
				HashMap<String, Object> mainHashVariable = new HashMap<String, Object>();
				HashMap<String, Object> mainHashFormReady = new HashMap<String, Object>();
				HashMap<ArrayList, ArrayList> ResultMap = new HashMap<ArrayList, ArrayList>();
				HashMap<ArrayList, ArrayList> ResultMap2 = new HashMap<ArrayList, ArrayList>();
				HashMap<ArrayList, ArrayList> ResultMap3 = new HashMap<ArrayList, ArrayList>();
				HashMap<ArrayList, ArrayList> ResultMap4 = new HashMap<ArrayList, ArrayList>();
				HashMap<ArrayList, ArrayList> ResultMap5 = new HashMap<ArrayList, ArrayList>();
				HashMap<ArrayList, ArrayList> ResultMap6 = new HashMap<ArrayList, ArrayList>();
				HashMap<ArrayList, ArrayList> ResultMap7 = new HashMap<ArrayList, ArrayList>();
				HashMap<ArrayList, ArrayList> ResultMap8 = new HashMap<ArrayList, ArrayList>();
				HashMap<ArrayList, ArrayList> ResultMap9 = new HashMap<ArrayList, ArrayList>();
				HashMap<ArrayList, ArrayList> ResultMap10 = new HashMap<ArrayList, ArrayList>();
				HashMap<ArrayList, ArrayList> ResultMap11 = new HashMap<ArrayList, ArrayList>();
				HashMap<ArrayList, ArrayList> ResultMap12 = new HashMap<ArrayList, ArrayList>();
				HashMap<ArrayList, ArrayList> ResultMap13 = new HashMap<ArrayList, ArrayList>();
				HashMap<ArrayList, ArrayList> ResultMap14 = new HashMap<ArrayList, ArrayList>();
				
				
				ArrayList<String> mainKeyDrawNameList = new ArrayList<String>();// gives the list of strings that contains DRAW long key
				ArrayList<String> mainKeyFieldNameList = new ArrayList<String>();
				ArrayList<String> mainKeySubFormNameList = new ArrayList<String>();
				ArrayList<String> mainKeyVariableNameList = new ArrayList<String>();
				
				String commonValue = "";
				
				//********************** CREATES HASHMAP FOR DRAW************************
				NodeList draw = document.getElementsByTagName("draw");
				ArrayList<Object> attrMapArrList = new ArrayList<Object>();// this list add the attrMap and make a arrayList of attrMap
				for (int temp = 0; temp < draw.getLength(); temp++) {
					// need to instantiate drawAttrMap everytime it goes to next drawNode
					HashMap<String, String> drawAttrMap = new HashMap<String, String>();
					Node drawNode = draw.item(temp);
					if (drawNode.hasAttributes()) {
						int numDrawAttr = drawNode.getAttributes().getLength();
						for (int i = 0; i < numDrawAttr; i++) {
							Node attr = drawNode.getAttributes().item(i);
							String attrName = attr.getNodeName();
							String attrValue = attr.getNodeValue();						
							drawAttrMap.put(attrName, attrValue); // get the map of attributes of w,h etc
						}
					}
					obj.mainKeyName(drawNode, commonValue,mainKeyDrawNameList);
					obj.drawInnerChilds(drawNode, drawAttrMap);
					obj.parseForParent(drawNode, commonValue, drawAttrMap);
					obj.obName(drawNode, drawAttrMap);
					if(!drawAttrMap.containsKey("PARENT_NAME")){
						drawAttrMap.put("PARENT_NAME", "form1/");
						
					}
					attrMapArrList.add(drawAttrMap);// this list add the attrmap and make a arrayList of attrMap
				}
				// System.out.println("attrMapArrList gives the list of attrMap :"+attrMapArrList);//gives the list of attrMap to make and print the hashMap of 2 list			
				for (int i = 0; i < mainKeyDrawNameList.size(); i++) {
					mainHash.put(mainKeyDrawNameList.get(i),attrMapArrList.get(i));
					mainHashDraw.put(mainKeyDrawNameList.get(i),attrMapArrList.get(i));
				}
				mainHashFinal.put("DRAW", mainHashDraw);
				// obj. print(mainHash,0);//print the hashMap without DRAW key			
				 //obj.print(mainHashFinal,0); // drawHashFinal shows the DRAw also the main key
				
						
				// **********************CREATES HASHMAP OF FIELD*********************
				NodeList field = document.getElementsByTagName("field");
				ArrayList<Object> attrMapFieldArrList = new ArrayList<Object>();
				for (int temp = 0; temp < field.getLength(); temp++) {
					// need to instantiate fieldAttrMap everytime it goes to next fieldNode
					HashMap<String, String> fieldAttrMap = new HashMap<String, String>();
					Node fieldNode = field.item(temp);
					if (fieldNode.hasAttributes()) {
						int numFieldAttr = fieldNode.getAttributes()
								.getLength();
						for (int i = 0; i < numFieldAttr; i++) {
							Node attr = fieldNode.getAttributes().item(i);
							String attrName = attr.getNodeName();
							String attrValue = attr.getNodeValue();
							fieldAttrMap.put(attrName, attrValue); // get the map of attributes of w, h etc
						}
					}
					obj.mainKeyName(fieldNode, commonValue,mainKeyFieldNameList);
					obj.drawInnerChilds(fieldNode, fieldAttrMap);
					obj.parseForParent(fieldNode, commonValue, fieldAttrMap);
					obj.obName(fieldNode, fieldAttrMap);
					obj.getElTagName(fieldNode);
					if(!fieldAttrMap.containsKey("PARENT_NAME")){
						fieldAttrMap.put("PARENT_NAME", "form1/");
						
					}
					attrMapFieldArrList.add(fieldAttrMap);// this list add the attrMap and make a arrayList of attrMap
				}
				// To create the main hash in which draw already existing
				for (int i = 0; i < mainKeyFieldNameList.size(); i++) {
					mainHash.put(mainKeyFieldNameList.get(i),attrMapFieldArrList.get(i));
					mainHashField.put(mainKeyFieldNameList.get(i),attrMapFieldArrList.get(i));
				}
				// this add the FIELD key to the main hash in which DRAW key  already existing
				mainHashFinal.put("FIELD", mainHashField);
				
				
				//******************** CREATES HASHMAP OF SUBFORM ******************
				NodeList subForm = document.getElementsByTagName("subform");
				ArrayList<Object> attrMapSubFormArrList = new ArrayList<Object>();
				for (int temp = 0; temp < subForm.getLength(); temp++) {
					// IMP:need to instantiate subFormAttrMap everytime it goes to next drawNode
					HashMap<String, String> subFormAttrMap = new HashMap<String, String>();
					Node subFormNode = subForm.item(temp);
					if (subFormNode.hasAttributes()) {
						int numsubFormAttr = subFormNode.getAttributes().getLength();
						for (int i = 0; i < numsubFormAttr; i++) {
							Node attr = subFormNode.getAttributes().item(i);
							String attrName = attr.getNodeName();
							String attrValue = attr.getNodeValue();
							if(!subFormAttrMap.containsKey("PARENT_NAME")){
								subFormAttrMap.put("PARENT_NAME", "form1/");
								
							}
							subFormAttrMap.put(attrName, attrValue); // get the map of attributes of w,h etc																	
						}
					}
	    	      obj.mainKeyName( subFormNode, commonValue, mainKeySubFormNameList);	  	       	    	      
	    	      obj.parseForParent( subFormNode, commonValue, subFormAttrMap);
	    	      obj.getElTagName(subFormNode);
	    	      obj.subFormPageBreak(subFormNode,0,subFormAttrMap);
				  attrMapSubFormArrList.add(subFormAttrMap);// this list add the attrMap and make a arrayList of attrMap																	
				}
				
				for (int i = 0; i < mainKeySubFormNameList.size(); i++) {
					mainHash.put(mainKeySubFormNameList.get(i), attrMapSubFormArrList.get(i));
					mainHashSubForm.put(mainKeySubFormNameList.get(i), attrMapSubFormArrList.get(i));
				}
				mainHashFinal.put("SUBFORM", mainHashSubForm);
				//obj.print(mainHashSubForm,0);
				
				//*********************** CREATES HASHMAP FOR VARIABLES***********************
				
				NodeList variable = document.getElementsByTagName("variables");
				ArrayList<Object> attrMapVariableArrList = new ArrayList<Object>();
				HashMap<String, String> variableAttrMap = new HashMap<String, String>();
				for (int temp = 0; temp < variable.getLength(); temp++) {
					// need to instantiate fieldAttrMap everytime it goes to next fieldNode
					Node variableNode = variable.item(temp);
					obj.drawInnerChilds(variableNode, variableAttrMap);
					attrMapVariableArrList.add(variableAttrMap);// this list add the attrMap and make a arrayList of attrMap
				}
				
				mainHashVariable.put("variables", variableAttrMap);
				// this add the VARIABLE key to the main hash in which DRAW key  already existing
				//mainHashFinal.put("VARIABLE", mainHashVariable);
				
				//****************************CREATES HASHMAP FOR FORM_READY***********************
				NodeList formReady = document.getElementsByTagName("subform");	
				Node formReadyNode = formReady.item(0);
				HashMap<String, String> formReadyAttrMap = new HashMap<String, String>();
				obj.drawInnerFormReady(formReadyNode, formReadyAttrMap);			
				mainHashFormReady.put("formReady",formReadyAttrMap );
				//mainHashFinal.put("FORM_READY", mainHashFormReady);
				
				
				// Calling Test case
				
				tc.numFieldAlignmentCheck(mainHashFinal, ResultMap, (String)filePath);
				tc.textfieldAlignment(mainHashFinal, ResultMap2, (String)filePath);
				tc.intValuesNumericField(mainHashField, ResultMap3, (String)filePath);
				tc.textFieldTRIMCheck(mainHashField, ResultMap4, (String)filePath);		
				tc.untitledName(mainHashFinal, ResultMap5, (String)filePath);
				tc.decimalValuesPlaces(mainHashField, ResultMap6, (String)filePath);
				tc.negativeValuesAccept(mainHashField, ResultMap7, (String)filePath);
				tc.checkForSubformBreak(mainHashSubForm, ResultMap8, (String)filePath);
				tc.checkForReqOpt(mainHashField, ResultMap9, (String)filePath);			
				tc.checkDateFormat(mainHashField, ResultMap10, (String)filePath);
				tc.chkForRepeatedNames(mainHashFinal, ResultMap11, (String)filePath);
				tc.autoSave(mainHashVariable, ResultMap12, (String)filePath);
				tc.formsReadyEvent(mainHashFormReady, ResultMap13, (String)filePath);	
				tc.sameXML(ResultMap14,(String)filePath ,(String)xmlPath, repository);

				hashMapList.add(ResultMap);
				hashMapList2.add(ResultMap2);
				hashMapList3.add(ResultMap3);
				hashMapList4.add(ResultMap4);
				hashMapList5.add(ResultMap5);
				hashMapList6.add(ResultMap6);
				hashMapList7.add(ResultMap7);
				hashMapList8.add(ResultMap8);
				hashMapList9.add(ResultMap9);
				hashMapList10.add(ResultMap10);
				hashMapList11.add(ResultMap11);
				hashMapList12.add(ResultMap12);	
				hashMapList13.add(ResultMap13);
				hashMapList14.add(ResultMap14);			
				}// end of for loop
					 
				 hashMapListFinal.add(hashMapList);
				 hashMapListFinal.add(hashMapList2);
				 hashMapListFinal.add(hashMapList3);
				 hashMapListFinal.add(hashMapList4);
				 hashMapListFinal.add(hashMapList5);
				 hashMapListFinal.add(hashMapList6);
				 hashMapListFinal.add(hashMapList7);
				 hashMapListFinal.add(hashMapList8);
				 hashMapListFinal.add(hashMapList9);
				 hashMapListFinal.add(hashMapList10);
				 hashMapListFinal.add(hashMapList11);
				 hashMapListFinal.add(hashMapList12);
				 hashMapListFinal.add(hashMapList13);
				 hashMapListFinal.add(hashMapList14);
				 obj.writeInExcel(hashMapListFinal);				
		}else{
			System.out.println("empty list");
			obj.fileDelete();
		}   
		    
	         	    
        }
	
            
	}   




