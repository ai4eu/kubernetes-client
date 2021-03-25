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


class OrchestratorClient:

    def __init__(self, addr, blueprint_path, dockerinfo_path, zip_path):
        self.port_address = addr
        self.blueprint = blueprint_path
        self.dockerinfo = dockerinfo_path
        self.protozip = zip_path

    def request_orchestrator_server_response(self):
        """open a gRPC channel"""
        channel = grpc.insecure_channel(self.port_address, options=(('grpc.enable_http_proxy', 0),))

        """create a stub (client)"""
        stub = orchestrator_pb2_grpc.start_orchestratorStub(channel)

        """Read the blueprint.json as dict and convert into string"""
        with open(self.blueprint) as f:
            blueprint_dict = json.load(f)
        blueprint_str = json.dumps(blueprint_dict)

        """Read the dockerinfo.json as dict and convert to string"""
        with open(self.dockerinfo) as f:
            dockerinfo_dict = json.load(f)
        dockerinfo_str = json.dumps(dockerinfo_dict)

        """Read the zip file containing all the protobufs for a particular pipeline"""
        with open(self.protozip, 'rb') as f:
            byte_stream = f.read()

        """create a request to pass it to orchestrator server"""
        request = orchestrator_pb2.PipelineConfig(blueprint=blueprint_str, dockerinfo=dockerinfo_str,
                                                  protoszip=byte_stream)
        """make the call"""
        response = stub.executePipeline(request)

        print("status code for the orchestrator: ", response.statusCode)
        print("status message of the orchestrator: ", response.statusText)


if __name__ == "__main__":
    port = 'localhost:50090'
    blueprint = "blueprint.json"
    docker_info = "dockerinfo.json"
    proto_zip = "pipelineprotos.zip"
    client = OrchestratorClient(port, blueprint, docker_info, proto_zip)
    client.request_orchestrator_server_response()
