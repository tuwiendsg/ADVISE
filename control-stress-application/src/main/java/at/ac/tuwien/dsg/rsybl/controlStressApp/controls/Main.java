package at.ac.tuwien.dsg.rsybl.controlStressApp.controls;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.extl.jade.user.Nic;

import at.ac.tuwien.dsg.csdg.DependencyGraph;
import at.ac.tuwien.dsg.csdg.elasticityInformation.elasticityRequirements.SYBLElasticityRequirementsDescription;
import at.ac.tuwien.dsg.csdg.inputProcessing.multiLevelModel.InputProcessing;
import at.ac.tuwien.dsg.csdg.inputProcessing.multiLevelModel.abstractModelXML.CloudServiceXML;
import at.ac.tuwien.dsg.csdg.inputProcessing.multiLevelModel.deploymentDescription.DeploymentDescription;
import at.ac.tuwien.dsg.mela.common.configuration.metricComposition.CompositionRule;
import at.ac.tuwien.dsg.mela.common.configuration.metricComposition.CompositionRulesConfiguration;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		BufferedReader bufferedReader = null;
        try {
            //		FlexiantActions flexiantActions = new FlexiantActions();
            //		flexiantActions.cleanNics();
            //		System.out.println(flexiantActions.createNewServer("DataNode_new",
            //				"c7ee301c-813c-34f4-827a-783f22312dcb", 2, 4));
            //		flexiantActions.listAllNics();
            InputProcessing inputProcessing = new InputProcessing();
            JAXBContext jc;
            CloudServiceXML applicationDescription = null;
            DeploymentDescription deploymentDescription = null;
            try {
                    jc = JAXBContext.newInstance(CloudServiceXML.class);
            
            Unmarshaller u = jc.createUnmarshaller();

             applicationDescription = (CloudServiceXML)  u.unmarshal(new File("src/main/resources/config/serviceDescription_1.xml"));
            //applicationDescription = element.getValue();

		

            JAXBContext jc1 = JAXBContext.newInstance(DeploymentDescription.class);
            Unmarshaller u1 = jc1.createUnmarshaller();

            deploymentDescription = (DeploymentDescription) u1.unmarshal(new File("src/main/resources/config/newDeploymentDescription.xml"));

            } catch (JAXBException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }
           
            bufferedReader = new BufferedReader(new FileReader(new File("src/main/resources/config/compositionRules.xml")));
            String line = "";
            String compositionRules = "";
                try {
                    while ((line=bufferedReader.readLine())!=null){
                        compositionRules+=line;
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            DependencyGraph dependencyGraph = inputProcessing.loadDependencyGraphFromObjects(applicationDescription, new SYBLElasticityRequirementsDescription(),
                                            deploymentDescription);
            System.out.println(dependencyGraph.graphToString());
            RandomControlGenerationSalsa controlGeneration = new RandomControlGenerationSalsa(
                            dependencyGraph,compositionRules);
            controlGeneration.scaleOut(dependencyGraph.getNodeWithID("DataNodeUnit"));
            controlGeneration.scaleIn(dependencyGraph.getNodeWithID("DataNodeUnit"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
		
	}

}
