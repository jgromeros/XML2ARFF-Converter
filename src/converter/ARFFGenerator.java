package converter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import jg.xml2arff.vo.PostVO;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

/**
 * 914-631-9113 Generates ARFF file from XML http://weka.wikispaces.com/Creating+an+ARFF+file
 * 
 * @author zengr
 */
public class ARFFGenerator {

    ARFFGenerator() {
    }

    public Instances generateARFF(List<PostVO> posts) {
        FastVector atts = new FastVector();
        setupAttributes(atts);
        Instances data = new Instances("Stackoverflow_Dump", atts, 0);
        try {
            fillData(data, atts, posts);

            // Create file
            FileWriter fstream = new FileWriter("Output.arff", true);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(data.toString());

            // Close the output stream
            out.close();

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return data;
    }

    /**
     * Setup of the attributes to be used TODO: Identify and solve attributes that I will need to use as nominal
     * attributes IMPORTANT!!!!!!
     * 
     * @param atts
     */
    private void setupAttributes(FastVector atts) {
        atts.addElement(new Attribute("Id"));
        atts.addElement(new Attribute("PostTypeId"));
        atts.addElement(new Attribute("AcceptedAnswerId"));
        atts.addElement(new Attribute("ParentId"));
        atts.addElement(new Attribute("CreationDate", "yyyy-MM-dd"));// Date
        atts.addElement(new Attribute("Score"));
        atts.addElement(new Attribute("ViewCount"));
        atts.addElement(new Attribute("Body", (FastVector) null));// String
        atts.addElement(new Attribute("OwnerUserId"));
        atts.addElement(new Attribute("LastEditorUserId"));
        atts.addElement(new Attribute("LastEditDate", "yyyy-MM-dd"));// Date
        atts.addElement(new Attribute("LastActivityDate", "yyyy-MM-dd"));// Date
        atts.addElement(new Attribute("Title", (FastVector) null));// String
        atts.addElement(new Attribute("Tags", (FastVector) null));// String
        atts.addElement(new Attribute("AnswerCount"));
        atts.addElement(new Attribute("CommentCount"));
        atts.addElement(new Attribute("FavoriteCount"));
        atts.addElement(new Attribute("ClosedDate", "yyyy-MM-dd"));// Date
        atts.addElement(new Attribute("CommunityOwnedDate", "yyyy-MM-dd"));// Date
    }

    /**
     * Fill data in the instances
     * 
     * @param atts
     * @throws ParseException
     */
    private void fillData(Instances data, FastVector atts, List<PostVO> posts) throws ParseException {
        double[] vals;
        for (int i = 0; i < posts.size(); i++) {
            vals = new double[data.numAttributes()];
            vals[0] = posts.get(i).getId();
            vals[1] = posts.get(i).getPostTypeId();
            vals[2] = posts.get(i).getAcceptedAnswerId() == null ? Instance.missingValue() : posts.get(i)
                    .getAcceptedAnswerId();
            vals[3] = posts.get(i).getParentId() == null ? Instance.missingValue() : posts.get(i).getParentId();
            vals[4] = data.attribute(4).parseDate(posts.get(i).getCreationDate());// Date
            vals[5] = posts.get(i).getScore();
            vals[6] = posts.get(i).getViewCount();
            vals[7] = data.attribute(7).addStringValue(posts.get(i).getBody());// String
            vals[8] = posts.get(i).getOwnerUserId() == null ? Instance.missingValue() : posts.get(i).getOwnerUserId();
            vals[9] = posts.get(i).getLastEditorUserId() == null ? Instance.missingValue() :
                    posts.get(i).getLastEditorUserId();
            vals[10] = posts.get(i).getLastEditDate() == null ? Instance.missingValue() :
                    data.attribute(10).parseDate(posts.get(i).getLastEditDate());// Date
            vals[11] = data.attribute(11).parseDate(posts.get(i).getLastActivityDate());// Date
            vals[12] = data.attribute(12).addStringValue(posts.get(i).getTitle());// String
            vals[13] = data.attribute(13).addStringValue(posts.get(i).getTags());// String
            vals[14] = posts.get(i).getAnswerCount();
            vals[15] = posts.get(i).getCommentCount();
            vals[16] = posts.get(i).getFavoriteCount() == null ? Instance.missingValue() :
                    posts.get(i).getFavoriteCount();
            vals[17] = posts.get(i).getClosedDate() == null ? Instance.missingValue() :
                    data.attribute(17).parseDate(posts.get(i).getClosedDate());// Date
            vals[18] = posts.get(i).getCommunityOwnedDate() == null ? Instance.missingValue() :
                    data.attribute(18).parseDate(posts.get(i).getCommunityOwnedDate());// Date
            data.add(new Instance(1.0, vals));
        }
    }

}