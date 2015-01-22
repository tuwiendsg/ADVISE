package at.ac.tuwien.dsg.rsybl.enforcementPlugins.salsa;


import java.util.ArrayList;
import java.util.List;





import at.ac.tuwien.dsg.csdg.DependencyGraph;
import at.ac.tuwien.dsg.csdg.Node;
import at.ac.tuwien.dsg.csdg.SimpleRelationship;
import at.ac.tuwien.dsg.csdg.Node.NodeType;
import at.ac.tuwien.dsg.csdg.Relationship.RelationshipType;
import at.ac.tuwien.dsg.rsybl.utils.RuntimeLogger;
import at.ac.tuwien.dsg.rsybl.enforcementPlugins.salsa.common.DefaultSalsaClient;

import at.ac.tuwien.dsg.rSybl.dataProcessingUnit.api.MonitoringAPIInterface;


public class EnforcementSALSAAPI  {

    private Node controlledService;
    private DefaultSalsaClient salsaClient;
    private MonitoringAPIInterface monitoring;

    public EnforcementSALSAAPI(Node cloudService) {
        salsaClient = new DefaultSalsaClient(cloudService);
        this.controlledService = cloudService;

    }

    public Node findNode(String id) {
        boolean found = false;
        if (!found) {
            if (id.equalsIgnoreCase(controlledService.getId())) {
                found = true;
                return controlledService;
            }
        }
        Node topology = controlledService.getAllRelatedNodesOfType(
                RelationshipType.COMPOSITION_RELATIONSHIP,
                NodeType.SERVICE_TOPOLOGY).get(0);

        if (topology.getId().equalsIgnoreCase(id)) {
            found = true;
            return topology;
        }
        List<Node> topologies = new ArrayList<Node>();

        topologies.addAll(topology.getAllRelatedNodesOfType(
                RelationshipType.COMPOSITION_RELATIONSHIP,
                NodeType.SERVICE_TOPOLOGY));

        List<Node> componentsToExplore = new ArrayList<Node>();
        if (topology.getAllRelatedNodesOfType(
                RelationshipType.COMPOSITION_RELATIONSHIP,
                NodeType.SERVICE_UNIT) != null) {
            componentsToExplore.addAll(topology.getAllRelatedNodesOfType(
                    RelationshipType.COMPOSITION_RELATIONSHIP,
                    NodeType.SERVICE_UNIT));
        }
        while (!found && !topologies.isEmpty()) {
            Node currentTopology = topologies.get(0);
            topologies.remove(0);
            if (currentTopology.getId().equalsIgnoreCase(id)) {
                found = true;
                return currentTopology;
            } else {
                // System.err.println(currentTopology.getId());
                if (currentTopology.getAllRelatedNodesOfType(
                        RelationshipType.COMPOSITION_RELATIONSHIP,
                        NodeType.SERVICE_TOPOLOGY) != null
                        && currentTopology.getAllRelatedNodesOfType(
                                RelationshipType.COMPOSITION_RELATIONSHIP,
                                NodeType.SERVICE_TOPOLOGY).size() > 0) {
                    topologies.addAll(currentTopology.getAllRelatedNodesOfType(
                            RelationshipType.COMPOSITION_RELATIONSHIP,
                            NodeType.SERVICE_TOPOLOGY));
                }
                if (currentTopology.getAllRelatedNodesOfType(
                        RelationshipType.COMPOSITION_RELATIONSHIP,
                        NodeType.SERVICE_UNIT) != null
                        && currentTopology.getAllRelatedNodesOfType(
                                RelationshipType.COMPOSITION_RELATIONSHIP,
                                NodeType.SERVICE_UNIT).size() > 0) {
                    componentsToExplore.addAll(currentTopology
                            .getAllRelatedNodesOfType(
                                    RelationshipType.COMPOSITION_RELATIONSHIP,
                                    NodeType.SERVICE_UNIT));
                }
            }
        }
        // if (getCurrentReadLatency(arg0).isNaN())

        while (!found && !componentsToExplore.isEmpty()) {
            Node component = componentsToExplore.get(0);
            componentsToExplore.remove(0);
            if (component.getId().equalsIgnoreCase(id)) {
                found = true;
                return component;
            }
        }

        return null;
    }

    
    public void undeployService(Node serviceID) {

        salsaClient.undeployService(serviceID);

    }


