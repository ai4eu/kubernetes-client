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
package com.dockerKube.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.dockerKube.beans.ContainerBean;
import com.dockerKube.beans.DeploymentBean;



public interface KubeService {

	public String getBluePrintNexus(String solutionId, String revisionId,String datasource,String userName,String dataPd,
			String nexusUrl,String nexusUserName,String nexusPd) throws  Exception;
	public ByteArrayOutputStream getBluePrintNexusSingleSolution(String solutionId, String revisionId,String datasource,String userName,String dataPd,
			String nexusUrl,String nexusUserName,String nexusPd) throws  Exception;
	public List<ContainerBean> getprotoDetails(List<ContainerBean> contList,DeploymentBean dBean)throws  Exception;
	public ByteArrayOutputStream getNexusUrlFile(String nexusUrl, String nexusUserName,String nexusPassword,String nexusURI)throws Exception;
	public void getDataBrokerFile(List<ContainerBean> contList,DeploymentBean dBean,String jsonString) throws Exception;
	public void getSolutionYMLFile(DeploymentBean dBean,String jsonString)throws Exception;
	public byte[] createCompositeSolutionZip(DeploymentBean dBean)throws Exception;
	public byte[] createSingleSolutionZip(DeploymentBean dBean)throws Exception;
	public String getSolutionCode(String solutionId,String datasource,String userName,String dataPd);
	public byte[] singleSolutionDetails(DeploymentBean dBean,String imageTag,String singleModelPort, String solutionToolKitType)throws Exception;
	public byte[] compositeSolutionDetails(DeploymentBean dBean, String solutionToolKitType)throws Exception;
	public String getSingleImageData(String solutionId,String revisionId,String datasource,String userName,String dataPd)throws Exception;

}
