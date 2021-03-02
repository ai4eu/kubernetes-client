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

public class DockerKubeConstants {
	
    
	public static final String APIVERSION_YML="apiVersion";
	public static final String V_YML="v1";
	public static final String KIND_YML="kind";
	public static final String SERVICE_YML="Service";
	public static final String NAMESPACE_YML="namespace"; 
	public static final String ACUMOS_YML="acumos";
	public static final String NAME_YML="name";
	public static final String METADATA_YML="metadata";
	public static final String MYMODEL_SERVICE_YML="mymodel-service";
	public static final String APP_YML="app";
	public static final String MYMODEL_YML="mymodel";
	public static final String SELECTOR_YML="selector";
	public static final String TYPE_YML="type";
	public static final String NODEPORT_YML="nodePort";
	public static final String NODE_PORT_YML="nodePort";
	public static final String NODE_TYPE_PORT_YML="NodePort";
	public static final String CLUSTERIP_YML="ClusterIP";
	public static final String PORT_YML="port";
	public static final String TARGETPORT_YML="targetPort";
	public static final String PORTS_YML="ports";
	public static final String SPEC_YML="spec";
	
	public static final String APIVERSION_DEP_YML="apiVersion";
	public static final String APPS_V1_DEP_YML="apps/v1";
	public static final String KIND_DEP_YML="kind";
	public static final String DEPLOYMENT_DEP_YML="Deployment";
	public static final String NAMESPACE_DEP_YML="namespace";
	public static final String ACUMOS_DEP_YML="acumos";
	public static final String NAME_DEP_YML="name";
	public static final String LABELS_DEP_YML="labels";
	public static final String METADATA_DEP_YML="metadata";
	public static final String REPLICAS_DEP_YML="replicas";
	public static final String APP_DEP_YML="app";
	public static final String MYMODEL_DEP_YML="mymodel";
	public static final String MATCHLABELS_DEP_YML="matchLabels";
	public static final String SELECTOR_DEP_YML="selector";
	public static final String IMAGE_DEP_YML="image";
	public static final String PROTOBUF_API_DEP_YML="protobuf-api";
	public static final String PROBE_UI_YML="probe-ui";
	
	
	
	public static final String CONTAINERPORT_DEP_YML="containerPort";
	public static final String PORTS_DEP_YML="ports";
	public static final String CONTAINERS_DEP_YML="containers";
	public static final String SPEC_DEP_YML="spec";
	public static final String TEMPLATE_DEP_YML="template";
	public static final String MOUNTPATH_DEP_YML="mountPath";
	public static final String PATHLOGS_DEP_YML="/logs";
	public static final String DATABROKER_PATHLOG_DEP_YML="/var/acumos/datasource";
	public static final String DATABROKER_LOGNAME="datasource";
	public static final String PROBE_MOUNTPATH_DEP_YML="/usr/share/nginx/html";
	public static final String PROBE_PATHLOG_DEP_YML="/var/acumos/microservice";
	public static final String LOGS_DEP_YML="logs";
	public static final String VOLUMEMOUNTS_DEP_YML="volumeMounts";
	public static final String ACUMOS_REGISTRY_DEP_YML="acumos-registry";
	public static final String IMAGEPULLSECRETS_DEP_YML="imagePullSecrets";
	public static final String PATH_DEP_YML="path";
	public static final String ACUMOSPATHLOG_DEP_YML="/var/acumos/log";
	public static final String HOSTPATH_DEP_YML="hostPath";
	public static final String RESTARTPOLICY_DEP_YML="restartPolicy";
	public static final String ALWAYS_DEP_YML="Always";
	public static final String VOLUMES_DEP_YML="volumes";
	public static final String NAME_MCAPI_YML="mc-api";
	public static final String NAME_DATABROKER_YML="databroker-api";
	public static final String DATABROKER_NAME_YML= "databroker";
	public static final String VOLUME_PROTO_YML= "proto-files";
	
