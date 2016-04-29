package continuousIntegration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Functions {
	
 public void writeInExcel(List hashMapListFinal) throws IOException{
	 
		try{
			int sheetIndex= 1;
		    XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = null;
			XSSFRow row =null;
			XSSFCell cell = null;
			XSSFCell cel = null;
			
			// Style

			XSSFCellStyle styleHeaderColor = wb.createCellStyle();
			styleHeaderColor.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
			styleHeaderColor.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
			styleHeaderColor.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			styleHeaderColor.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			styleHeaderColor.setBorderRight(XSSFCellStyle.BORDER_THIN);
			styleHeaderColor.setBorderTop(XSSFCellStyle.BORDER_THIN);

			XSSFCellStyle styleColor = wb.createCellStyle();
			styleColor.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			styleColor.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
			styleColor.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			styleColor.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			styleColor.setBorderRight(XSSFCellStyle.BORDER_THIN);
			styleColor.setBorderTop(XSSFCellStyle.BORDER_THIN);

			XSSFCellStyle styleBorder = wb.createCellStyle();
			styleBorder.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			styleBorder.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			styleBorder.setBorderRight(XSSFCellStyle.BORDER_THIN);
			styleBorder.setBorderTop(XSSFCellStyle.BORDER_THIN);

			XSSFFont font = wb.createFont();
			font.setBold(true);
		   
				
			for(Object hashMapList : hashMapListFinal){
			    int rowIndex = 5;
				int cellIndex = 0;
				int cellIndex1 = 1;
			
				sheet = wb.createSheet("sheet" + sheetIndex);
				System.out.println(sheetIndex);		
				switch(sheetIndex){
				case 1 :
					row = sheet.createRow(1);
					cell = sheet.getRow(1).createCell(0);
					cell.setCellValue("----NUMERIC FIELDS NOT RIGHT ALIGNED----");			    
					break;
				
				case 2: 
					row = sheet.createRow(1);
					cell = sheet.getRow(1).createCell(0);
					cell.setCellValue("----TEXTFIELDS, DATE AND DROPDOWN NOT LEFT ALIGNED----");
					break;
					
				case 3: 
					row = sheet.createRow(1);
					cell = sheet.getRow(1).createCell(0);
					cell.setCellValue("----NUMERIC FIELDS CONTAIN POSITIVE INTEGER VALUES AND NOT ACCEPT DECIMAL VALUES----");
					break;
					
				case 4: 
					row = sheet.createRow(1);
					cell = sheet.getRow(1).createCell(0);
					cell.setCellValue("----TEXTFIELD AND NARRATIVE BOX NOT CONTAINS TRIM() FUNCTIONALITY ----");
					break;
					
				case 5: 
					row = sheet.createRow(1);
					cell = sheet.getRow(1).createCell(0);
					cell.setCellValue("----UNTITLED NAME----");
					break;
					
				case 6: 
					row = sheet.createRow(1);
					cell = sheet.getRow(1).createCell(0);
					cell.setCellValue("----NUMERIC FIELDS THAT ACCEPTS DECIMAL VALUES MORE THAN 2 DIGITS----");
					break;
					
				case 7: 
					row = sheet.createRow(1);
					cell = sheet.getRow(1).createCell(0);
					cell.setCellValue("----NUMERIC FIELDS  ACCEPTS NEGATIVE VALUES----");
					break;
					
				case 8: 
					row = sheet.createRow(1);
					cell = sheet.getRow(1).createCell(0);
					cell.setCellValue("----TO LIST THE SUBFORM NOT PAGE BREAK WITH CONTENT ----");
					break;
					
				case 9: 
					row = sheet.createRow(1);
					cell = sheet.getRow(1).createCell(0);
					cell.setCellValue("----LIST OF ALL THE FIELDS NOT CONTAINS req_ AND opt_ EXCEPT READONLY FIELDS ----");
					break;
					
				case 10: 
					row = sheet.createRow(1);
					cell = sheet.getRow(1).createCell(0);
					cell.setCellValue("----TO CHECK THE FORMAT OF THE DATE ----");
					break;
					
				case 11: 
					row = sheet.createRow(1);
					cell = sheet.getRow(1).createCell(0);
					cell.setCellValue("----LIST OF THE REPEATED NAMES----");
					break;
					
				case 12: 
					row = sheet.createRow(1);
					cell = sheet.getRow(1).createCell(0);
					cell.setCellValue("----AUTO SAVE FUNCTIONALITY EXISTS OR NOT----");
					break;
					
				case 13: 
					row = sheet.createRow(1);
					cell = sheet.getRow(1).createCell(0);
					cell.setCellValue("----FORMS READY EVENT SCRIPT EXISTS OR NOT----");
					break;
					
				case 14: 
					row = sheet.createRow(1);
					cell = sheet.getRow(1).createCell(0);
					cell.setCellValue("----SAME XML OF XDP EXISTS OR NOT----");
					break;
					
				default: 
					System.out.println("no sheet");
				}
				
				sheet.autoSizeColumn(cellIndex);
				cell.setCellStyle(styleHeaderColor);
				styleHeaderColor.setFont(font);
				styleColor.setFont(font);			
				sheetIndex++;
				
			    row = sheet.createRow(3);
				sheet.autoSizeColumn(cellIndex);
				cell = sheet.getRow(3).createCell(0);
				cell.setCellValue("FIELD NAME");
				cell.setCellStyle(styleHeaderColor);
				cell = sheet.getRow(3).createCell(1);
				cell.setCellValue("PARENT NAME");
				cell.setCellStyle(styleHeaderColor);
				styleHeaderColor.setFont(font);
				styleColor.setFont(font);
				
				for (int temp = 0; temp < ((List) hashMapList).size(); temp++) {
					HashMap<ArrayList, ArrayList> map = (HashMap<ArrayList, ArrayList>) ((List) hashMapList).get(temp);
					Set<Entry<ArrayList, ArrayList>> entrySet = map.entrySet();
					Iterator<Entry<ArrayList, ArrayList>> entrySetIterator = entrySet.iterator();
					while (entrySetIterator.hasNext()) {
						Entry entry = entrySetIterator.next();
						List listKey = (List) entry.getKey();
						List listParent = (List) entry.getValue();
						int listKeySize = listKey.size();
						for (int i = 0; i < listKeySize; i++) {
							row = sheet.createRow(++rowIndex);
							String valueIs = listKey.get(i).toString();
							sheet.autoSizeColumn(cellIndex);
							sheet.autoSizeColumn(cellIndex1);
							cell = sheet.getRow(rowIndex).createCell(0);
							cell.setCellValue(listKey.get(i).toString());
							cel = sheet.getRow(rowIndex).createCell(1);
							cel.setCellValue(listParent.get(i).toString());
							if (!listKey.get(i).toString().equals("")) {
								cell.setCellStyle(styleBorder);
								cel.setCellStyle(styleBorder);
								if (listKey.get(i).toString().endsWith(".xdp")) {
									cell.setCellStyle(styleColor);
								}
							}		
						}
					}
				}//end for loop
			}
			//C:/Users/Megha/.jenkins/jobs/Test/workspace/ExcelReport/Result.xlsx
			FileOutputStream output = new FileOutputStream(new File(
		    		"C:/Users/Group10 User/.jenkins/jobs/ContIntegration/workspace/ExcelReport/Result.xlsx"));
			wb.write(output);
			
			System.out.println("Excel File is created......!!!!");
			output.flush();
			output.close();
		}catch (Exception e) {
	        throw e;
	  }

  
 }
 
 public boolean fileDelete(){
	 File f = null;
     boolean bool = false;
     
     try{
        
        f = new File("C:/Users/Group10 User/.jenkins/jobs/ContIntegration/workspace/ExcelReport/Result.xlsx");       
        // tries to delete a non-existing file
        bool = f.delete();        
        // prints
        System.out.println("File deleted: "+bool);
     }catch(Exception e){
        // if any error occurs
        e.printStackTrace();
     }
	return true;
	 
 }
	
public String mainKeyName(Node node,String value,List drawNameArrList){
		int numDrawAttr=0;
		String resultValue="m";
	    String attrValue=null;
		if(node.hasAttributes()){
   		  numDrawAttr = node.getAttributes().getLength();
		}
		 for (int loopIndex =0; loopIndex<numDrawAttr; loopIndex++) {
		   	 Node attribute = node.getAttributes().item(loopIndex);
		     String attrName= attribute.getNodeName();
		     attrValue=attribute.getNodeValue();
		   	 value = attrValue.concat("_"+value); 
		   	 resultValue=value;
       	 }	
		 //attMap.put(value, "Draw");
		 drawNameArrList.add(value);		  
		 return  resultValue;//to get hash map put attmap here
    }
			
 public  HashMap obName(Node node,HashMap attMap){	   
   if(node.hasChildNodes()){
		int childNodeLen= node.getChildNodes().getLength();
		NodeList childNodeList=node.getChildNodes();
		String firstChild=node.getFirstChild().toString();
		for(int i=0;i<childNodeLen;i++){
			Node childNode=node.getChildNodes().item(i);
			short tag=childNode.getNodeType();
			if(tag==childNode.ELEMENT_NODE){
				Node tagNode = getElTagName(childNode);
				String tagNodeName= tagNode.getNodeName();
			    if ((tagNodeName.equals("ui")&& tagNode.hasChildNodes())) {
				    String object_type = tagNode.getFirstChild().getNextSibling().getNodeName();
					attMap.put("OBJECT_TYPE", object_type);					
			    }
		    }			
	     }
     }
  // System.out.println("obname is :" +attMap);
   return attMap;	  
 }
	
  public HashMap parseForParent(Node node, String Value,HashMap attMap){		
	if(node.getParentNode() != null){
		Node parentNode=node.getParentNode();
            if (parentNode.getNodeType()==parentNode.ELEMENT_NODE) {
                int numAttr=0;
	            if (parentNode.hasAttributes()) {
	                 numAttr =parentNode.getAttributes().getLength();
	             }
	            for (int loopIndex =0; loopIndex<numAttr; loopIndex++) {
	            	 Node attribute = parentNode.getAttributes().item(loopIndex);
	            	 if(attribute.getNodeName().equals("name")){	            		 
	            		 String name =attribute.getNodeValue();
	            		 Value = name.concat("/"+Value);
	            		 }
            		 attMap.put("PARENT_NAME", Value);
            		 parseForParent(parentNode,Value,attMap);
	              }	            	 
	            }
         }
	//System.out.println("parse for parent is : " +attMap);
	return attMap;
	}
  
  public HashMap drawInnerFormReady(Node node, HashMap attMap){
	  int subChilds = node.getChildNodes().getLength();
		NodeList subChildsList =node.getChildNodes();
		for(int temp = 0; temp < subChilds; temp++){
			Node childNode = subChildsList.item(temp);
			String childValue = childNode.getNodeName();
			//System.out.println("value is : " +childValue);
			if (childValue.equals("event")){
				if( subChildsList.item(temp).hasAttributes()){
					 NamedNodeMap eventAttr = childNode.getAttributes();
					int eventAttrLen = childNode.getAttributes().getLength();
					for(int j=0; j<eventAttrLen; j++){
						String name = eventAttr.item(j).getNodeName();
						String value = eventAttr.item(j).getNodeValue();
						attMap.put(name, value);
						
					}
				}
			}
		}
	return attMap;
	
}
  
 public HashMap drawInnerChilds(Node node,HashMap attMap){
	 if(node.hasChildNodes()){
		 List list=new ArrayList();
		 HashMap<String,String> drawMap = new HashMap<String, String>();
		 int childNodeLen= node.getChildNodes().getLength();		 
		 for(int i=0;i<childNodeLen;i++){				
			 Node childNode= node.getChildNodes().item(i);
			 //System.out.println("childNode is :"+childNode.getNodeName());
	    	 if(childNode.getNodeType() == childNode.ELEMENT_NODE){		    		
	    		 Node tagName= getElTagName(childNode);
	    		 String tagNodeName= tagName.getNodeName();		    		 
	    		 //System.out.println("tagNodeName is :"+tagName);//it gives the name without #text list
	    		 int numAttr=0;
	    		   if(tagName.hasAttributes()){
		    		    numAttr = tagName.getAttributes().getLength();		    		 
	    		        for(int j=0;j<numAttr;j++){
		    			      Node attr=tagName.getAttributes().item(j);
		    			      String attrName=attr.getNodeName();
		    			      String attrNameValue=attr.getNodeValue();
		    			      String primaryKeyDraw = tagNodeName+'_'+attrName;
			    			  attMap.put(primaryKeyDraw,attrNameValue);			    			 
	    		         }		    		        
	    		    }				    	  
	    	   }else if(childNode.getNodeType() == childNode.TEXT_NODE){
	    		   
	    		     Node tagName= getElTagName(childNode);
		    		 String tagNodeName= childNode.getNodeName();//it is showing the script
		    		 String tagNodeValue= childNode.getNodeValue().trim();//to remove the value that not contain value		    		 
		    		 if(!tagNodeValue.equals("")){
		    			 String nodeName=node.getParentNode().getNodeName();//gives main node name(eg:event)
		    	         String nodeNameChild= childNode.getParentNode().getNodeName();//gives childnode of main node name(eg:script)			    			
		    			 int numAttr=0;
			    		   if(node.getParentNode().hasAttributes()){
				    		  numAttr = node.getParentNode().getAttributes().getLength();		    		 				    		        
			    			      Node attr=node.getParentNode().getAttributes().item(0);
			    			      String attrName=attr.getNodeName();
			    			      String attrNameValue=attr.getNodeValue();
			    			      String primaryKey=nodeName+'_'+nodeNameChild+'_'+attrNameValue;
			    			      attMap.put(primaryKey,tagNodeValue);		    			 			    		        		    		        
			    		    }else{
			    		    	String primaryKey1=nodeName+'_'+nodeNameChild;
				    		    attMap.put(primaryKey1,tagNodeValue);
				    		 }			    						    			
			    	} 
		       }
		      drawInnerChilds(childNode, attMap);
		     
			 }
		 }
		 return attMap;
	 }
		 

	 public Node getElTagName(Node node){		
  		if(node.hasChildNodes()){
  			NodeList childNode=node.getChildNodes();
  			for(int i=0;i<childNode.getLength();i++){
  				Node nameIs=childNode.item(i);
  				short nodeType = nameIs.getNodeType();
  				if(nodeType==nameIs.ELEMENT_NODE){
  					getElTagName(nameIs);
  				}	  				
  			}	  			
  		}
	  return node;
     }
	 
	public  HashMap subFormPageBreak(Node node,int FlagPageBreak1,HashMap attrMap){
	 if(node.hasChildNodes()){
		int childNodeLen= node.getChildNodes().getLength();
		NodeList childNodeList=node.getChildNodes();
		for(int i=0;i<childNodeLen;i++){
			Node childNode=node.getChildNodes().item(i);
			short tag=childNode.getNodeType();
			if(tag==childNode.ELEMENT_NODE){
				Node tagNode = getElTagName(childNode);
				String tagNodeName= tagNode.getNodeName();
				if(tagNodeName.equals("keep")){
					FlagPageBreak1=1;	
				}
				attrMap.put("PAGE_BREAK",FlagPageBreak1);
				subFormPageBreak( childNode,FlagPageBreak1,attrMap);	
			}	
		}    	 
     }
	return attrMap;
   }
	 
   public void print(HashMap map, Integer tab) {
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            String key = pairs.getKey().toString();
            Object value = pairs.getValue();
            if (value instanceof Map) {
                System.out.println(getTab(tab) + key + " ==> {");
                print((HashMap) value, tab + 1);
                System.out.println(getTab(tab) + "}");
            }
            else if (value instanceof List) {
                System.out.println(getTab(tab) + key + " ==> {");
                print((List) value, tab + 1);
                System.out.println(getTab(tab) + "}");
            }
            else {
                System.out.println(getTab(tab) + key + " ==> " + value);
            }
        }
    }
   public void print(List list, Integer tab) {
        for (Integer index = 0; index < list.size(); index++) {
            Object value = list.get(index);
            if (value instanceof Map) {
                System.out.println(getTab(tab) + index.toString() + ": {");
                print((HashMap) value, tab + 1);
                System.out.println(getTab(tab) + "}");
            }
            else if (value instanceof List) {
                print((List) value, tab + 1);
            }
            else {
                System.out.println(getTab(tab) + index.toString() + ": " + value);
            }
        }
    }
   public String getTab(Integer tab) {
        String string = "";
        for (Integer index = 0; index < tab; index++) {
            string += "    ";
        }
        return string;
    }

}