    public boolean scaleOut(Node arg0) {
        //monitoring.enforcingActionStarted("ScaleOut", arg0);
        boolean res = false;
        Node o = arg0;
        RuntimeLogger.logger.info("Scaling out ... " + arg0 + " "
                + arg0.getNodeType());

        if (o.getNodeType() == NodeType.CODE_REGION) {
            res = scaleOutComponent(findComponentOfCodeRegion(arg0));
        }

        // TODO : enable just ComponentTopology level
        if (o.getNodeType() == NodeType.SERVICE_UNIT) {
            res = scaleOutComponent(o);
        }
        if (o.getNodeType() == NodeType.SERVICE_TOPOLOGY) {
            // TODO: make it possible to scale a set of component topologies

            Node master = null;
            Node slave = null;
            ArrayList<Node> comps = (ArrayList<Node>) o
                    .getAllRelatedNodesOfType(
                            RelationshipType.COMPOSITION_RELATIONSHIP,
                            NodeType.SERVICE_UNIT);

            for (Node comp : comps) {
                if (comp.getAllRelatedNodesOfType(RelationshipType.MASTER_OF) != null) {
                    master = comp;
                    slave = comp.getAllRelatedNodesOfType(
                            RelationshipType.MASTER_OF).get(0);
                }
            }

            // TODO:add for more slaves
            List<String> slavesPrivateIps = new ArrayList<String>();
            List<String> masterPrivateIps = new ArrayList<String>();
            for (String ip : slave.getAssociatedIps()) {
                if (ip.split("\\.")[0].length() == 2) {
                    slavesPrivateIps.add(ip);
                }
            }
            for (String ip : master.getAssociatedIps()) {
                if (ip.split("\\.")[0].length() == 2) {
                    masterPrivateIps.add(ip);
                }
            }
            int nb = 1;
            if (masterPrivateIps.size() != 0) {
                nb = slavesPrivateIps.size() / masterPrivateIps.size();
            }
            // maybe we implement cluster-level actions
            // cloudsOpenStackConnection.scaleOutCluster(master, slave,
            // nb,master.getAssociatedIps().get(0),controlledService);

        }

        return res;
        //monitoring.enforcingActionEnded("ScaleOut", arg0);

    }

    private void loadDeploymentDescription() {
        // try {
        // JAXBContext a = JAXBContext.newInstance( DeploymentDescription.class
        // );
        // Unmarshaller u = a.createUnmarshaller();
        // String deploymentDescriptionPath =
        // at.ac.tuwien.dsg.sybl.monitorandenforcement.utils.Configuration.getDeploymentDescriptionPath();
        // // RuntimeLogger.logger.info("Got here "+deploymentDescriptionPath);
        // //
        // RuntimeLogger.logger.info("Got here "+this.getClass().getClassLoader().getResourceAsStream(deploymentDescriptionPath));
        //
        // if (deploymentDescriptionPath!=null)
        // deploymentDescription = (DeploymentDescription) u.unmarshal(
        // this.getClass().getClassLoader().getResourceAsStream(deploymentDescriptionPath))
        // ;
        //
        // //RuntimeLogger.logger.info("Read deployment Descrption"+deploymentDescription.toString());
        //
        // } catch (Exception e) {
        // RuntimeLogger.logger.error("Error in reading deployment description"+e.toString());
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
    }