	public static final String APPLICATION_JSON="application/json";
	public static final String BLUEPRINT_CONTAINER_NAME= "BluePrintContainer";
	public static final String BLUEPRINT_MODELCONNECTOR_NAME= "modelconnector";
	public static final String SINGLE_CONTAINER_NAME= "SingleImageContainer";
	public static final String PROBE_CONTAINER_NAME= "probe";
	public static final String NGINX_CONTAINER_NAME= "nginx";
	public static final String JSON_FILE_NAME= "blueprint.json";
	public static final String AZURE_AUTH_LOCATION= "AZURE_AUTH_LOCATION";
	public static final String AZURE_AUTH_LOCATION_NEXT= "AZURE_AUTH_LOCATION_2";
	public static final String SSH_ACS= "ACS";
	public static final String IMAGE_TAG_LATEST= "latest";
	public static final String PRIVATE_REPO_PREFIX= "/samples/";
	public static final String CONTAINER_NAME_PREFIX= "-private_";
	public static final String PROBE_NODE_TYPE= "Probe";
	public static final String DEFAULT_NODE_TYPE= "Default";
	public static final String PUT_DOCKER_INFO_URL= "putDockerInfo";
	public static final String PUT_BLUEPRINT_INFO_URL= "putBlueprint";
	public static final String CONFIG_DB_URL= "configDB";
	public static final String DATABROKER_NAME= "DataBroker";
	public static final String PROBEAPI_NAME="probe-api";
	public static final String VALUE="value";
	public static final String NEXUSENDPOINT_URL="NEXUSENDPOINTURL";
	public static final String ACUMOS_PROBE_EXTERNAL_PORT="ACUMOS_PROBE_EXTERNAL_PORT";
	public static final String ENV="env";
	public static final String PROBE_SCHEMA_YML="probe-schema";
	
	public static final String DEPLOYMENT_PROCESS= "DP";
	public static final String DEPLOYMENT_FAILED= "FA";
	public static final String DATA_BROKER_CSV_FILE= "csv";
	public static final String ARTIFACT_TYPE_BLUEPRINT= "BP";
	public static final String HTTP_PROPERTY= "https.protocols";
	public static final String HTTP_PROPERTY_VALUE= "TLSv1";
	public static final String SSL_DOCKER= "docker";
	public static final String DOCKER_CERT_PATH="DOCKER_CERT_PATH";
	public static final String CA_PEM="ca.pem";
	public static final String KEY_PEM="key.pem";
	public static final String CERT_PEM="cert.pem";
	public static final String VNET_NAME="vnet";
	public static final String FRONT_END_NSG_NAME="fensg";
	public static final String NETWORK_INTERFACE_NAME="nic1";
	public static final String PUBLIC_IP_ADDRESS_LEAF="pip1";
	public static final int SSH_PORT=22;
	public static final int DOCKER_HOST_PORT=80;
	public static final String NODES="nodes";
	public static final String DEPENDS_ON="depends_on";
	public static final String CONTAINER_NAME="container_name";
	public static final String IMAGE="image";
	public static final String NAME="name";
	public static final String VERSION="version";
	public static final String ORCHESTRATOR="orchestrator";
	public static final String INPUT_OPERATION_SIGNATURES="input_operation_signatures";
	public static final String OPERATION="operation";
	public static final String PROBE_INDOCATOR="probeIndocator";
	public static final String PROBE_INDICATOR="probeIndicator";
	public static final String OPERATION_SIGNATURE="operation_signature";
	public static final String CONNECTED_TO="connected_to";
	public static final String OPERATION_NAME="operation_name";
	public static final String INPUT_MESSAGE_NAME="input_message_name";
	public static final String OUTPUT_MESSAGE_NAME="output_message_name";
	public static final String BLUEPRINT_CONTAINER="BluePrintContainer";
	public static final String CONTAINER_TEST="test";
	public static final String TRAINING_CLIENTS="training_clients";
	public static final String INPUT_PORTS="input_ports";
	public static final String OPERATION_SIGNATURE_LIST="operation_signature_list";
	public static final String NODE_TYPE="node_type";
	public static final String PROTO_URI="proto_uri";
	public static final String SCRIPT="script";
	public static final String DATA_BROKER_MAP="data_broker_map";
	public static final String DATA_BROKER_TYPE="data_broker_type";
	public static final String DEFAULT="Default";
	public static final String DATA_BROKER="DataBroker";
	public static final String CSV_FILE_NAME="csv";
	public static final String TARGET_SYSTEM_URL="target_system_url";
	public static final String LOCAL_SYSTEM_DATA_FILE_PATH="local_system_data_file_path";
	public static final String FIRST_ROW="first_row";
	public static final String CSV_FILE_FIELD_SEPARATOR="csv_file_field_separator";
	public static final String MAP_INPUTS="map_inputs";
	public static final String INPUT_FIELD="input_field";
	public static final String TYPE="type";
	public static final String CHECKED="checked";
	public static final String MAPPED_TO_FIELD="mapped_to_field";
	public static final String MAP_OUTPUTS="map_outputs";
	public static final String OUTPUT_FIELD="output_field";
	public static final String TAG="tag";
	public static final String TYPE_AND_ROLE_HIERARCHY_LIST="type_and_role_hierarchy_list";
	public static final String ROLE="role";
	public static final String VM_CREATION_ERROR="role";
	public static final String KUBE_VERSION="apps/v1beta2";
	public static final String KUBE_KIND="Deployment";
	public static final String KUBE_PATH_DEPLOY_SH="deploy/private/deploy.sh";
	public static final String KUBE_PATH_K8S_SH="deploy/private/setup_k8s.sh";
	public static final String KUBE_SOLUTION="solution.zip";
	public static final String KUBE_DEPLOY_SH="deploy.sh";
	public static final String KUBE_CLIENT_SCRIPT="kubernetes-client-script.py";
	public static final String KUBE_PATH_CLIENT_SCRIPT="deploy/private/kubernetes-client-script.py";
	public static final String KUBE_DOC_FILE_PATH="docs/deploy-in-private-k8s.rst";
	public static final String KUBE_DOC_FILE="deploy-in-private-k8s.rst";
	public static final String KUBE_DEPLOY_ENV_SH="deploy_env.sh";
	public static final String KUBE_K8S_SH="setup_k8s.sh";
	public static final String KUBE_BLUEPRINT_JSON="blueprint.json";
	public static final String KUBE_DOCKERINFO_JSON="dockerinfo.json";
	public static final String KUBE_SOLUTION_YML="solution.yaml";
	public static final String KUBE_DATABROKER_JSON="databroker.json";
	public static final String NGINX_CONTAINER="Nginx";
	public static final String BLUEPRINT_IMAGENAME_PROP="blueprint.ImageName";
	public static final String BLUEPRINT_NAME_PROP="blueprint.name";
	public static final String BLUEPRINT_PORT_PROP="blueprint.port";
	public static final String BLUEPRINT_NODEPORT_PROP="blueprint.nodePort";
	public static final String NEXUS_URL_PROP="nexus.url";
	public static final String NEXUS_USERNAME_PROP="nexus.username";
	public static final String NEXUS_PD_PROP="nexus.password";
	public static final String CMNDATASVC_CMNDATASVCENDPOINTURL_PROP="cmndatasvc.cmndatasvcendpointurl";
	public static final String CMNDATASVC_CMNDATASVCUSER_PROP="cmndatasvc.cmndatasvcuser";
	public static final String CMNDATASVC_CMNDATASVCPD_PROP="cmndatasvc.cmndatasvcpwd";
	public static final String KUBE_IP="kube.ip";
	public static final String PROBEIMAGE_NAME="probe.probeImageName";
	public static final String PROBEIMAGE_PORT="probe.probeImagePORT";
	public static final String NGINXIMAGE_NAME="kube.nginxImageName";
	public static final String INCREMENT_PORT="kube.incrementPort";
	public static final String SINGLE_MODEL_PORT="kube.singleModelPort";
	public static final String SINGLE_NODE_PORT="kube.singleNodePort";
	public static final String SINGLE_TARGET_PORT="kube.singleTargetPort";
	
