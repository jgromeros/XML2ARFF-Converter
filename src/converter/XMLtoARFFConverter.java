package converter;

/**
 * Main class for converter from Stack Overflow XML dump to ARFF format.
 * 
 * @author utkarsh2012
 *
 */
public class XMLtoARFFConverter {

    /**
     * Number of instances that can be handled by the machine. Feel free to change it according to your machine Original
     * comment: "to avoid heap space error, select it according to your machine
     */
    public static final int NUMBER_OF_INSTANCES = 100000;

    /**
     * Main method for calling the converter
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        String xmlFilePath = "/home/jgromero/Downloads/stackoverflow-data-for-challenge/Posts1402.xml";
        XMLSaxHandler xmlObj = new XMLSaxHandler();
        xmlObj.generateArff(xmlFilePath);
    }

}