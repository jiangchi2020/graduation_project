from ast import keyword
from nlp_pb2 import RpcNlpResponse
from nlp_pb2_grpc import RpcNlpServiceServicer

class NlpService(RpcNlpServiceServicer):
    def analysis(self, request, context):
        query = request.query
        print("recv query => %s" % query)
        return RpcNlpResponse(
            status=0,
            message="OK",
            keyword=[
                RpcNlpResponse.Keyword(
                    type=RpcNlpResponse.QueryType.POINT,
                    center="成都东",
                    distance=20000,
                    poiCode=110000,
                    key="公园"
                ),
                RpcNlpResponse.Keyword(
                    type=RpcNlpResponse.QueryType.POINT,
                    center="合肥南",
                    distance=10000,
                    poiCode=50000,
                    key="酒店"
                )
            ])