#!/usr/bin/env python3
# ===================================================================================
# Copyright (C) 2021 Fraunhofer Gesellschaft. All rights reserved.
# ===================================================================================
# This Acumos software file is distributed by Fraunhofer Gesellschaft
# under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# This file is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
# ===============LICENSE_END========================================================

import grpc
import json
# import the generated classes
import orchestrator_pb2
import orchestrator_pb2_grpc
import shutil
import os
import argparse

class OrchestratorClient:


    def request_orchestrator_server_response(self, port_address, blueprint, dockerinfo, protozip):
        """open a gRPC channel"""
        channel = grpc.insecure_channel(port_address)

        """create a stub (client)"""
        stub = orchestrator_pb2_grpc.start_orchestratorStub(channel)

        """Read the blueprint.json as dict and convert into string"""
        with open(blueprint) as f:
            blueprint_dict = json.load(f)
        blueprint_str = json.dumps(blueprint_dict)

        """Read the dockerinfo.json as dict and convert to string"""
        with open(dockerinfo) as f:
            dockerinfo_dict = json.load(f)
        dockerinfo_str = json.dumps(dockerinfo_dict)

        """Read the zip file containing all the protobufs for a particular pipeline"""
        with open(protozip, 'rb') as f:
            byte_stream = f.read()

        """create a request to pass it to orchestrator server"""
        request = orchestrator_pb2.PipelineConfig(blueprint=blueprint_str, dockerinfo=dockerinfo_str,
                                                  protoszip=byte_stream)
        """make the call"""
        response = stub.executePipeline(request)

        print("status code for the orchestrator: ", response.statusCode)
        print("status message of the orchestrator: ", response.statusText)

    def go_one_folder_up(self):
        os.path.abspath(os.curdir)
        os.chdir("..")
        return os.path.abspath(os.curdir)

    def create_protoszip(self):
        path = self.go_one_folder_up()
        proto_folder = path + "/microservice"
        shutil.make_archive("pipelineprotos", 'zip', proto_folder)

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Enter port address")
    parser.add_argument('port_address', type=str, help='Enter the address in the format ipaddress:port' )
    args = parser.parse_args()
    #port = '10.103.246.169:30030'
    port = args.port_address
    client = OrchestratorClient()
    client.create_protoszip()
    blueprint = "blueprint.json"
    docker_info ="dockerinfo.json"
    proto_zip = "pipelineprotos.zip"
    client.request_orchestrator_server_response(port, blueprint, docker_info, proto_zip)
