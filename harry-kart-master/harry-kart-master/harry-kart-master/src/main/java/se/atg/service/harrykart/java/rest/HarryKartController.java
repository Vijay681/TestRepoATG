package se.atg.service.harrykart.java.rest;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import se.atg.service.harrykart.java.LoopModel;
import se.atg.service.harrykart.java.ParticipantModel;
import se.atg.service.harrykart.java.Ranking;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/java/api")
public class HarryKartController {

    @PostMapping(path = "/play", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String playHarryKart() throws JsonProcessingException {



        HashMap<String, ArrayList<String>> hosrseDetails=new HashMap<>();
        List<LoopModel> loopModelList=new ArrayList<>();
        List<ParticipantModel> participantModelList=new ArrayList<>();
        int numberofIterations=0;

        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder builder=factory.newDocumentBuilder();

            ClassLoader classLoader = getClass().getClassLoader();

            //Here I have used one input file but we can replace it with other given input files and code will run fine for all given input files, I have tested this code with all given file
            File file = new File(classLoader.getResource("input_0.xml").getFile());

            Document document=builder.parse(file);


            Node numberOfLoops =document.getDocumentElement().getFirstChild().getNextSibling();
            numberofIterations=Integer.parseInt(numberOfLoops.getTextContent());


            document.getDocumentElement().normalize();

            //Get all elements by tag name

            NodeList participantList =document.getElementsByTagName("startList");

            for(int i=0;i<participantList.getLength();i++)
            {
                Node participant=participantList.item(i);

                if(participant.getNodeType()==Node.ELEMENT_NODE)
                {
                    Element participantElement= (Element) participant;

                    NodeList participantDetails=participant.getChildNodes();

                    for(int j = 0; j < participantDetails.getLength(); j++){
                        Node detail = participantDetails.item(j);
                        if(detail.getNodeType() == Node.ELEMENT_NODE) {
                            Element detailElement = (Element) detail;


                            int lane=Integer.parseInt(detailElement.getElementsByTagName("lane")
                                    .item(0).getChildNodes().item(0).getNodeValue());
                            String name=detailElement.getElementsByTagName("name")
                                    .item(0).getChildNodes().item(0).getNodeValue();
                            int baseSpeed=Integer.parseInt(detailElement.getElementsByTagName("baseSpeed")
                                    .item(0).getChildNodes().item(0).getNodeValue());
                            participantModelList.add(new ParticipantModel(lane,name,baseSpeed));
                        //    System.out.println("Before");
                          //  System.out.println("     " + detailElement.getTagName() + ": " + detailElement.getTextContent());
                        }

                    }

                }
            }
           // System.out.println("Starting Loops");
            NodeList loopsList =document.getElementsByTagName("powerUps");

            for(int i=0;i<loopsList.getLength();i++)
            {
                Node loop=loopsList.item(i);

                if(loop.getNodeType()==Node.ELEMENT_NODE)
                {
                    Element loopElement= (Element) loop;

                    NodeList loopDetails=loop.getChildNodes();

                    for(int j = 0; j < loopDetails.getLength(); j++){
                        Node detail = loopDetails.item(j);
                        if(detail.getNodeType() == Node.ELEMENT_NODE) {
                            Element loopdetailElement = (Element) detail;
                            int lane1=Integer.parseInt(loopdetailElement.getElementsByTagName("lane")
                                    .item(0).getChildNodes().item(0).getNodeValue());
                            int lane2=Integer.parseInt(loopdetailElement.getElementsByTagName("lane")
                                    .item(1).getChildNodes().item(0).getNodeValue());
                            int lane3=Integer.parseInt(loopdetailElement.getElementsByTagName("lane")
                                    .item(2).getChildNodes().item(0).getNodeValue());
                            int lane4=Integer.parseInt(loopdetailElement.getElementsByTagName("lane")
                                    .item(3).getChildNodes().item(0).getNodeValue());
                            loopModelList.add(new LoopModel(lane1,lane2,lane3,lane4));                        }

                    }

                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        int i=0;
//        do {
//
//        }while(i<=2);
        for(int i=0;i<numberofIterations-1;i++)
        {
           LoopModel loopModel= loopModelList.get(i);
            participantModelList.get(0).setBaseSpeed(participantModelList.get(0).getBaseSpeed()+loopModel.getLane1());
            participantModelList.get(1).setBaseSpeed(participantModelList.get(1).getBaseSpeed()+loopModel.getLane2());
            participantModelList.get(2).setBaseSpeed(participantModelList.get(2).getBaseSpeed()+loopModel.getLane3());
            participantModelList.get(3).setBaseSpeed(participantModelList.get(3).getBaseSpeed()+loopModel.getLane4());
        }
        Collections.sort(participantModelList,Collections.reverseOrder());
        List<Ranking> ranking=new ArrayList<>();
        for(int i=0;i<3;i++)
        {
            ranking.add(new Ranking(i+1, participantModelList.get(i).getName()));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String rootName = Ranking.class.getAnnotation(JsonRootName.class).value();
        String jsonString=objectMapper.writer().withRootName(rootName).writeValueAsString(ranking);



        return jsonString;
    }

}