    private boolean scaleOutComponent(Node o) {
        boolean res = false;
        DependencyGraph graph = new DependencyGraph();
        graph.setCloudService(controlledService);
        RuntimeLogger.logger
                .info("~~~~~~~~~~~~~~~~~~~~~~Image from which we create "
                        + (String) o.getStaticInformation("DefaultImage"));
        String ip = salsaClient.scaleOut(o.getId());
        Node node = new Node();
        Node artifact = null;
        Node container = null;

        if (o.getAllRelatedNodesOfType(RelationshipType.HOSTED_ON_RELATIONSHIP, NodeType.ARTIFACT) != null && o.getAllRelatedNodesOfType(RelationshipType.HOSTED_ON_RELATIONSHIP, NodeType.ARTIFACT).size() > 0) {
            artifact = o.getAllRelatedNodesOfType(RelationshipType.HOSTED_ON_RELATIONSHIP, NodeType.ARTIFACT).get(0);

            if (artifact.getAllRelatedNodesOfType(RelationshipType.HOSTED_ON_RELATIONSHIP, NodeType.CONTAINER) != null && artifact.getAllRelatedNodesOfType(RelationshipType.HOSTED_ON_RELATIONSHIP, NodeType.CONTAINER).size() > 0) {

                container = artifact.getAllRelatedNodesOfType(RelationshipType.HOSTED_ON_RELATIONSHIP, NodeType.CONTAINER).get(0);
            }
        }

        if (!ip.equalsIgnoreCase("err") && !ip.equalsIgnoreCase("")) {
            node.getStaticInformation().put("IP", ip);
            node.setId(ip);
            node.setNodeType(NodeType.VIRTUAL_MACHINE);
            SimpleRelationship rel = new SimpleRelationship();

            rel.setTargetElement(node.getId());
            rel.setType(RelationshipType.HOSTED_ON_RELATIONSHIP);
            RuntimeLogger.logger.info("Adding to " + o.getId() + " vm with ip "
                    + ip);

            if (artifact == null && container == null) {
                rel.setSourceElement(o.getId());
                o.addNode(node, rel);
            } else {
                if (container == null) {
                    rel.setSourceElement(artifact.getId());
                    artifact.addNode(node, rel);
                } else {
                    rel.setSourceElement(container.getId());
                    container.addNode(node, rel);
                }
            }
            res = true;
        } else {
            res = false;
            RuntimeLogger.logger.info("IP is empty");
        }
        RuntimeLogger.logger.info("The controlled service is now "
                + controlledService.toString());

        monitoring.refreshServiceStructure(controlledService);
        return res;
    }

    private boolean scaleInComponent(Node toscale, String ip) {
        boolean res = false;
        DependencyGraph graph = new DependencyGraph();
        graph.setCloudService(controlledService);

        Node toBeScaled = graph.getNodeWithID(toscale.getId());
        Node toBeRemoved = graph.getNodeWithID(ip);
        RuntimeLogger.logger.info("Trying to remove  " + toBeRemoved.getId() + " From " + toBeScaled.getId());

        res = salsaClient.scaleIn(toBeRemoved.getId());

        RuntimeLogger.logger.info("Objects here" + toBeScaled + monitoring);
        toBeScaled.removeNode(toBeRemoved);
        monitoring.refreshServiceStructure(controlledService);

        return res;
    }

    private boolean scaleInComponent(String toscale) {
        boolean res = false;
        DependencyGraph graph = new DependencyGraph();
        graph.setCloudService(controlledService);

        Node toBeScaled = graph.getNodeWithID(toscale);

        Node artifact = null;
        Node container = null;
        Node toBeScaledArtifactorContainer = toBeScaled;
        if (toBeScaled.getAllRelatedNodesOfType(RelationshipType.HOSTED_ON_RELATIONSHIP, NodeType.ARTIFACT) != null && toBeScaled.getAllRelatedNodesOfType(RelationshipType.HOSTED_ON_RELATIONSHIP, NodeType.ARTIFACT).size() > 0) {
            artifact = toBeScaled.getAllRelatedNodesOfType(RelationshipType.HOSTED_ON_RELATIONSHIP, NodeType.ARTIFACT).get(0);

            if (artifact.getAllRelatedNodesOfType(RelationshipType.HOSTED_ON_RELATIONSHIP, NodeType.CONTAINER) != null && artifact.getAllRelatedNodesOfType(RelationshipType.HOSTED_ON_RELATIONSHIP, NodeType.CONTAINER).size() > 0) {

                container = artifact.getAllRelatedNodesOfType(RelationshipType.HOSTED_ON_RELATIONSHIP, NodeType.CONTAINER).get(0);
            }
        }
        boolean ok = false;
        if (artifact == null && container == null) {
            ok = toBeScaled.getAllRelatedNodesOfType(RelationshipType.HOSTED_ON_RELATIONSHIP, NodeType.VIRTUAL_MACHINE).size() > 1;
        } else {
            if (container == null) {
                ok = artifact.getAllRelatedNodesOfType(RelationshipType.HOSTED_ON_RELATIONSHIP, NodeType.VIRTUAL_MACHINE).size() > 1;
                toBeScaledArtifactorContainer = artifact;
            } else {
                ok = container.getAllRelatedNodesOfType(RelationshipType.HOSTED_ON_RELATIONSHIP, NodeType.VIRTUAL_MACHINE).size() > 1;
                toBeScaledArtifactorContainer = container;
            }
        }
        Node toBeRemoved = toBeScaledArtifactorContainer.getAllRelatedNodesOfType(RelationshipType.HOSTED_ON_RELATIONSHIP, NodeType.VIRTUAL_MACHINE).get(0);
        RuntimeLogger.logger.info("Trying to remove  " + toBeRemoved.getId() + " From " + toBeScaled.getId());

        res = salsaClient.scaleIn(toBeRemoved.getId());

        RuntimeLogger.logger.info("Objects here" + toBeScaled + monitoring);
        toBeScaledArtifactorContainer.removeNode(toBeRemoved);

        monitoring.refreshServiceStructure(controlledService);
        return res;
    }