	public static final String DATABROKER_MODEL_PORT="kube.dataBrokerModelPort";
	public static final String DATABROKER_NODE_PORT="kube.dataBrokerNodePort";
	public static final String DATABROKER_TARGET_PORT="kube.dataBrokerTargetPort";
	
	public static final String PROBE_MODEL_PORT="probe.probeModelPort";
	public static final String PROBE_NODE_PORT="probe.probeNodePort";
	public static final String PROBE_TARGET_PORT="probe.probeTargetPort";
	public static final String PROBE_API_PORT="probe.probeApiPort";
	public static final String PROBE_SCHEMA_PORT="probe.probeSchemaPort";
	public static final String PROBE_EXTERNAL_PORT="probe.probeExternalPort";
	
	public static final String FOLDERPATH="kube.folderPath";
	public static final String ML_TARGET_PORT="kube.mlTargetPort";
	public static final String NGINX_IMAGE_NAME="kube.nginxImageName";
	public static final String NEXUS_END_POINTURL="kube.nexusEndPointURL";
	public static final String DOCKER_PROXY_HOST="dockerproxy.host";
	public static final String DOCKER_PROXY_PORT="dockerproxy.port";
	
	public static final String LOGSTASH_HOST="logstash.host";
	public static final String LOGSTASH_IP="logstash.ip";
	public static final String LOGSTASH_PORT="logstash.port";
	
	public static final String DOCKER_HOST="dockerHost";
	public static final String DOCKER_PORT="dockerPort";
	public static final String MODEL_NAME="modelName";
	public static final String SOLUTION_ID="solutionId";

}
