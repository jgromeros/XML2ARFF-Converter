package converter;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import jg.xml2arff.vo.PostVO;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLSaxHandler extends DefaultHandler {

    private PostVO post;
    private ArrayList<PostVO> posts;
    private int count = 0;

    public XMLSaxHandler() {
        posts = new ArrayList<PostVO>();
    }

    public ArrayList<PostVO> generateArff(String filepath) {
        parseDocument(filepath);
        return posts;
    }

    private void parseDocument(String filepath) {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        try {
            SAXParser sp = spf.newSAXParser();
            sp.parse(filepath, this);
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public void startElement(String uri, String localName, String qName, Attributes attrs) throws SAXException {
        if (qName.equals("row")) {
            if (attrs.getValue("PostTypeId").equals("1")) {
                post = new PostVO();
                post.setId(Integer.valueOf(attrs.getValue("Id")));
                post.setPostTypeId(Integer.valueOf(attrs.getValue("PostTypeId")));
                post.setAcceptedAnswerId(attrs.getValue("AcceptedAnswerId") == null ? null :
                        Integer.valueOf(attrs.getValue("AcceptedAnswerId")));
                post.setParentId(attrs.getValue("ParentId") == null ? null :
                        Integer.valueOf(attrs.getValue("ParentId")));
                post.setCreationDate(attrs.getValue("CreationDate"));
                post.setScore(Integer.valueOf(attrs.getValue("Score")));
                post.setViewCount(Integer.valueOf(attrs.getValue("ViewCount")));
                post.setBody(attrs.getValue("Body"));
                post.setOwnerUserId(attrs.getValue("OwnerUserId") == null ? null :
                        Integer.valueOf(attrs.getValue("OwnerUserId")));
                post.setOwnerDisplayName(attrs.getValue("OwnerDisplayName"));
                post.setLastEditorUserId(attrs.getValue("LastEditorUserId") == null ? null :
                        Integer.valueOf(attrs.getValue("LastEditorUserId")));
                post.setLastEditorDisplayName(attrs.getValue("LastEditorDisplayName"));
                post.setLastEditDate(attrs.getValue("LastEditDate"));
                post.setLastActivityDate(attrs.getValue("LastActivityDate"));
                post.setTitle(attrs.getValue("Title"));
                post.setTags(attrs.getValue("Tags"));
                post.setAnswerCount(Integer.valueOf(attrs.getValue("AnswerCount")));
                post.setCommentCount(Integer.valueOf(attrs.getValue("CommentCount")));
                post.setFavoriteCount(attrs.getValue("FavoriteCount") == null ? null :
                        Integer.valueOf(attrs.getValue("FavoriteCount")));
                post.setClosedDate(attrs.getValue("ClosedDate"));
                post.setCommunityOwnedDate(attrs.getValue("CommunityOwnedDate"));
            }
        }
    }

    /**
     * Seems like this needs to avoid out of memory errors by setting a maximum number of instances Original comment:
     * This dirty ifelse branching is to avoid out of memory error
     */
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("row")) {
            if (count == XMLtoARFFConverter.NUMBER_OF_INSTANCES) {
                ARFFGenerator createArff = new ARFFGenerator();
                createArff.generateARFF(posts);
                System.exit(0);
            } else {
                if (post != null) {
                    posts.add(post);
                    count = count + 1;
                    post = null;
                }
            }
        }
    }
}