    private boolean scaleInComponent(Node o) {
        boolean res = false;
        DependencyGraph graph = new DependencyGraph();
        graph.setCloudService(controlledService);

        Node toBeScaled = graph.getNodeWithID(o.getId());
        Node artifact = null;
        Node container = null;
        Node toBeScaledArtifactorContainer = toBeScaled;
        if (toBeScaled.getAllRelatedNodesOfType(RelationshipType.HOSTED_ON_RELATIONSHIP, NodeType.ARTIFACT) != null && toBeScaled.getAllRelatedNodesOfType(RelationshipType.HOSTED_ON_RELATIONSHIP, NodeType.ARTIFACT).size() > 0) {
            artifact = toBeScaled.getAllRelatedNodesOfType(RelationshipType.HOSTED_ON_RELATIONSHIP, NodeType.ARTIFACT).get(0);

            if (artifact.getAllRelatedNodesOfType(RelationshipType.HOSTED_ON_RELATIONSHIP, NodeType.CONTAINER) != null && artifact.getAllRelatedNodesOfType(RelationshipType.HOSTED_ON_RELATIONSHIP, NodeType.CONTAINER).size() > 0) {

                container = artifact.getAllRelatedNodesOfType(RelationshipType.HOSTED_ON_RELATIONSHIP, NodeType.CONTAINER).get(0);
            }
        }
        boolean ok = false;
        if (artifact == null && container == null) {
            ok = toBeScaled.getAllRelatedNodesOfType(RelationshipType.HOSTED_ON_RELATIONSHIP, NodeType.VIRTUAL_MACHINE).size() > 1;
        } else {
            if (container == null) {
                ok = artifact.getAllRelatedNodesOfType(RelationshipType.HOSTED_ON_RELATIONSHIP, NodeType.VIRTUAL_MACHINE).size() > 1;
                toBeScaledArtifactorContainer = artifact;
            } else {
                ok = container.getAllRelatedNodesOfType(RelationshipType.HOSTED_ON_RELATIONSHIP, NodeType.VIRTUAL_MACHINE).size() > 1;
                toBeScaledArtifactorContainer = container;
            }
        }
        Node toBeRemoved = toBeScaledArtifactorContainer.getAllRelatedNodesOfType(RelationshipType.HOSTED_ON_RELATIONSHIP, NodeType.VIRTUAL_MACHINE).get(0);
        RuntimeLogger.logger.info("Trying to remove  " + toBeRemoved.getId() + " From " + toBeScaled.getId());
        String cmd = "";
        String ip = toBeRemoved.getId();

        res = salsaClient.scaleIn(toBeRemoved.getId());

        RuntimeLogger.logger.info("Objects here" + toBeScaled + monitoring);
        toBeScaledArtifactorContainer.removeNode(toBeRemoved);

        monitoring.refreshServiceStructure(controlledService);
        return res;
    }

