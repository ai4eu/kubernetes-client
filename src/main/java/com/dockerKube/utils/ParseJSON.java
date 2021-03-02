/*-
 * ===============LICENSE_START=======================================================
 * Acumos
 * ===================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property & Tech Mahindra. All rights reserved.
 * ===================================================================================
 * This Acumos software file is distributed by AT&T and Tech Mahindra
 * under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * This file is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ===============LICENSE_END=========================================================
 */

package com.dockerKube.utils;

import java.io.StringReader;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dockerKube.beans.ContainerBean;
import com.dockerKube.beans.DeploymentKubeBean;
import com.dockerKube.parsebean.Blueprint;
import com.dockerKube.parsebean.ConnectedTo;
import com.dockerKube.parsebean.DataBrokerBean;
import com.dockerKube.parsebean.DataBrokerMap;
import com.dockerKube.parsebean.InputField;
import com.dockerKube.parsebean.InputPort;
import com.dockerKube.parsebean.MapInputs;
import com.dockerKube.parsebean.MapOutputs;
import com.dockerKube.parsebean.Node;
import com.dockerKube.parsebean.NodeTree;
import com.dockerKube.parsebean.OperationSignature;
import com.dockerKube.parsebean.OperationSignatureList;
import com.dockerKube.parsebean.OutputField;
import com.dockerKube.parsebean.ProbeIndicator;
import com.dockerKube.parsebean.TypeAndRoleHierarchyList;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ParseJSON {

	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	/**
	 * getDataBrokerContainer method is used to get Databroker details
	 * 
	 * @param jsonString - json string for parse
	 * @throws Exception - exception thrown
	 * @return dataBrokerBean - DataBrokerBean obj return
	 */
	public DataBrokerBean getDataBrokerContainer(String jsonString) throws Exception {
		log.debug(" getDataBrokerContainer Start");
		log.debug("jsonString " + jsonString);
		ArrayList<String> list = new ArrayList<String>();
		DataBrokerBean dataBrokerBean = null;
		try {

			Object obj = new JSONParser().parse(new StringReader(jsonString));
			Iterator<Map.Entry> itr1 = null;
			JSONObject jo = (JSONObject) obj;
			JSONArray nodes = (JSONArray) jo.get(DockerKubeConstants.NODES);
			int nodeLength = -1;
			int dataBrokerVal = -1;
			ArrayList<Integer> dataBrokerList = new ArrayList<Integer>();
			ArrayList<Node> nodeList = new ArrayList<Node>();
			if (nodes != null) {
				Iterator itr3 = nodes.iterator();
				int nodeCount = 0;
				while (itr3.hasNext()) {
					Node node = new Node();
					itr1 = ((Map) itr3.next()).entrySet().iterator();
					log.debug("Nodes" + ++nodeCount);
					nodeLength++;
					String containerName = null, nodeType = null, script = null;
					while (itr1.hasNext()) {
						Map.Entry pair = itr1.next();
						if (pair != null && pair.getKey() != null && pair.getValue() != null) {
							String key = (String) pair.getKey();

							if (key != null && key.equalsIgnoreCase(DockerKubeConstants.NODE_TYPE)) {
								nodeType = (String) pair.getValue();
								if (nodeType != null && nodeType.equalsIgnoreCase(DockerKubeConstants.DATA_BROKER)) {
									dataBrokerList.add(nodeLength);
								}
							}

						}
					}
				}
				log.debug("dataBrokerList Nodes " + dataBrokerList);
				if (dataBrokerList != null && dataBrokerList.size() > 0) {
					for (Integer num : dataBrokerList) {
						dataBrokerVal = num;
						log.debug("dataBrokerVal " + dataBrokerVal);
						dataBrokerBean = new DataBrokerBean();
						DataBrokerMap dataBrokerMap = new DataBrokerMap();
						ArrayList<MapInputs> mapInputList = new ArrayList<MapInputs>();
						ArrayList<MapOutputs> mapOutputList = new ArrayList<MapOutputs>();

						log.debug("DataBroker Nodes " + nodes.get(dataBrokerVal));
						JSONObject jsonObject = (JSONObject) nodes.get(dataBrokerVal);
						String containerName = "";
						String nodeType = "";
						String image = "";
						String protoUri = "";
						JSONArray mapInputArray = null;
						JSONArray mapoutputArray = null;
						JSONObject dataBrokerObject = null;
						if (jsonObject != null) {
							if (jsonObject.get(DockerKubeConstants.CONTAINER_NAME) != null) {
								containerName = (String) jsonObject.get(DockerKubeConstants.CONTAINER_NAME);

							}
							if (jsonObject.get(DockerKubeConstants.NODE_TYPE) != null) {
								nodeType = (String) jsonObject.get(DockerKubeConstants.NODE_TYPE);
							}
							if (jsonObject.get(DockerKubeConstants.IMAGE) != null) {
								image = (String) jsonObject.get(DockerKubeConstants.IMAGE);
							}
							if (jsonObject.get(DockerKubeConstants.PROTO_URI) != null) {
								protoUri = (String) jsonObject.get(DockerKubeConstants.PROTO_URI);
								dataBrokerBean.setProtobufFile(protoUri);
							}
							if (jsonObject.get(DockerKubeConstants.DATA_BROKER_MAP) != null) {
								dataBrokerObject = (JSONObject) jsonObject.get(DockerKubeConstants.DATA_BROKER_MAP);
								if (dataBrokerObject != null) {
									if (dataBrokerObject.get(DockerKubeConstants.DATA_BROKER_TYPE) != null) {
										String dataBrokerType = (String) dataBrokerObject
												.get(DockerKubeConstants.DATA_BROKER_TYPE);
										log.debug("dataBrokerType " + dataBrokerType);
										if (dataBrokerType != null
												&& dataBrokerType.equalsIgnoreCase(DockerKubeConstants.CSV_FILE_NAME)) {
											dataBrokerMap.setDataBrokerType((String) dataBrokerObject
													.get(DockerKubeConstants.DATA_BROKER_TYPE));
										} else {
											dataBrokerBean = null;
											break;
										}

									}
									if (dataBrokerObject.get(DockerKubeConstants.SCRIPT) != null) {
										dataBrokerMap
												.setScript((String) dataBrokerObject.get(DockerKubeConstants.SCRIPT));
									}
									if (dataBrokerObject.get(DockerKubeConstants.TARGET_SYSTEM_URL) != null) {
										dataBrokerMap.setTargetSystemUrl(
												(String) dataBrokerObject.get(DockerKubeConstants.TARGET_SYSTEM_URL));
									}
									if (dataBrokerObject.get(DockerKubeConstants.LOCAL_SYSTEM_DATA_FILE_PATH) != null) {
										dataBrokerMap.setLocalSystemDataFilePath((String) dataBrokerObject
												.get(DockerKubeConstants.LOCAL_SYSTEM_DATA_FILE_PATH));
									}
									if (dataBrokerObject.get(DockerKubeConstants.FIRST_ROW) != null) {
										dataBrokerMap.setFirstRow(
												(String) dataBrokerObject.get(DockerKubeConstants.FIRST_ROW));
									}
									if (dataBrokerObject.get(DockerKubeConstants.CSV_FILE_FIELD_SEPARATOR) != null) {
										dataBrokerMap.setCsvFileFieldSeparator((String) dataBrokerObject
												.get(DockerKubeConstants.CSV_FILE_FIELD_SEPARATOR));
									}
									if (dataBrokerObject.get(DockerKubeConstants.MAP_INPUTS) != null) {
										MapInputs mapInputBean = new MapInputs();
										mapInputArray = (JSONArray) dataBrokerObject
												.get(DockerKubeConstants.MAP_INPUTS);
										log.debug("mapInputArray " + mapInputArray);
										Iterator itr5 = null;
										Iterator<Map.Entry> itr6 = null;
										itr5 = mapInputArray.iterator();
										while (itr5.hasNext()) {
											itr6 = ((Map) itr5.next()).entrySet().iterator();
											InputField inputFieldBean = new InputField();
											while (itr6.hasNext()) {
												Map.Entry pair = itr6.next();
												String key = (String) pair.getKey();
												log.debug("Key " + key);
												log.debug("value " + pair.getValue());
												if (key != null
														&& key.equalsIgnoreCase(DockerKubeConstants.INPUT_FIELD)) {
													JSONObject inputFieldJsonObject = (JSONObject) pair.getValue();
													if (inputFieldJsonObject != null) {
														if (inputFieldJsonObject
																.get(DockerKubeConstants.NAME) != null) {
															inputFieldBean.setName((String) inputFieldJsonObject
																	.get(DockerKubeConstants.NAME));
														}
														if (inputFieldJsonObject
																.get(DockerKubeConstants.TYPE) != null) {
															inputFieldBean.setType((String) inputFieldJsonObject
																	.get(DockerKubeConstants.TYPE));
														}
														if (inputFieldJsonObject
																.get(DockerKubeConstants.CHECKED) != null) {
															inputFieldBean.setChecked((String) inputFieldJsonObject
																	.get(DockerKubeConstants.CHECKED));
														}
														if (inputFieldJsonObject
																.get(DockerKubeConstants.MAPPED_TO_FIELD) != null) {
															inputFieldBean
																	.setMappedToField((String) inputFieldJsonObject
																			.get(DockerKubeConstants.MAPPED_TO_FIELD));
														}
													}
												}
											}
											mapInputBean.setInputField(inputFieldBean);
										}
										mapInputList.add(mapInputBean);
									}
									dataBrokerMap.setMapInputs(mapInputList);
									if (dataBrokerObject.get(DockerKubeConstants.MAP_OUTPUTS) != null) {
										MapOutputs mapOutputBean = new MapOutputs();
										mapoutputArray = (JSONArray) dataBrokerObject
												.get(DockerKubeConstants.MAP_OUTPUTS);
										log.debug("mapoutputArray " + mapoutputArray);
										Iterator itr7 = null;
										Iterator<Map.Entry> itr8 = null;
										itr7 = mapoutputArray.iterator();
										while (itr7.hasNext()) {
											itr8 = ((Map) itr7.next()).entrySet().iterator();
											OutputField outputFieldBean = new OutputField();
											while (itr8.hasNext()) {
												Map.Entry pair = itr8.next();
												String key = (String) pair.getKey();
												log.debug("Key  " + key);
												log.debug("value " + pair.getValue());
												if (key != null
														&& key.equalsIgnoreCase(DockerKubeConstants.OUTPUT_FIELD)) {
													JSONObject outputFieldJsonObject = (JSONObject) pair.getValue();
													if (outputFieldJsonObject != null) {
														if (outputFieldJsonObject
																.get(DockerKubeConstants.TAG) != null) {
															outputFieldBean.setTag((String) outputFieldJsonObject
																	.get(DockerKubeConstants.TAG));
														}
														if (outputFieldJsonObject
																.get(DockerKubeConstants.NAME) != null) {
															outputFieldBean.setName((String) outputFieldJsonObject
																	.get(DockerKubeConstants.NAME));
														}
														if (outputFieldJsonObject.get(
																DockerKubeConstants.TYPE_AND_ROLE_HIERARCHY_LIST) != null) {
															ArrayList<TypeAndRoleHierarchyList> roleHirerachryList = new ArrayList<TypeAndRoleHierarchyList>();
															JSONArray hierarchyListArray = (JSONArray) outputFieldJsonObject
																	.get(DockerKubeConstants.TYPE_AND_ROLE_HIERARCHY_LIST);
															if (hierarchyListArray != null) {
																Iterator itr9 = null;
																Iterator<Map.Entry> itr10 = null;
																itr9 = hierarchyListArray.iterator();
																while (itr9.hasNext()) {
																	itr10 = ((Map) itr9.next()).entrySet().iterator();
																	TypeAndRoleHierarchyList typeAndRoleHierarchyListBean = new TypeAndRoleHierarchyList();
																	while (itr10.hasNext()) {
																		Map.Entry mapPair = itr10.next();
																		String keyVal = (String) mapPair.getKey();
																		log.debug("Key " + keyVal);
																		log.debug("value " + mapPair.getValue());
																		if (keyVal != null) {
																			if (keyVal.equalsIgnoreCase(
																					DockerKubeConstants.NAME)) {
																				typeAndRoleHierarchyListBean.setName(
																						(String) mapPair.getValue());
																			}
																			if (keyVal.equalsIgnoreCase(
																					DockerKubeConstants.ROLE)) {
																				typeAndRoleHierarchyListBean.setRole(
																						(String) mapPair.getValue());
																			}
																		}
																	}
																	roleHirerachryList
																			.add(typeAndRoleHierarchyListBean);
																}
															}
															outputFieldBean
																	.setTypeAndRoleHierarchyList(roleHirerachryList);
														}

													}
												}
											}
											mapOutputBean.setOutputField(outputFieldBean);
										}

										mapOutputList.add(mapOutputBean);
									}
									dataBrokerMap.setMapOutputs(mapOutputList);
								}
							}
							dataBrokerBean.setDataBrokerMap(dataBrokerMap);

						}
					}
				}
			}

		} catch (Exception e) {
			log.error("parseJsonFile failed", e);
			throw new Exception(e.getMessage());
		}
		log.debug("dataBrokerBean " + dataBrokerBean);
		log.debug(" getDataBrokerContainer End");
		return dataBrokerBean;
	}

	/**
	 * jsonFileToObject method is used to get json details
	 * 
	 * @param jsonFile       - json string for parse
	 * @param dataBrokerBean - DataBrokerBean obj
	 * @throws Exception - exception thrown
	 * @return blueprint - Blueprint file object return
	 */
	public Blueprint jsonFileToObject(String jsonFile, DataBrokerBean dataBrokerBean) throws Exception {
		log.debug(" jsonFileToObject Start");
		log.debug("jsonFile " + jsonFile);
		ArrayList<String> list = new ArrayList<String>();
		Blueprint blueprint = new Blueprint();
		ObjectMapper mapper = new ObjectMapper();
		try {
			Object obj = new JSONParser().parse(new StringReader(jsonFile));
			JSONObject jo = (JSONObject) obj;
			String prettyJSONString = jo.toString();
			String name = (String) jo.get(DockerKubeConstants.NAME);
			String version = (String) jo.get(DockerKubeConstants.VERSION);
			blueprint.setName(name);
			blueprint.setVersion(version);
			Iterator<Map.Entry> itr1 = null;
			ArrayList<ProbeIndicator> list_of_pb_indicators = new ArrayList<ProbeIndicator>();
			ProbeIndicator prbIndicator = new ProbeIndicator();
			prbIndicator.setValue("false");
			list_of_pb_indicators.add(prbIndicator);
			blueprint.setProbeIndicator(list_of_pb_indicators);

			JSONArray inputPorts = (JSONArray) jo.get(DockerKubeConstants.INPUT_PORTS);
			ArrayList<OperationSignature> operationList = new ArrayList<OperationSignature>();
			List<InputPort> inputPortList = null;
			if (inputPorts != null) {
				inputPortList = new ArrayList<InputPort>();
				log.debug("input ports");

				Iterator itr2 = inputPorts.iterator();
				while (itr2.hasNext()) {
					InputPort inputPortObj = new InputPort();
					OperationSignature operationSignature = new OperationSignature();
					itr1 = ((Map) itr2.next()).entrySet().iterator();
					while (itr1.hasNext()) {
						Map.Entry pair = itr1.next();
						String key = (String) pair.getKey();
						log.debug("-->" + pair.getKey() + " : " + pair.getValue());
						if (key != null && key.equalsIgnoreCase(DockerKubeConstants.OPERATION_SIGNATURE)) {
							JSONObject jsonObject = (JSONObject) pair.getValue();
							String operationName = (String) jsonObject.get(DockerKubeConstants.OPERATION_NAME);
							operationSignature.setOperationName(operationName);
							inputPortObj.setOperationSignature(operationSignature);
						}
						if (key != null && key.equalsIgnoreCase(DockerKubeConstants.CONTAINER_NAME)) {
							inputPortObj.setContainerName((String) pair.getValue());
						}

					}
					inputPortList.add(inputPortObj);
				}

			}
			if (inputPorts != null && inputPorts.size() > 0) {
				blueprint.setInputPorts(inputPortList);
			}
			JSONArray nodes = (JSONArray) jo.get(DockerKubeConstants.NODES);
			ArrayList<Node> nodeList = new ArrayList<Node>();
			if (nodes != null) {
				Iterator itr3 = nodes.iterator();
				int nodeCount = 0;
				while (itr3.hasNext()) {
					Node node = new Node();
					ArrayList<OperationSignatureList> operSigList = new ArrayList<OperationSignatureList>();
					OperationSignatureList obpListObject = new OperationSignatureList();
					operSigList.add(obpListObject);
					itr1 = ((Map) itr3.next()).entrySet().iterator();
					log.debug("Nodes " + ++nodeCount);
					while (itr1.hasNext()) {
						Map.Entry pair = itr1.next();
						if (pair != null && pair.getKey() != null && pair.getValue() != null) {
							String key = (String) pair.getKey();
							if (key != null && key.equalsIgnoreCase(DockerKubeConstants.OPERATION_SIGNATURE_LIST)) {

								if (pair.getValue() != null) {
									operSigList = jsonArrayParseObjectProb(pair.getValue(), operSigList);

									log.debug("operSigList " + operSigList);
								}

							} else {
								log.debug(" key " + pair.getKey() + " : " + pair.getValue());
								if (key != null && key.equalsIgnoreCase(DockerKubeConstants.CONTAINER_NAME)) {
									node.setContainerName((String) pair.getValue());
								}
								if (key != null && key.equalsIgnoreCase(DockerKubeConstants.IMAGE)) {
									node.setImage((String) pair.getValue());
								}

								if (key != null && key.equalsIgnoreCase(DockerKubeConstants.NODE_TYPE)) {
									node.setNodeType((String) pair.getValue());
								}
								if (key != null && key.equalsIgnoreCase(DockerKubeConstants.PROTO_URI)) {
									node.setProtoUri((String) pair.getValue());
								}
								JSONObject dataBrokerObject = null;
								if (key != null && key.equalsIgnoreCase(DockerKubeConstants.DATA_BROKER_MAP)) {
									dataBrokerObject = (JSONObject) pair.getValue();
									if (dataBrokerObject != null) {
										if (dataBrokerObject.get(DockerKubeConstants.DATA_BROKER_TYPE) != null) {
											String dataBrokerType = (String) dataBrokerObject
													.get(DockerKubeConstants.DATA_BROKER_TYPE);
											log.debug("dataBrokerType " + dataBrokerType);
											if (dataBrokerType != null && dataBrokerType
													.equalsIgnoreCase(DockerKubeConstants.CSV_FILE_NAME)) {
												if (dataBrokerBean != null) {
													node.setDataBrokerMap(dataBrokerBean.getDataBrokerMap());
												}
											}

										}
									}
								}

							}
						}
					}
					node.setOperationSignatureList(operSigList);
					nodeList.add(node);
				}

			}
			blueprint.setNodes(nodeList);
		} catch (Exception e) {
			log.error("jsonFileToObject failed", e);
			throw new Exception(e.getMessage());
		}
		log.debug("list " + list);
		log.debug("jsonFileToObject End");
		return blueprint;
	}

	/**
	 * jsonArrayParseObjectProb method is used to get prob details
	 * 
	 * @param obj           - obj of class
	 * @param listComponent - list of component
	 * @return listComponent - list of component
	 */
	public ArrayList<OperationSignatureList> jsonArrayParseObjectProb(Object obj,
			ArrayList<OperationSignatureList> listComponent) {
		log.debug("jsonArrayParseObjectProb Start");
		JSONArray jsonArr = (JSONArray) obj;
		Iterator itr = jsonArr.iterator();
		OperationSignatureList oprListObj = listComponent.get(0);
		ArrayList<ConnectedTo> connectedList = new ArrayList<ConnectedTo>();
		Iterator<Map.Entry> itr1 = null;
		Iterator<Map.Entry> itr3 = null;

		while (itr.hasNext()) {
			itr1 = ((Map) itr.next()).entrySet().iterator();

			while (itr1.hasNext()) {
				Map.Entry pair = itr1.next();
				String key = (String) pair.getKey();
				log.debug("Key " + key);
				ConnectedTo connectedTo = null;
				if (key != null && key.equalsIgnoreCase(DockerKubeConstants.CONNECTED_TO)) {
					JSONArray connArr = (JSONArray) pair.getValue();
					Iterator conItr = connArr.iterator();
					connectedTo = new ConnectedTo();
					while (conItr.hasNext()) {

						itr3 = ((Map) conItr.next()).entrySet().iterator();
						while (itr3.hasNext()) {
							Map.Entry pair1 = itr3.next();
							String connectedKey = (String) pair1.getKey();
							if (connectedKey != null
									&& connectedKey.equalsIgnoreCase(DockerKubeConstants.CONTAINER_NAME)) {
								String containerKey = (String) pair1.getKey();
								String containerName = (String) pair1.getValue();
								connectedTo.setContainerName(containerName);
							}

							if (connectedKey != null
									&& connectedKey.equalsIgnoreCase(DockerKubeConstants.OPERATION_SIGNATURE)) {
								JSONObject objVar = (JSONObject) pair1.getValue();
								OperationSignature opr = new OperationSignature();
								if (objVar != null) {
									String operation = (String) objVar.get(DockerKubeConstants.OPERATION_NAME);
									opr.setOperationName(operation);
									log.debug("operation " + operation);
									connectedTo.setOperationSignature(opr);
								}
							}
						}

					}
					if (connectedTo != null) {
						connectedList.add(connectedTo);
					}

				}
				if (connectedList != null && connectedList.size() > 0) {
					oprListObj.setConnectedTo(connectedList);
				}
				if (key != null && key.equalsIgnoreCase(DockerKubeConstants.OPERATION_SIGNATURE)) {
					JSONObject objVar = (JSONObject) pair.getValue();
					OperationSignature oprSignature = new OperationSignature();
					if (objVar != null) {
						String oprName = (String) objVar.get(DockerKubeConstants.OPERATION_NAME);
						if (oprName != null && !"".equals(oprName)) {
							oprSignature.setOperationName(oprName);
						}

						String inputMessage = (String) objVar.get(DockerKubeConstants.INPUT_MESSAGE_NAME);
						if (inputMessage != null && !"".equals(inputMessage)) {
							oprSignature.setInputMessageName(inputMessage);
						}

						String outputMessage = (String) objVar.get(DockerKubeConstants.OUTPUT_MESSAGE_NAME);
						if (outputMessage != null && !"".equals(outputMessage)) {
							oprSignature.setOutputMessageName(outputMessage);
						}
					}
					oprListObj.setOperationSignature(oprSignature);
				}

			}
		}
		log.debug("listComponent " + listComponent);
		log.debug("jsonArrayParseObjectProb End");
		return listComponent;
	}

	/**
	 * getProtoDetails method is used to get prob details
	 * 
	 * @param jsonString - json string for parse
	 * @throws Exception - exception thrown
	 * @return contList - container list
	 */
	public List<ContainerBean> getProtoDetails(String jsonString) throws Exception {
		log.debug("getProtoDetails Start");
		log.debug("jsonString " + jsonString);
		HashMap<String, String> imageMap = new HashMap<String, String>();
		ArrayList<String> list = new ArrayList<String>();
		List<ContainerBean> contList = new ArrayList<ContainerBean>();
		try {
			Object obj = new JSONParser().parse(new StringReader(jsonString));
			Iterator<Map.Entry> itr1 = null;
			JSONObject jo = (JSONObject) obj;
			JSONArray nodes = (JSONArray) jo.get(DockerKubeConstants.NODES);
			ArrayList<Node> nodeList = new ArrayList<Node>();
			if (nodes != null) {
				Iterator itr3 = nodes.iterator();
				int nodeCount = 0;
				while (itr3.hasNext()) {
					Node node = new Node();
					itr1 = ((Map) itr3.next()).entrySet().iterator();
					log.debug("Nodes" + ++nodeCount);
					String containerName = null, imageName = null, protoUri = null, protoUriFile = null,
							protoUriFolder = null;
					;
					String nodeType = "";
					while (itr1.hasNext()) {

						Map.Entry pair = itr1.next();
						if (pair != null && pair.getKey() != null && pair.getValue() != null) {
							String key = (String) pair.getKey();
							if (key != null && key.equalsIgnoreCase(DockerKubeConstants.CONTAINER_NAME)) {
								containerName = (String) pair.getValue();
							}
							if (key != null && key.equalsIgnoreCase(DockerKubeConstants.IMAGE)) {
								imageName = (String) pair.getValue();
							}
							if (key != null && key.equalsIgnoreCase(DockerKubeConstants.NODE_TYPE)) {
								nodeType = (String) pair.getValue();
							}
							if (key != null && key.equalsIgnoreCase(DockerKubeConstants.PROTO_URI)) {
								protoUri = (String) pair.getValue();
							}

						}
					}

					if (containerName != null && !"".equals(containerName) && !nodeType.equalsIgnoreCase("Splitter")
							&& !nodeType.equalsIgnoreCase("Collator")) {
						imageMap.put(containerName, protoUri);
						ContainerBean contBean = new ContainerBean();
						contBean.setContainerName(containerName);
						if (imageName != null) {
							contBean.setImage(imageName);
						}
						if (nodeType != null) {
							contBean.setNodeType(nodeType);
						}
						if (protoUri != null) {
							contBean.setProtoUriPath(protoUri);
						}
						contList.add(contBean);
					}

				}
			}

		} catch (Exception e) {
			log.error("getProtoDetails failed", e);
			throw new Exception(e.getMessage());
		}
		log.debug("imageMap " + imageMap);
		log.debug(" getProtoDetails End");
		return contList;
	}

	/**
	 * parseJsonFileImageMap method is used to get prob details
	 * 
	 * @param jsonString - json string for parse
	 * @throws Exception - exception thrown
	 * @return DeploymentKubeBeanList - deploymentKubeBeanList bean obj
	 */
	public List<DeploymentKubeBean> parseJsonFileImageMap(String jsonString) throws Exception {
		log.debug("parseJsonFileProbe Start");
		log.debug("jsonString " + jsonString);
		HashMap<String, String> imageMap = new HashMap<String, String>();
		ArrayList<String> list = new ArrayList<String>();
		List<DeploymentKubeBean> deploymentKubeBeanList = new ArrayList<DeploymentKubeBean>();
		try {
			Object obj = new JSONParser().parse(new StringReader(jsonString));
			Iterator<Map.Entry> itr1 = null;
			JSONObject jo = (JSONObject) obj;
			JSONArray nodes = (JSONArray) jo.get(DockerKubeConstants.NODES);
			ArrayList<Node> nodeList = new ArrayList<Node>();
			if (nodes != null) {
				Iterator itr3 = nodes.iterator();
				int nodeCount = 0;
				while (itr3.hasNext()) {
					Node node = new Node();
					itr1 = ((Map) itr3.next()).entrySet().iterator();
					log.debug("Nodes" + ++nodeCount);
					String containerName = null, imageName = null;
					String nodeType = "";
					while (itr1.hasNext()) {

						Map.Entry pair = itr1.next();
						if (pair != null && pair.getKey() != null && pair.getValue() != null) {
							String key = (String) pair.getKey();
							if (key != null && key.equalsIgnoreCase(DockerKubeConstants.CONTAINER_NAME)) {
								containerName = (String) pair.getValue();
							}
							if (key != null && key.equalsIgnoreCase(DockerKubeConstants.IMAGE)) {
								imageName = (String) pair.getValue();
								String result[] = imageName.split("/");
								
								/**
								 * Temporary Solution
								 * 
								 * 
								 **/

							}
							if (key != null && key.equalsIgnoreCase(DockerKubeConstants.NODE_TYPE)) {
								nodeType = (String) pair.getValue();
							}
						}
					}
					if (containerName != null && imageName != null && !"".equals(containerName) && !"".equals(imageName)
							&& !nodeType.equalsIgnoreCase("Splitter") && !nodeType.equalsIgnoreCase("Collator")) {
						DeploymentKubeBean dbean = new DeploymentKubeBean();
						dbean.setImage(imageName);
						dbean.setContainerName(containerName);
						dbean.setNodeType(nodeType);
						deploymentKubeBeanList.add(dbean);
						imageMap.put(imageName, containerName);
						System.out.println("Sajid Naeem : ");
					}

				}
			}

		} catch (Exception e) {
			log.error("parseJsonFileProbe failed", e);
			throw new Exception(e.getMessage());
		}
		log.debug("imageMap " + imageMap);
		log.debug("DeploymentKubeBeanList " + deploymentKubeBeanList);
		log.debug(" parseJsonFileProbe End");
		return deploymentKubeBeanList;
	}

	/**
	 * getSequenceListFromJSON method is used to get prob details
	 * 
	 * @param jsonString - json string for parse
	 * @throws Exception - exception thrown
	 * @return linkedList - List of containers
	 */
	public LinkedList<String> getSequenceListFromJSON(String jsonString) throws Exception {
		log.debug("getSequenceFromJSONProbe Start");
		log.debug("jsonString " + jsonString);
		String contentString = "";
		HashMap<String, String> imageMap = new HashMap<String, String>();
		ArrayList<String> list = new ArrayList<String>();
		LinkedList<String> linkedList = new LinkedList<String>();
		try {
			NodeTree<String> root = new NodeTree<String>(DockerKubeConstants.BLUEPRINT_CONTAINER);
			Object obj = new JSONParser().parse(new StringReader(jsonString));
			JSONObject jo = (JSONObject) obj;
			JSONArray nodes = (JSONArray) jo.get(DockerKubeConstants.NODES);
			if (nodes != null && !nodes.isEmpty()) {
				Iterator itr3 = nodes.iterator();
				int nodeCount = 0;
				while (itr3.hasNext()) {
					Iterator<Map.Entry> itr4 = ((Map) itr3.next()).entrySet().iterator();
					Iterator<Map.Entry> itr5 = itr4;
					String containerName = "";
					String imageName = "";
					String contName = "test";
					NodeTree<String> testNode = new NodeTree<String>(contName);
					log.debug("Second while");
					String nodeType = "";
					while (itr4.hasNext()) {
						Map.Entry pair = itr4.next();
						if (pair != null && pair.getKey() != null && pair.getValue() != null
								&& !nodeType.equalsIgnoreCase("Splitter") && !nodeType.equalsIgnoreCase("Collator")) {
							log.debug("key " + pair.getKey() + " nodeType" + nodeType);
							String key = (String) pair.getKey();
							String val = (String) pair.getValue().toString();
							if (key != null && key.equalsIgnoreCase(DockerKubeConstants.OPERATION_SIGNATURE_LIST)) {
								sequenceJsonParseProbe(pair.getValue(), testNode, root);
							}
							if (key != null && key.equalsIgnoreCase(DockerKubeConstants.CONTAINER_NAME)) {
								containerName = val;
								contName = val;
							} else {
								log.debug("key " + pair.getKey());
							}
							if (key != null && key.equalsIgnoreCase(DockerKubeConstants.IMAGE)) {
								imageName = val;
								list.add(val);
							}
							if (key != null && key.equalsIgnoreCase(DockerKubeConstants.NODE_TYPE)) {
								nodeType = val;
							}
							if (containerName != null && imageName != null && !"".equals(containerName)
									&& !"".equals(imageName)) {
								imageMap.put(imageName, containerName);
							}
						}
					}
					log.debug(" nodeType Before Adding" + nodeType);
					if (!nodeType.equalsIgnoreCase("Splitter") && !nodeType.equalsIgnoreCase("Collator")) {
						log.debug(" nodeType After Adding" + nodeType);
						testNode.setData(contName);
						NodeTree<String> searchNode = findDataInTree(root, contName);
						if (searchNode != null) {
							NodeTree<String> parent = searchNode.getParent();
							String parentData = parent.getData();
							if (parent != null) {
								int index = parent.getChildren().indexOf(searchNode);
								parent.getChildren().remove(searchNode);
							}
							NodeTree<String> parrentNode = findDataInTree(root, parentData);
							if (parrentNode != null) {
								parrentNode.addChild(testNode);
							}
						} else {
							root.addChild(testNode);
						}
					}
				}
			}
			printTree(root, " ", linkedList);
			Collections.reverse(linkedList);
			log.debug("linkedList " + linkedList);

		} catch (Exception e) {
			log.error("getSequenceFromJSONProbe failed", e);
			throw new Exception(e.getMessage());
		}
		log.debug(" getSequenceFromJSONProbe End");
		return linkedList;
	}

	public NodeTree<String> findDataInTree(NodeTree node, String searchQuery) {
		log.debug("findDataInTree Start");
		NodeTree<String> ss = null;
		if (node.getData().equals(searchQuery)) {
			log.debug("node.Data() " + node.getData());
			return node;
		}
		List<NodeTree<String>> children = node.getChildren();
		int count = children.size();
		for (NodeTree each : children) {
			NodeTree<String> findDataInTree = findDataInTree(each, searchQuery);
			log.debug("node.Data " + node.getData());
			if (findDataInTree != null) {
				return findDataInTree;
			}

		}
		log.debug(" findDataInTree End");
		return ss;
	}

	public <T> void printTree(NodeTree<T> node, String appender, LinkedList<String> linkedList) {
		log.debug(appender + node.getData());
		linkedList.add(String.valueOf(node.getData()));
		node.getChildren().forEach(each -> printTree(each, (appender + appender), linkedList));

	}

	/**
	 * sequenceJsonParseProbe method is used to get sequence details
	 * 
	 * @param obj      -obj of class
	 * @param newNode  - Node of nodetree
	 * @param rootNode - root node of nodeTree
	 */
	public void sequenceJsonParseProbe(Object obj, NodeTree<String> newNode, NodeTree<String> rootNode) {
		log.debug("sequenceJsonParseProbe Start");
		JSONArray jsonArr = (JSONArray) obj;
		Iterator itr = jsonArr.iterator();
		Iterator<Map.Entry> itr1 = null;
		while (itr.hasNext()) {
			itr1 = ((Map) itr.next()).entrySet().iterator();
			while (itr1.hasNext()) {
				Map.Entry pair = itr1.next();
				log.debug("key value" + pair.getKey() + " : " + pair.getValue());

				if (pair.getKey() != null && pair.getKey().equals(DockerKubeConstants.CONNECTED_TO)
						&& pair.getValue() != null) {

					JSONArray connArr = (JSONArray) pair.getValue();
					Iterator conItr = connArr.iterator();
					while (conItr.hasNext()) {
						String data = null;
						Iterator<Map.Entry> contrItr = ((Map) conItr.next()).entrySet().iterator();
						while (contrItr.hasNext()) {
							Map.Entry cntPair = contrItr.next();
							String connectedKey = (String) cntPair.getKey();
							if (connectedKey != null
									&& connectedKey.equalsIgnoreCase(DockerKubeConstants.CONTAINER_NAME)) {
								String containerKey = (String) cntPair.getKey();
								data = (String) cntPair.getValue();
								NodeTree<String> subNode = new NodeTree<String>(data);
								NodeTree<String> searchNode = findDataInTree(rootNode, data);
								if (searchNode != null) {
									NodeTree<String> parent = searchNode.getParent();
									String parentData = parent.getData();
									if (parent != null) {
										parent.getChildren().remove(searchNode);
										newNode.addChild(searchNode);
									}

								} else {
									newNode.addChild(subNode);
								}
							}
						}
					}
				}
				log.debug("key value " + pair.getKey() + " : " + pair.getValue());
			}
		}

		log.debug("sequenceJsonParseProbe End");
	}

	public boolean checkProbeIndicator(String jsonString) throws Exception {
		log.debug("checkProbeIndicator Start");
		boolean probeIndicatorValue = false;
		String value = null;
		try {
			Object obj = new JSONParser().parse(new StringReader(jsonString));
			JSONObject jo = (JSONObject) obj;
			JSONArray probeIndicator = (JSONArray) jo.get(DockerKubeConstants.PROBE_INDICATOR);
			if (probeIndicator == null) {
				probeIndicator = (JSONArray) jo.get(DockerKubeConstants.PROBE_INDOCATOR);
			}
			if (probeIndicator != null) {
				Iterator itr = probeIndicator.iterator();
				Iterator<Map.Entry> itr1 = null;

				while (itr.hasNext()) {
					itr1 = ((Map) itr.next()).entrySet().iterator();
					while (itr1.hasNext()) {
						Map.Entry pair = itr1.next();
						value = (String) pair.getValue();
					}
				}
			}
			if (value != null && value.equalsIgnoreCase("True")) {
				probeIndicatorValue = true;
			} else {
				probeIndicatorValue = false;
			}
		} catch (Exception e) {
			log.error("checkProbeIndicator failed", e);
			throw new Exception(e.getMessage());
		}
		log.debug("probeIndicator " + probeIndicatorValue);
		log.debug("checkProbeIndicator End");
		return probeIndicatorValue;

	}

	public void getBluprintDetail(String jsonFile) throws Exception {
		log.debug("getBluprintDetail Start");
		try {
			Object obj = new JSONParser().parse(new StringReader(jsonFile));
			JSONObject jo = (JSONObject) obj;
			String prettyJSONString = jo.toString();
			String name = (String) jo.get(DockerKubeConstants.NAME);
			String version = (String) jo.get(DockerKubeConstants.VERSION);
		} catch (Exception e) {
			log.error("checkProbeIndicator failed", e);
			throw e;
		}
		log.debug("getBluprintDetail End");
	}
}
