package it.polito.dp2.WF.sol2;

import java.io.File;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

public final class WorkFlowModel {
	
	private WorkFlowModel() {}
	
	private static Node getRootNode()
	{
		try
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
	        File xmlFile = new File(System.getProperty("it.polito.dp2.WF.sol1.WFInfo.file"));
	        /*
	        byte[] encoded = Files.readAllBytes(Paths.get(xmlFile.getPath()));
	        InputSource is = new InputSource();
	        is.setCharacterStream(new StringReader(new String(encoded)));
	        Document dom = builder.parse(is);
	        */
	        Document dom = builder.parse(xmlFile);
	        dom.normalize();
	        if(dom.hasChildNodes())
	        	return dom.getLastChild();
		}
		catch (Exception ex)
		{
			return (Node) ex;
	    }
		return null;
	}
	
	private static List<Node> getChildNodesByType(Node root, String type)
	{
		List<Node> ret = new ArrayList<Node>();
		if(root != null)
		{
			NodeList nlChilds = root.getChildNodes();
            for (int iChild = 0; iChild < nlChilds.getLength(); iChild++)
            {
            	Node currentNode = nlChilds.item(iChild);
            	if (currentNode.getNodeType() == Node.ELEMENT_NODE)
            		if (currentNode.getNodeName().trim().equals(type))
            			ret.add(currentNode);
            }
		}

		return ret;
	}
	
	public static List<Node> allWorkflow()
	{
		return getChildNodesByType(getRootNode(),"workflow");
	}
	
	public static List<Node> allProcesses()
	{
		List<Node> ret = new ArrayList<Node>();
		
		for(Node workflowNode:allWorkflow())
			for(Node processNode:getChildNodesByType(workflowNode,"process"))
				ret.add(processNode);
		
		return ret;
	}
	
	public static List<Node> whereProcesses(String workflowName)
	{
		List<Node> ret = new ArrayList<Node>();
		Node workflowNode = findWorkflow(workflowName);
				
		for(Node processNode:getChildNodesByType(workflowNode,"process"))
			ret.add(processNode);
		
		return ret;
	}
	
	public static Node findWorkflow(String name)
	{	
		for(Node currentNode:allWorkflow())
		{
			NamedNodeMap nnm = currentNode.getAttributes();
			if (nnm != null && nnm.getLength() > 0)
				for (int iAttr=0; iAttr < nnm.getLength(); iAttr++)
            	   if(nnm.item(iAttr).getNodeName() == "name" && nnm.item(iAttr).getNodeValue().trim().equals(name))
            		   return currentNode;
		}
		
		return null;
	}
	
	public static HashMap<String,String> getAttibutes(Node currentNode)
	{
		HashMap<String,String> ret = new HashMap<String,String>();
		
		if (currentNode != null)
		{
			NamedNodeMap nnm = currentNode.getAttributes();
			if(nnm.getLength() > 0)
		        for (int iAttr=0; iAttr < nnm.getLength(); iAttr++)
		        	ret.put(nnm.item(iAttr).getNodeName(), nnm.item(iAttr).getNodeValue());
		}
		
		return ret;
	}

	public static String getNodeValue(Node currentNode)
	{
		if(currentNode != null)
			if (currentNode.getNodeType() == Node.ELEMENT_NODE && currentNode.hasChildNodes())
			{
	               Node nTextChild = currentNode.getChildNodes().item(0);
	               return nTextChild.getNodeValue();
	        }
		return null;
	}
	
	public static List<Node> allActionStatus(Node process)
	{
		return getChildNodesByType(process, "actionStatus");
	}
	
	public static List<Node> allActions(Node workflow)
	{
		return getChildNodesByType(workflow, "action");
	}

	public static Node findAction(Node workflow, String name)
	{	
		if(workflow != null)
			for(Node currentNode:allActions(workflow))
			{
				NamedNodeMap nnm = currentNode.getAttributes();
				if (nnm != null && nnm.getLength() > 0)
					for (int iAttr=0; iAttr < nnm.getLength(); iAttr++)
		            	   if(nnm.item(iAttr).getNodeName() == "name" && nnm.item(iAttr).getNodeValue().trim().equals(name))
		            		   return currentNode;
			}
		
		return null;
	}

	public static Node getRole(Node parent)
	{
		if(parent != null)
		{
			List<Node> childs = getChildNodesByType(parent, "role");
			if(childs.size() == 1)
				return childs.get(0);
		}
		return null;
			
	}
	
	public static Node getActionName(Node parent)
	{
		if(parent != null)
		{
			List<Node> childs = getChildNodesByType(parent, "actionName");
			if(childs.size() == 1)
				return childs.get(0);	
		}
		return null;
	}
	
	public static Node getActor(Node parent)
	{
		if(parent != null)
		{
			List<Node> childs = getChildNodesByType(parent, "actor");
			if(childs.size() == 1)
				return childs.get(0);
		}
		return null;
			
	}
	
	public static List<Node> followingActions(Node action)
	{
		if(action != null)
		{
			List<Node> childs = getChildNodesByType(action, "followingActions");
			if(childs.size() == 1)
				return getChildNodesByType(childs.get(0), "actionName");
		}
		return null;
			
	}
}