    public Node findControllerForComponent(Node c) {
        Node res = null;
        List<Node> componentTopologies = controlledService
                .getAllRelatedNodesOfType(
                        RelationshipType.COMPOSITION_RELATIONSHIP,
                        NodeType.SERVICE_TOPOLOGY);
        for (Node componentTopology : componentTopologies) {
            for (Node topology : componentTopology.getAllRelatedNodesOfType(
                    RelationshipType.COMPOSITION_RELATIONSHIP,
                    NodeType.SERVICE_TOPOLOGY)) {
                Node master = null;
                Node slave = null;
                ArrayList<Node> comps = (ArrayList<Node>) topology
                        .getAllRelatedNodesOfType(RelationshipType.COMPOSITION_RELATIONSHIP);

                for (Node comp : comps) {
                    if (comp.getAllRelatedNodesOfType(RelationshipType.MASTER_OF) != null) {
                        master = comp;
                        slave = comp.getAllRelatedNodesOfType(
                                RelationshipType.MASTER_OF).get(0);
                    }
                }
                if (slave.getId().equalsIgnoreCase(c.getId())) {
                    for (Node component : comps) {
                        if (component.getId().equalsIgnoreCase(master.getId())) {
                            res = component;
                        }
                    }
                }

                if (topology.getAllRelatedNodesOfType(
                        RelationshipType.COMPOSITION_RELATIONSHIP,
                        NodeType.SERVICE_TOPOLOGY) != null) {
                    for (Node top : topology.getAllRelatedNodesOfType(
                            RelationshipType.COMPOSITION_RELATIONSHIP,
                            NodeType.SERVICE_TOPOLOGY)) {
                        Node master1 = null;
                        Node slave1 = null;
                        ArrayList<Node> comps1 = (ArrayList<Node>) topology
                                .getAllRelatedNodesOfType(RelationshipType.COMPOSITION_RELATIONSHIP);

                        for (Node comp : comps) {
                            if (comp.getAllRelatedNodesOfType(RelationshipType.MASTER_OF) != null) {
                                master1 = comp;
                                slave1 = comp.getAllRelatedNodesOfType(
                                        RelationshipType.MASTER_OF).get(0);
                            }
                        }

                        if (slave1.getId().equalsIgnoreCase(c.getId())) {

                            List<Node> comps2 = top.getAllRelatedNodesOfType(
                                    RelationshipType.COMPOSITION_RELATIONSHIP,
                                    NodeType.SERVICE_UNIT);
                            for (Node component : comps2) {
                                if (component.getId().equalsIgnoreCase(
                                        master1.getId())) {
                                    res = component;
                                }
                            }
                        }
                    }
                }

            }
        }
        return res;
    }

    private Node findComponentOfCodeRegion(Node e) {
        boolean found = false;
        Node topology = controlledService.getAllRelatedNodesOfType(
                RelationshipType.COMPOSITION_RELATIONSHIP,
                NodeType.SERVICE_TOPOLOGY).get(0);
        List<Node> topologies = new ArrayList<Node>();
        topologies.addAll(topology.getAllRelatedNodesOfType(
                RelationshipType.COMPOSITION_RELATIONSHIP,
                NodeType.SERVICE_TOPOLOGY));

        List<Node> componentsToExplore = new ArrayList<Node>();
        if (topology.getAllRelatedNodesOfType(
                RelationshipType.COMPOSITION_RELATIONSHIP,
                NodeType.SERVICE_UNIT) != null) {
            componentsToExplore.addAll(topology.getAllRelatedNodesOfType(
                    RelationshipType.COMPOSITION_RELATIONSHIP,
                    NodeType.SERVICE_UNIT));
        }
        while (!topologies.isEmpty()) {
            Node currentTopology = topologies.get(0);
            topologies.remove(0);
            if (currentTopology.getAllRelatedNodesOfType(
                    RelationshipType.COMPOSITION_RELATIONSHIP,
                    NodeType.SERVICE_TOPOLOGY).size() > 0) {
                topologies.addAll(currentTopology.getAllRelatedNodesOfType(
                        RelationshipType.COMPOSITION_RELATIONSHIP,
                        NodeType.SERVICE_TOPOLOGY));
            }
            if (currentTopology.getAllRelatedNodesOfType(
                    RelationshipType.COMPOSITION_RELATIONSHIP,
                    NodeType.SERVICE_UNIT).size() > 0) {
                componentsToExplore.addAll(currentTopology
                        .getAllRelatedNodesOfType(
                                RelationshipType.COMPOSITION_RELATIONSHIP,
                                NodeType.SERVICE_UNIT));
            }

        }

        while (!componentsToExplore.isEmpty()) {
            Node component = componentsToExplore.get(0);
            componentsToExplore.remove(0);
            for (Node codeRegion : component.getAllRelatedNodesOfType(
                    RelationshipType.COMPOSITION_RELATIONSHIP,
                    NodeType.CODE_REGION)) {
                if (codeRegion.getId().equalsIgnoreCase(e.getId())) {
                    return component;
                }
            }
        }

        return null;
    }

    private Node findParentNode(String entityID, Node controlledService) {
        if (controlledService.getId().equalsIgnoreCase(entityID)) {
            return null;
        }
        Node topology = controlledService.getAllRelatedNodesOfType(
                RelationshipType.COMPOSITION_RELATIONSHIP,
                NodeType.SERVICE_TOPOLOGY).get(0);

        if (topology.getId().equalsIgnoreCase(entityID)) {
            // targetedNode=topology;
            return controlledService;
        }
        for (Node componentTopology : topology.getAllRelatedNodesOfType(
                RelationshipType.COMPOSITION_RELATIONSHIP,
                NodeType.SERVICE_TOPOLOGY)) {
            if (componentTopology.getId().equalsIgnoreCase(entityID)) {
                // targetedNode=componentTopology;
                return topology;
            }
            for (Node subTopology : componentTopology.getAllRelatedNodesOfType(
                    RelationshipType.COMPOSITION_RELATIONSHIP,
                    NodeType.SERVICE_TOPOLOGY)) {
                if (subTopology.getId().equalsIgnoreCase(entityID)) {
                    // targetedNode=subTopology;
                    return componentTopology;
                }
                for (Node comp : subTopology.getAllRelatedNodesOfType(
                        RelationshipType.COMPOSITION_RELATIONSHIP,
                        NodeType.SERVICE_UNIT)) {
                    if (comp.getId().equalsIgnoreCase(entityID)) {
                        // targetedNode=comp;
                        return subTopology;
                    }
                }
            }
            for (Node comp : componentTopology.getAllRelatedNodesOfType(
                    RelationshipType.COMPOSITION_RELATIONSHIP,
                    NodeType.SERVICE_UNIT)) {
                if (comp.getId().equalsIgnoreCase(entityID)) {
                    // targetedNode=comp;
                    return componentTopology;
                }
            }
        }
        return null;
    }

    public boolean scaleIn(Node arg0) {
        RuntimeLogger.logger.info("Scaling in..." + arg0.getId());
        boolean res = false;
        if (arg0.getNodeType() == NodeType.CODE_REGION) {
            res = scaleIn(findComponentOfCodeRegion(arg0));
        }

        // TODO : enable just ComponentTopology level
        if (arg0.getNodeType() == NodeType.SERVICE_UNIT) {
            res = scaleInComponent(arg0);
        }

        if (arg0.getNodeType() == NodeType.SERVICE_TOPOLOGY) {
            ArrayList<Node> comps = (ArrayList<Node>) arg0
                    .getAllRelatedNodesOfType(RelationshipType.COMPOSITION_RELATIONSHIP);
            Node master = null;
            Node slave = null;

            for (Node comp : comps) {
                if (comp.getAllRelatedNodesOfType(RelationshipType.MASTER_OF) != null) {
                    master = comp;
                    slave = comp.getAllRelatedNodesOfType(
                            RelationshipType.MASTER_OF).get(0);
                }
            }

            for (Node component : comps) {
                if (component.getId().equalsIgnoreCase(master.getId())) {
                    master = component;
                }
                if (component.getId().equalsIgnoreCase(slave.getId())) {
                    slave = component;
                }

            }

            for (String ip : master.getAssociatedIps()) {
                if (ip.split("\\.")[0].length() == 2) {
                   // monitoring.enforcingActionStarted("ScaleIn", arg0);

                    //monitoring.enforcingActionEnded("ScaleIn", arg0);
                    break;
                }
                // scale in on the number of components of the topology
            }
        }

//		if (arg0.getAllRelatedNodesOfType(
//				RelationshipType.HOSTED_ON_RELATIONSHIP,
//				NodeType.VIRTUAL_MACHINE).size() > 1) {
//			// RuntimeLogger.logger.info("Scaling in "+arg0.getId());
//			//monitoring.enforcingActionStarted("ScaleIn", arg0);
//
//			scaleInComponent(((Node) arg0));
//			//monitoring.enforcingActionEnded("ScaleIn", arg0);
//		}
        return res;
    }

    public boolean scaleIn(Node arg0, String ip) {
        RuntimeLogger.logger.info("Scaling in..." + arg0.getId());
        boolean res = false;
        if (arg0.getNodeType() == NodeType.CODE_REGION) {
            res = scaleIn(findComponentOfCodeRegion(arg0));
        }

        // TODO : enable just ComponentTopology level
        if (arg0.getNodeType() == NodeType.SERVICE_UNIT) {
            res = scaleInComponent(arg0, ip);
        }

        if (arg0.getNodeType() == NodeType.SERVICE_TOPOLOGY) {
            ArrayList<Node> comps = (ArrayList<Node>) arg0
                    .getAllRelatedNodesOfType(RelationshipType.COMPOSITION_RELATIONSHIP);
            Node master = null;
            Node slave = null;

            for (Node comp : comps) {
                if (comp.getAllRelatedNodesOfType(RelationshipType.MASTER_OF) != null) {
                    master = comp;
                    slave = comp.getAllRelatedNodesOfType(
                            RelationshipType.MASTER_OF).get(0);
                }
            }

            for (Node component : comps) {
                if (component.getId().equalsIgnoreCase(master.getId())) {
                    master = component;
                }
                if (component.getId().equalsIgnoreCase(slave.getId())) {
                    slave = component;
                }

            }

            for (String x : master.getAssociatedIps()) {
                if (x.split("\\.")[0].length() == 2) {
                   // monitoring.enforcingActionStarted("ScaleIn", arg0);

                    // monitoring.enforcingActionEnded("ScaleIn", arg0);
                    break;
                }
                // scale in on the number of components of the topology
            }
        }

//		if (arg0.getAllRelatedNodesOfType(
//				RelationshipType.HOSTED_ON_RELATIONSHIP,
//				NodeType.VIRTUAL_MACHINE).size() > 1) {
//			// RuntimeLogger.logger.info("Scaling in "+arg0.getId());
//			//monitoring.enforcingActionStarted("ScaleIn", arg0);
//
//			scaleInComponent(((Node) arg0));
//			//monitoring.enforcingActionEnded("ScaleIn", arg0);
//		}
        return res;
    }

    public boolean scaleIn(String toScale) {
        RuntimeLogger.logger.info("Scaling in..." + toScale);
        boolean res = false;

        res = scaleInComponent(toScale);

        return res;
    }

    public List<String> getElasticityCapabilities() {
        List<String> list = new ArrayList<String>();
        list.add("scaleOut");
        list.add("scaleIn");
        return list;
    }

    public List<String> getElasticityCapabilities(Node cloudService) {
        List<String> list = new ArrayList<String>();
        list.add("scaleOut");
        list.add("scaleIn");
        return list;
    }

    public void setControlledService(Node controlledService) {
        this.controlledService = controlledService;
    }

    public Node getControlledService() {
        // TODO Auto-generated method stub
        return controlledService;
    }


    public void setMonitoringPlugin(MonitoringAPIInterface monitoring) {
        this.monitoring = monitoring;
    }

    public boolean enforceAction(String actionName, Node entity) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean containsElasticityCapability(Node entity, String capability) {
        for (String cap : getElasticityCapabilities()) {
            if (cap.equalsIgnoreCase(capability)) {
                return true;
            }
        }
        return false;
    }

    
    public boolean scaleOut(double violationDegree, Node toBeScaled) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
